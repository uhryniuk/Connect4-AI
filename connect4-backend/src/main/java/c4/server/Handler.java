package c4.server;

import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;

import c4.server.endpoint.Endpoint;
import c4.server.methods.IRequestMethod;
import c4.server.methods.MethodMapper;

import com.sun.net.httpserver.HttpExchange;

public class Handler implements HttpHandler {
// What type of design pattern would be good for this.
// Definitly implement a builder type for this.
// Where we can hit `.build()`
// which will complete and attachthe endpoint to the server.

    private String route;
    private IRequestMethod requestMethod;
    private Endpoint endpoint;

    // Still need to implement builder versions.
    public Handler(){}


    public Handler(Endpoint endpoint){
        this.route = endpoint.getRoute();
        this.requestMethod = MethodMapper.parseMethod(endpoint.getMethod());
        this.endpoint = endpoint;
    }

    @Override
    public void handle(HttpExchange ex){
        try{
            // Obviously this doesn't stay here.
            requestMethod.respond(ex, endpoint);
        } catch (IOException e) {
            System.out.println("Handler Died...");
        }
    }

    // This will add the endpoint to the Singleton server that is running.
    // Also checks to see if the builder was completed with all pieces.
        // If pieces are missing don't add it and throw error.
    public void expose(){}

    // Getters and Setters
    public String getRoute(){
        return this.route;
    }

    public String getMethodType(){
        return this.requestMethod.getType();
    }

}
