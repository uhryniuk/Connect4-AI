package c4.server.methods;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

public class Post implements IRequestMethod{
    public void respond(HttpExchange ex, String response) throws IOException{
        try{
            ex.getResponseHeaders().set("Content-Type", "application/json");
            
            InputStream is = ex.getRequestBody();
            
            int currentChar;
            StringBuilder sb = new StringBuilder();
            while((currentChar = is.read()) != -1 ){
                char castedChar = (char)currentChar;
                sb.append(castedChar);
            }

            response = sb.toString();
            System.out.println(sb.toString());
            OutputStream os = ex.getResponseBody();
            ex.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
            os.flush();
            os.close();
            is.close(); 
        } catch (IOException e){
            System.out.println("Get Handler died");
        }   
    }

    // May be redundant;
    public String getType(){
        return "POST";
    }
}
