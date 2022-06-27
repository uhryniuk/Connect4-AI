package c4.endpoints.get;

import c4.server.response.Endpoint;

/**
 * This is a test class, not a part of server framework.
 */
public class AllData extends Endpoint {
    public AllData(){
        // Not sure if these are even needed.
        setRoute("/all-data");
        setMethod("GET");
    }

    @Override
    public String getResponse(){
        // Whatever queries can be set here.
        // Or they can be preprocessed by the instance creation.
        setResponse("you can cache ur response too");
        return "THIS IS THE RESPONSE";
    }
    
}
