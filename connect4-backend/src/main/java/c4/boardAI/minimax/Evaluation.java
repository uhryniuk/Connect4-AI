package c4.boardAI.minimax;

import c4.boardAI.Board;

/*
 * Special Data Class to convey loads of info down the call stack.
 */
public class Evaluation{
    
    private Board board = null;         // The board to return
    private int value;                  // Value which the board yeilded.
    private int depth = 0;              // Depth of searching
    private boolean isAI = false;       // Board repersents an AI
    
    // May delete one of these, not sure which one is more relevant.
    // Do I need to keep track of the depth?
    // We shall see.
    // Perhaps turn this into a build at this point.
    public Evaluation(Board board, int value, int depth, boolean isAI){
        this.board = board;
        this.value = value;
        this.depth = depth;
        this.isAI = isAI;
    }

    public Evaluation(Board board, int value, boolean isAI){
        this.board = board;
        this.value = value;
        this.isAI = isAI;
    }

    public Board getBoard(){return this.board;}
    public int getValue(){return this.value;}
    public int getDepth(){return this.depth;}
    public Board getParentBoard(){return this.board.getParentBoard();}
}
