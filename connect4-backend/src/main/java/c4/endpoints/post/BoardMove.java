package c4.endpoints.post;

import c4.boardAI.Board;
import c4.server.endpoint.Endpoint;
import java.util.Random;

import com.google.gson.Gson;
import c4.boardAI.minimax.Minimax;
import c4.boardAI.BoardEvaluator;

public class BoardMove extends Endpoint{
    public BoardMove(){
        setRoute("/api/board-move");
        setMethod("POST");
        addHeader("Content-Type", "applcation/json");
    }

    public String makeRandomMove(String boardJSON){
        Gson gson = new Gson();
        String[][] boardObj = gson.fromJson(boardJSON, String[][].class);
        // Random rand = new Random();
        // int randCol = rand.nextInt(7);

        Board board = new Board(boardObj);

        new BoardEvaluator().calculate(board, 0);
        board = Minimax.getAI().getResponse(board);
        // board[randRow][randCol] = "2";

        // for(int i = 5; i >= 0; i--){
        //     if (board[i][randCol].equals("0")){
        //         board[i][randCol] = "2";
        //         break;
        //     }
        // }
        
        return gson.toJson(board.getBoard(), String[][].class);
    }

    @Override
    public void processData(){
        String newResponse = this.makeRandomMove(this.getRequestBody());
        this.setResponse(newResponse);
    }
}
