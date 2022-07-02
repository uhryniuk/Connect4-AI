package c4.server.methods;

import java.io.OutputStream;
import java.io.IOException;
import c4.server.endpoint.Header;
import com.sun.net.httpserver.HttpExchange;

import c4.server.endpoint.Endpoint;
// Transform this into singleton along with the POST method.
public class Get implements IRequestMethod{
    public void respond(HttpExchange ex, Endpoint resObj) throws IOException{
        try{

            String response = resObj.getResponse();
            for ( Header header : resObj.getHeaders() )
            {
                ex.getResponseHeaders().set(header.getKey(), header.getValue());
            }

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
