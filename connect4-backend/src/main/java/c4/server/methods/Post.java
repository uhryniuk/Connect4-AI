package c4.server.methods;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import c4.boardData.Board;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import c4.server.response.Endpoint;

public class Post implements IRequestMethod{
    public void respond(HttpExchange ex, Endpoint resObj) throws IOException{
        try{
            String response = resObj.getResponse();
            ex.getResponseHeaders().set("Content-Type", "application/json");
            Gson gson = new Gson();
            InputStream is = ex.getRequestBody();
            
            int currentChar;
            StringBuilder sb = new StringBuilder();
            while((currentChar = is.read()) != -1 ){
                char castedChar = (char)currentChar;
                sb.append(castedChar);
            }
            String s = sb.toString();
            int[] e = {1,2,3,4,5};
            
            System.out.println(gson.toJson(e));
            int[][] test = gson.fromJson(s, int[][].class);
            System.out.println(test + " " + s);
            for(int i = 0; i < test.length; i++){
                for(int j = 0; j < test[i].length; j++){
                    System.out.print(""+test[i][j]+" ");
                }
                System.out.println("\t"+test[i]);
            }
            // Setting the request data and the response data.
            // This should take most of the time.
            resObj.setRequestBody(sb.toString());
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
