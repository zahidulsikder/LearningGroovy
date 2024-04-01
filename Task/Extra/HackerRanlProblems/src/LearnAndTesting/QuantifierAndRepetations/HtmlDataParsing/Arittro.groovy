import groovy.xml.MarkupBuilder

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


String url = "https://www.gov.uk/government/publications/businesses-not-complying-with-money-laundering-regulations-in-2018-to-2019/list-of-businesses-for-tax-year-2019-to-2020-that-have-not-complied-with-the-2017-money-laundering-regulations";


//Create http Request to the webste
HttpClient httpClient = HttpClient.newHttpClient();
HttpRequest httpRequest = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
def responseBody = httpResponse.body();


//Initialize a arrayList to store all the fetching data
def businessesList = []


//fetching all tables body from the table
def tableMatcher = responseBody =~ /(?s)<tbody>(.*?)<\/tbody>/

//counter for the tables ---> There are 14 tables I just need 4 tables to fetch data
int count = 0;


//Iterate tableMatcher and counting for the 4th
while (tableMatcher.find()) {
    count++
    def table = tableMatcher.group(1)
    tableDataFetch(table, businessesList)
    if (count >= 4) {
        break;
    }

}

//console print to check
businessesList.each { businessData ->
    println("Main Entity Name: ${businessData.Name}")
    println("Address: ${businessData.registeredAddress}")
    println("Entity URL: $url")
    println("Remark: Penalty Amount: ${businessData.penaltyAmount}")
    println("Event Description: ${businessData.description}")
    println("Event Date: NO EVENT DATE")
    println("----------------------------------------------------")
}


// Build the XML structure using MarkupBuilder
def writer = new StringWriter()
def xmlBuilder = new MarkupBuilder(writer)
xmlBuilder.businesses() {
    businessesList.each { businessData ->
        business() {
            "Main Entity_Name" businessData.Name
            "Address" businessData.registeredAddress
            "Entity URL" url
            "Remark" "Penalty Amount: " + businessData.penaltyAmount
            "Event Description" businessData.description
            "Event Date" "NO EVENT DATE"
        }
    }
}


// Write  XML to a file
def xmlString = writer.toString()
Path outputFilePath = Paths.get("output.xml")
Files.write(outputFilePath, xmlString.getBytes())


//General method for the all 4 tables
def tableDataFetch(table, businessesList) {

    //Regex for fetching td only
    def tableMatcher = table =~ /(?s)<tr>(.*?)<\\/tr>/
    while (tableMatcher.find()) {

        //println tableMatcher.group(1);
        def tableData = tableMatcher.group(1);
        def boundarycatch = /(?s)<td>(.*?)<\/td>/;

        // fetching all td inner text to the matches ArrayList
        def matches = tableData.findAll(boundarycatch) { match ->
            match[1].replaceAll(/<[^>]*>/, '').trim()
        }

        // println matches

        def entityName = matches.get(0).replaceAll(/&amp;/, "&").replaceAll(/\s+/, " ");
        def entityAddress = matches.get(1).replaceAll(/\s+/, " ");
        def entityPanalty = matches.get(4).replaceAll(/\s+/, " ");
        def entityDescription = matches.get(3).replaceAll(/\s+/, " ");

        //creating a Map
        def businessData = [
                Name             : entityName,
                registeredAddress: entityAddress,
                penaltyAmount    : entityPanalty,
                description      : entityDescription
        ]

        //set to the mainArrayList
        businessesList << businessData


    }

}