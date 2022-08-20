package c4.boardAI.minimax;

import c4.boardAI.Board;

/*
 * Special Data Class to convey loads of info down the call stack.
 */
public class Evaluation{
    
    private Board board = null;         // The board to return
    private int value;                  // Value which the board yeilded.
    private int depth = 0;              // Depth of searching
    

    public Evaluation(Board board, int value){
        this.board = board;
        this.value = value;
    }

    public Board getBoard(){return this.board;}
    public int getValue(){return this.value;}
    public int getDepth(){return this.depth;}
    public Board getParentBoard(){return this.board.getParentBoard();}
}
