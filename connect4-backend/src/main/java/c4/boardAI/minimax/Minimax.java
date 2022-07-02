package c4.boardAI.minimax;

import c4.boardAI.Board;

public class Minimax {
    
    // Minimaxing notes:
    /**
     * This should potentially be a singleton.
     * - One live isntance, that mutates with more and mroe requests.
     * - Provides us with stable memory usage, instead of spikes.
     * - For now, just get an AI working, regardless of speed.
     *   - Performance enchancements are a late stage battle.
     * 
     * Minimax Theory:
     * - Begin with a maxing node
     *   - Maxing of determined heuristics
     * - Follow with Minizming node
     *   - Again, determined by heuristics of the game.
     * - The player we are maximizing for, also needs to go first
     *  - We follow by doing max -> min -> max -> min
     *   - This follows for each layer of the tree.
     * 
     * 
     * 
     * 
     * 
     */

    
    private static Minimax ai = null; // Singleton of the AI, so memory is consistent.
    private Board currentBoard;

    /* Constructor */
    private Minimax(){}

    /**
     * Accessor function the minimax AI
     * @return The singleton of the minimax AI.
     */
    public Minimax getAI()
    {
        if ( ai == null )
        {
            ai = new Minimax();
        } 
        return ai;
    }

    public void updateBoard(Board newBoard)
    {
        this.currentBoard = newBoard;
    }
    
    private void minimax(Board board, int depth, boolean isMax)
    {
        
    }

    

}
