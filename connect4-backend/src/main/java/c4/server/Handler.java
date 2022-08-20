package c4.server;

import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;

import c4.server.endpoint.Endpoint;
import c4.server.methods.IRequestMethod;
import c4.server.methods.MethodMapper;

import com.sun.net.httpserver.HttpExchange;

public class Handler implements HttpHandler {

    private String route;
    private IRequestMethod requestMethod;
    private Endpoint endpoint;

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

    public String getRoute(){
        return this.route;
    }

    public String getMethodType(){
        return this.requestMethod.getType();
    }

}
