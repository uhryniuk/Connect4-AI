package c4.endpoints.webpage;

import c4.server.endpoint.Endpoint;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


/*
 * Test class for servin webpages.
 */
public class Index extends Endpoint{
    public Index ()
    {
        setRoute("/");
        setMethod("GET");
        setResponse(getPageData());
        addHeader("Content-Type", "text/html");
    }

    public String getPageData()
    {
        String webpageData = null;
        try {
            // Need aboslute path because compiled files are else in diff dir.
            String filePath = "/home/dyl/prog/Connect4-AI/connect4-backend/src/main/java/c4/endpoints/webpage/index.html";
            File webpage = new File(filePath);
            if (!webpage.exists()) throw new FileNotFoundException("File does not exist.");
            
            Scanner file = new Scanner(webpage);
            StringBuilder sb = new StringBuilder();

            while (file.hasNextLine())
            {
                sb.append(file.nextLine());
            }
            webpageData = sb.toString();

        } catch (FileNotFoundException e){
            webpageData = "page not found";
        }
        return webpageData;
    }

}
