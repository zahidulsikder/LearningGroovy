import groovy.xml.MarkupBuilder

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

def url = "https://www.gov.uk/government/publications/businesses-not-complying-with-money-laundering-regulations-in-2018-to-2019/list-of-businesses-for-tax-year-2019-to-2020-that-have-not-complied-with-the-2017-money-laundering-regulations"
def headers = [:]
headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
headers.put("authority", "www.gov.uk")

HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build()

HttpRequest httpRequest = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .headers(headers.entrySet().stream()
                .flatMap(entry -> {
                    String headerName = entry.getKey()
                    String headerValue = entry.getValue()
                    return Stream.of(headerName, headerValue)
                })
                .toArray(String[]::new)
        )
        .build()
try {
    HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

    def htmlSourceCode = httpResponse.body()
    // println(htmlSourceCode)
    def businessesList = []
    def tableReg = /(?s)<tbody>(.*?)<\/tbody>/
    def tableMatcher = htmlSourceCode =~ tableReg

    while (tableMatcher.find()) {
        def table = tableMatcher.group(1)

        for (int i = 0; i <= 3; i++) {
            def rowReg = /(?s)<tr>(.*?)<\\/tr>/
            def rowMatcher = table =~ rowReg
            while (rowMatcher.find()) {
                def rowData = rowMatcher.group(1)
                // println(rowData)
                def tdReg = /(?s)<td>(.*?)<\/td>/;
                def matchData = rowData.findAll(tdReg) { match ->
                    match[1].replaceAll(/<[^>]*>/, '').trim()
                }
                //println(matchData)
                def entity = matchData.get(0).replaceAll(/&amp;/, "&").replaceAll(/\s+/, " ")
                def address = matchData.get(1).replaceAll(/\s+/, " ")
                def penalty = matchData.get(4).replaceAll(/\s+/, " ")
                def description = matchData.get(3).replaceAll(/\s+/, " ")

                //println(description)

                def businessData = [
                        entityName             : entity,
                        registeredAddress: address,
                        penaltyAmount    : penalty,
                        description      : description
                ]

                println(businessData)
                businessesList << businessData
            }

            def writer = new StringWriter()
            def xmlBuilder = new MarkupBuilder(writer)
            xmlBuilder.businesses() {
                businessesList.each { businessData ->
                    business() {
                        "Entity" businessData.entityName
                        "Address" businessData.registeredAddress
                        "URL" url
                        "Remark" "Penalty Amount: " + businessData.penaltyAmount
                        "Description" businessData.description
                        "Date" "NO EVENT DATE"
                    }
                }
            }

            def xmlString = writer.toString()
            Path outputFilePath = Paths.get("output.xml")
            Files.write(outputFilePath, xmlString.getBytes())
        }
    }


} catch (Exception e) {
    e.stackTrace
}