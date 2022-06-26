package c4;


import java.util.Scanner;
import c4.server.AIServer;
import c4.server.Handler;

/**
 * This is just a testing driver class.
 */
public class App 
{
    public static void main( String[] args )
    {
        AIServer ais = new AIServer();
        System.out.println("Server is created");
        ais.addEndpoint("/howdy", "GET");
        ais.addEndpoint("/test-post", "POST");
        Scanner kb = new Scanner(System.in);
        ais.start();

        String word = null;
        while (word != null){
            word = kb.next();
        }
        //ais.killServer();
    }
}
