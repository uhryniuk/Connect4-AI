package c4.server.endpoint;

import java.util.ArrayList;

public abstract class Endpoint {
    
    private String route;
    private String method;
    private String response = "";
    private String requestBody = "";
    ArrayList<Header> headers = new ArrayList<>();

    public Endpoint(){
        // Check if all of them are null
    }

    protected void setRoute(String route){
        this.route = route;
    }
    
    protected void setMethod(String method){
        this.method = method;
    }
    
    protected void setResponse(String response){
        this.response = response;
    }

    public void setRequestBody(String req){
        this.requestBody = req;
    }
    
    public String getRoute(){
        return this.route;
    }

    public String getMethod(){
        return this.method;
    }
    
    public String getResponse(){
        return this.response;
    }

    public String getRequestBody(){
        return this.requestBody;
    }

    public ArrayList<Header> getHeaders(){
        return this.headers;
    }

    // Alternative functions

    public void addHeader(String key, String value){
        this.headers.add(new Header(key, value));
    }

    public void processData(){}

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Method:\t");
        sb.append(this.method);
        sb.append("Route: ");
        sb.append(this.route);
        

        return sb.toString();
    }
}
