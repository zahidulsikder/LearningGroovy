import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProblemF {
    public static void main(String[] args) {
        String urlLink = "https://www.interpol.int/How-we-work/Notices/View-Red-Notices";
        try {
            URL url = new URL(urlLink);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer htmlText = new StringBuffer();
                String line;
                while ((line = reader.readLine())!=null){
                    htmlText.append(line).append("\n");
                }
                System.out.println(htmlText);
            }else {
                System.out.println("Something is wrong and your status code is " + responseCode);
            }
            urlConnection.disconnect();
        }catch (MalformedURLException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
