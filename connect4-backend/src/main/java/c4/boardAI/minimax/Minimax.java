package c4.boardAI.minimax;

import c4.boardAI.Board;
import c4.boardAI.BoardEvaluator;

import java.util.ArrayList;
import java.util.HashMap;

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
    public static Minimax getAI()
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

    private int minimax(Board board, int depth, boolean isMax, int maxValue)
    {
        // Validate the board
        // did we find a winner? if so return this board.
            // We want the "shortest winner path"
        
        // IsMax, then do min layer next.

        // Otherwise, do a max layer, have toiterate voer all of the possible outcomes.
        
        
        
        // Hardcoding the number of layers to traverse
        // 26 layers is quite insane, thats almost half the board so like 26! options
        int ttt = new BoardEvaluator().calculate(board, depth);
        if ( depth == 6 || maxValue >= 4 || ttt >= 4) // Try a lower lever.
        {
            return maxValue;
        } else {
            maxValue = new BoardEvaluator().calculate(board, depth);
        }
        
        ArrayList<Board> boards = new ArrayList<>();
        for (int[] positions : board.getPossiblePositions()){
            Board newBoard = board.copyBoard();
            newBoard.makeMove(positions[0], positions[1]);
            boards.add(newBoard);
        }

        HashMap<Board, Integer> boardValueMap = new HashMap<>();

        for (Board b : boards)
        {
            int currVal = -1;
            if ( isMax )
            {
                currVal = minimax(b, depth+1, false, maxValue);
                boardValueMap.put(b,currVal);
    
            } else {
                currVal = minimax(b, depth+1, true, maxValue);
                boardValueMap.put(b,currVal);
            }
            if ( currVal >= 4 ) return currVal;
        }

        for (Board b : boardValueMap.keySet()){
            if ( isMax )
            {
                maxValue = Math.max(maxValue, boardValueMap.get(b));
            } else {
                maxValue = Math.min(maxValue, boardValueMap.get(b));
            }
            
        }

        return maxValue;
    }


    public Board getResponse(Board board){
        
        ArrayList<Board> boards = new ArrayList<>(); 
        for (int[] positions : board.getPossiblePositions()){
            Board newBoard = board.copyBoard();
            newBoard.makeMove(positions[0], positions[1]);
            boards.add(newBoard);
        }

        HashMap<Board, Integer> map = new HashMap<>();
        for ( Board b : boards )
        {   
            int boardValue = minimax(board, 1, true, 0);
            if (boardValue >= 4){
                return b;
            }
            
            map.put(b, boardValue);
        }
        int max = -1;
        Board returnBoard = null;
        for ( Board b : map.keySet())
        {
            int currentMax = map.get(b);
            if ( currentMax >= 4 ) return b;
            if ( currentMax > max ){
                max = currentMax;
                returnBoard = b;
            }

        }
        
        return returnBoard;
    }
}
