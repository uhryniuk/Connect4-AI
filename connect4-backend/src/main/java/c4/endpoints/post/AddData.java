package c4.endpoints.post;

import c4.server.endpoint.Endpoint;

/**
 * This is a test class, not a part of server framework.
 */
public class AddData extends Endpoint {
    private int[][] board = null;
    public void setBoardData(int[][] board){this.board = board;}
    public AddData(){
        setRoute("/api/add-data");
        setMethod("POST");
        // Add more to describe what is expected from POST data?
    }

    @Override
    public String getResponse(){
        setResponse("Added!");
        return "Added!";
    }
}
