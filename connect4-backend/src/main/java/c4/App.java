package c4;

import java.util.Scanner;
import c4.server.AIServer;
import c4.endpoints.get.*;
import c4.endpoints.post.*;

/**
 * This is just a testing driver class.
 */
public class App 
{
    public static void main( String[] args )
    {
        
        AIServer ais = new AIServer();
        // Thread serverThread = ais;s

        
        ais.addEndpoint(new BlankBoard());
        ais.addEndpoint(new BoardMove());
        ais.addEndpoint(new GetAllAiTypes());
        ais.start();

        Scanner kb = new Scanner(System.in);

        String word = "";
        while (!word.equals("kill")){
            word = kb.next();
            
        }
        kb.close();
        ais.killServer();
        
    }
}
