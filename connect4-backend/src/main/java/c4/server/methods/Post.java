package c4.server.methods;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import c4.boardData.Board;
import c4.server.endpoint.Endpoint;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class Post implements IRequestMethod{
    // Transform this into a Singleton, same with the GET Method.
    // Perhaps make the IRequestMethod a singleton off the bat?
    public void respond(HttpExchange ex, Endpoint resObj) throws IOException{
        try{
            String response = resObj.getResponse();
            ex.getResponseHeaders().set("Content-Type", "application/json");
            // Potentially move this.
            ex.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            InputStream is = ex.getRequestBody();
            
            int currentChar;
            StringBuilder sb = new StringBuilder();
            while((currentChar = is.read()) != -1 ){
                char castedChar = (char)currentChar;
                sb.append(castedChar);
            }
            
            // Setting the request data and the response data.
            // This should take most of the time.
            resObj.setRequestBody(sb.toString());
            resObj.processData();
            response = resObj.getResponse();
            // Development, should remove.
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
