package c4.endpoints.post;

import c4.boardAI.Board;
import c4.server.endpoint.Endpoint;
import java.util.Random;

import com.google.gson.Gson;

public class BoardMove extends Endpoint{
    public BoardMove(){
        setRoute("/api/board-move");
        setMethod("POST");
    }

    public String makeRandomMove(String boardJSON){
        Gson gson = new Gson();
        String[][] board = gson.fromJson(boardJSON, String[][].class);
        Random rand = new Random();
        int randRow = rand.nextInt(6);
        int randCol = rand.nextInt(7);

        Board temp = new Board(board);
        // board[randRow][randCol] = "2";

        for(int i = 5; i >= 0; i--){
            if (board[i][randCol].equals("0")){
                board[i][randCol] = "2";
                break;
            }
        }

        return gson.toJson(board, String[][].class);
    }

    @Override
    public void processData(){
        String newResponse = this.makeRandomMove(this.getRequestBody());
        this.setResponse(newResponse);
    }
}
