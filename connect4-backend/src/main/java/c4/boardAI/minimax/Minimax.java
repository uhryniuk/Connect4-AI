package c4.boardAI.minimax;

import c4.boardAI.boardUtils.Board;
import c4.boardAI.boardUtils.BoardEvaluator;
import c4.boardAI.boardUtils.Evaluation;

import java.util.ArrayList;
import java.util.HashMap;

public class Minimax {
    
    private static Minimax ai = null; // Singleton of the AI, so memory is consistent.

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


    /**
     *
     *  TODO This is a "dead" function
     *  Simply being kept here as reference as we revive funtionality is new function.
     * 
     */
    private Evaluation minimax(Board board, int depth, boolean isMax, int maxValue)
    {
        boolean isWinner = maxValue >= 4;
        // Set max recursion depth, increasing this sees further into the game.
        if ( depth == 5 )
        {   // If so return the max value, since we don't calculate any further.
            return new Evaluation(board, maxValue, depth, isWinner);
        }

        Evaluation currentEval = new BoardEvaluator().calculate(board, depth);
        maxValue = currentEval.getValue();
        
        // Make list of boards with all possible moves.
        // ArrayList<Board> boards = getBoardPositions(board);

        // Create value map for each board.
        HashMap<Board, Evaluation> boardValueMap = new HashMap<>();
        // for (Board b : boards)
        // {
        //     // Set currVal as a dumbie value.
            
        //     // Maxing in minimax
        //     if ( isMax )
        //     {
        //         // Do the recursion and add it to the map.
        //         // Evaluation currVal = Math.max(minimax(b, depth+1, false, maxValue).getValue(), maxValue);
        //         // boardValueMap.put(b,currVal);
    
        //     // Minning in minimax
        //     } else {
        //         // currVal = Math.min(minimax(b, depth+1, true, maxValue).getValue(), maxValue);
        //         // boardValueMap.put(b,currVal);
        //     }
        // }

        // Find the minimum or maximum of each node layer.
        for (Board b : boardValueMap.keySet()){
            if ( isMax )
            {
                // maxValue = Math.max(maxValue, boardValueMap.get(b));
            } else {
                // maxValue = Math.min(maxValue, boardValueMap.get(b));
            }
            
        }

        // Return the maximum after all computation.        
        return new Evaluation(board, maxValue, depth+1, isWinner);
    }


    // recursive limit set by two values: depth or if a line of len(maxValue) >= 4

    private Evaluation minimax(Evaluation currEval, boolean isMax){
        
        // Base Condition for returning our Boards
        if ( currEval.getDepth() == 3 || currEval.getValue() >= 4 ) return currEval;

        boolean isAI = !isMax; // So isMax is not used in weird spots.
        ArrayList<Board> childBoards = this.getBoardPositions(currEval.getBoard(), isAI);
        ArrayList<Evaluation> childEvals = new ArrayList<>();
        BoardEvaluator be = new BoardEvaluator();

        for (Board b : childBoards){
            
            if ( isMax ){
                childEvals.add(minimax( be.calculate(b, currEval.getDepth()+1), false ));
            } else {
                childEvals.add(minimax( be.calculate(b, currEval.getDepth()+1), true ));
            }
            
        }

        if ( isMax ){
            return this.getBestEvaluation(childEvals); 
        }
        // return this.getBestEvaluation(childEvals); 

        return this.getWorstEvaluation(childEvals);
    }
    
    /**
     * Get all boards based on all possible moves by AI
     * @param board Base board to see all possible moves.
     * @return List of all new possible boards.
     */
    private ArrayList<Board> getBoardPositions(Board board, boolean isAI)
    {
        ArrayList<Board> boards = new ArrayList<>(); 
        for (int[] positions : board.getPossiblePositions()){
            Board newBoard = board.copyBoard();
            newBoard.makeMove(positions[0], positions[1], isAI);
            boards.add(newBoard);
        }
        return boards;
    }

    /**
     * Generates and gets all possible board generations of current layer
     * @param boards All of the boards of the current layer
     * @return List of evaluated baords.
     */
    private Board getOptimalBoard(ArrayList<Board> boards)
    {
        HashMap<Evaluation, Board> evalMap = new HashMap<>();
        Evaluation bestEval = null;
        // Map all evals to their parent boards.
        for ( Board b : boards )
        {   
            Evaluation currEval = minimax(new BoardEvaluator().calculate(b, 1), true);
            evalMap.put(currEval, b);
            if ( bestEval == null ) 
                bestEval = currEval;
            else {
                boolean greaterOrEqualValue = bestEval.getValue() <= currEval.getValue();
                boolean lowerOrEqualDepth = bestEval.getDepth() <= currEval.getDepth();
                bestEval = greaterOrEqualValue && lowerOrEqualDepth ? currEval : bestEval;
            } 
                
        }

        return evalMap.get(bestEval);
    }

    /**
     * Obtains the highest valued board based on heuristic value and depth
     * @param evals List of all evalutaions in current layer.
     * @return Best possible evaluation.
     */
    private Evaluation getBestEvaluation(ArrayList<Evaluation> evals)
    {
        Evaluation bestBoard = evals.get(0);

        for ( Evaluation e : evals)
        {
            if (e.getValue() > bestBoard.getValue() && e.getDepth() < bestBoard.getDepth())
            {
                bestBoard = e;
            }

        }
        
        return bestBoard;
    }

    private Evaluation getWorstEvaluation(ArrayList<Evaluation> evals){
        Evaluation worstBoard = evals.get(0);

        for ( Evaluation e : evals)
        {
            if (e.getValue() < worstBoard.getValue() && e.getDepth() > worstBoard.getDepth())
            {
                worstBoard = e;
            }

        }
        return worstBoard;
    }

    /**
     * Response from the Minimax AI
     * @param board Current board state since the player made their move.
     * @return 
     */
    public Board getResponse(Board board){
        return getOptimalBoard( getBoardPositions( board, true ) );
    }
}
