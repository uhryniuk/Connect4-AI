package c4.server.endpoint;

public abstract class Endpoint {
    
    private String ROUTE;
    private String METHOD;
    private String RESPONSE = "";
    private String REQUEST_BODY = "";

    public Endpoint(){
        // Check if all of them are null
    }

    protected void setRoute(String route){
        this.ROUTE = route;
    }
    
    protected void setMethod(String method){
        this.METHOD = method;
    }
    
    protected void setResponse(String response){
        this.RESPONSE = response;
    }

    public void setRequestBody(String req){
        this.REQUEST_BODY = req;
    }
    
    public String getRoute(){
        return this.ROUTE;
    }

    public String getMethod(){
        return this.METHOD;
    }
    
    public abstract String getResponse();

    public String getRequestBody(){
        return this.REQUEST_BODY;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Route: ");
        sb.append(this.ROUTE);
        sb.append("Method: ");
        sb.append(this.METHOD);
        sb.append("Response: ");
        sb.append(this.RESPONSE);

        return sb.toString();
    }
}
