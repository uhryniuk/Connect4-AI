package c4.boardAI.minimax;

import c4.boardAI.Board;

/*
 * Special Data Class to convey loads of info down the call stack.
 */
public class Evaluation{
    
    private Board board = null;         // The board to return
    private int value;                  // Value which the board yeilded.

    public Evaluation(Board board, int value){
        this.board = board;
        this.value = value;
    }

    public Board getBoard(){return this.board;}
    public int getValue(){return this.value;}
    public Board getParentBoard(){return this.board.getParentBoard();}
}
