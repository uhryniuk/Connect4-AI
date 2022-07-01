package c4;

import java.util.Scanner;
import c4.server.AIServer;
import c4.endpoints.get.AllData;
import c4.endpoints.get.BlankBoard;
import c4.endpoints.post.AddData;
import c4.endpoints.post.BoardMove;
/**
 * This is just a testing driver class.
 */
public class App 
{
    public static void main( String[] args )
    {
        
        AIServer ais = new AIServer();
        // Thread serverThread = ais;s

        ais.addEndpoint(new AllData());
        ais.addEndpoint(new AddData());
        ais.addEndpoint(new BlankBoard());
        ais.addEndpoint(new BoardMove());

        Scanner kb = new Scanner(System.in);
        ais.start();
        // serverThread.run();

        String word = "faart";
        while (!word.equals("kill")){
            word = kb.next();
            
        }
        kb.close();
        ais.killServer();
        
    }
}
