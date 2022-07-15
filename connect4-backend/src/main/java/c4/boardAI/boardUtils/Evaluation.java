package c4.boardAI.boardUtils;

/*
 * Special Data Class to convey loads of info down the call stack.
 */
public class Evaluation{
    
    private Board board = null;         // The board to return
    private int value;                  // Value which the board yeilded.
    private int depth;                  // Depth of searching
    private boolean isWinner = false;   // Flag if this Eval is a winner or not.
    
    public Evaluation(Board board, int value, int depth, boolean isWinner){
        this.board = board;
        this.value = value;
        this.depth = depth;
        this.isWinner = isWinner;
    }

    public Board getBoard(){return this.board;}
    public int getValue(){return this.value;}
    public int getDepth(){return this.depth;}
    public boolean isWinner(){return this.isWinner;}
}
