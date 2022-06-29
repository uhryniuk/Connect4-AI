package c4.endpoints.get;

import com.google.gson.Gson;

import c4.server.endpoint.Endpoint;

/**
 * Get's a blank board for the Connect 4 game.
 */
public class BlankBoard extends Endpoint {
    public BlankBoard(){
        setMethod("GET");
        setRoute("/api/blank-board");
        setResponse(this.constructBlankBoard());
    }
    public String constructBlankBoard(){
        String[][] newBoard = new String[6][7];
        for(int i = 0; i < newBoard.length; i++){

            for(int j = 0; j < newBoard[0].length; j++){
                newBoard[i][j] = "1";
            }

        }
        Gson gson = new Gson();
        return gson.toJson(newBoard, String[][].class);
    }
}
