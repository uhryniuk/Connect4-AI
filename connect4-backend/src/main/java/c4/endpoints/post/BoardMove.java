package c4.endpoints.post;

import c4.server.endpoint.Endpoint;
import com.google.gson.Gson;

import c4.boardAI.Board;
import c4.boardAI.minimax.Minimax;

public class BoardMove extends Endpoint{
    public BoardMove(){
        setRoute("/api/board-move");
        setMethod("POST");
        addHeader("Content-Type", "applcation/json");
    }

    public String makeRandomMove(String boardJSON){
        Gson gson = new Gson();
        String[][] boardObj = gson.fromJson(boardJSON, String[][].class);
        Board board = Minimax.getAI().getResponse(new Board(boardObj));
        
        // Send the parent board, as it's the best choice to make in first layer.
        
        for (String[] s : board.getParentBoard().getBoard()){
            for ( String ss : s ){
                System.out.print(ss+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");

        return gson.toJson(board.getParentBoard().getBoard(), String[][].class);
        // return gson.toJson(board.getBoard(), String[][].class);
    }

    @Override
    public void processData(){
        String newResponse = this.makeRandomMove(this.getRequestBody());
        this.setResponse(newResponse);
    }
}
