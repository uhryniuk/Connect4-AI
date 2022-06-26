package c4.server.methods;

import java.io.OutputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

public class Get implements IRequestMethod{
    public void respond(HttpExchange ex, String response) throws IOException{
        try{
            ex.getResponseHeaders().set("Content-Type", "application/json");
            ex.sendResponseHeaders(200, response.length());

            OutputStream os = ex.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        } catch (IOException e){
            System.out.println("Get Handler died");
        }   
    }

    // May be redundant;
    public String getType(){
        return "GET";
    }
}
