package c4.endpoints.post;

import c4.server.response.Endpoint;

/**
 * This is a test class, not a part of server framework.
 */
public class AddData extends Endpoint {
    public AddData(){
        setRoute("/add-data");
        setMethod("POST");
        // Add more to describe what is expected from POST data?
    }
    @Override
    public String getResponse(){
        setResponse("Added!");
        return "Added!";
    }
}
