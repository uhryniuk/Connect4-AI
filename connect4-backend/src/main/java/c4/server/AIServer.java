package c4.server;

import com.sun.net.httpserver.HttpServer;
import c4.server.endpoint.Endpoint;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.io.IOException;
import java.util.HashSet;

public class AIServer extends Thread {
   
    // Make the server a singleton, this way easy to access all over.
    private HttpServer server;
    private int PORT = 8050;
    private ExecutorService threadPool;
    private HashSet<Endpoint> endpoints = new HashSet<>();
    // private Thread serverThread;

    public AIServer(){
        System.out.println("Server is being consructed..");
        try {
            // Create server and set thread count.
            this.server = HttpServer.create(new InetSocketAddress(PORT), 0);
            this.threadPool = Executors.newFixedThreadPool(1);
            this.server.setExecutor(this.threadPool);
            
        } catch (IOException e) {
            System.out.println("Failure to start server.");
        } 
    }

    // For "inline" approach to adding response.
    // Ideally this would be a callback.
    public void setResponse(String route, String response){}

    public void addEndpoint(Endpoint endpoint){
        Handler h = new Handler(endpoint);
        endpoints.add(endpoint); // Add hanlding for collisions.
        this.server.createContext(h.getRoute(), h);
    }
   
    public void start(){
        this.server.start();
        System.out.println("The following endpoints are available: \nRoute:\t\tMethod:");
        for(Endpoint e : this.endpoints){
            System.out.println(e.getRoute()+"\t"+e.getMethod());
        }
        // this.run();
    }

    public void killServer(){
        this.server.stop(1);
        this.threadPool.shutdownNow();
        System.out.println("----Server shutdown safely!----");
    }

    public void run(){
        this.server.start();
    }

}
