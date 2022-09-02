package c4.boardAI.minimax;

import c4.boardAI.Board;

/*
 * DTO to convey loads of info down the call stack.
 */
public class Evaluation{
    
    private Board board = null;         // The board which was evaluated.
    private int value;                  // Value which the board yielded.

    public Evaluation(Board board, int value){
        this.board = board;
        this.value = value;
    }

    public Board getBoard(){return this.board;}

    public int getValue(){return this.value;}
    
    public Board getParentBoard(){return this.board.getParentBoard();}
}
