package c4.boardAI.minimax;

import java.util.ArrayList;

import c4.boardAI.Board;

public class Minimax {
    private static Minimax ai = null; // Singleton of the AI, so memory is consistent.
    private final static int DEPTH = 4;

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
     * Generates the AI's response to return for the HTTP request.
     * @param board Current state of the board.
     * @return      Optimal Move to make.
     */
    public Board getResponse(Board board){
        System.out.println("\nStill need to add a logger :/");
        return getOptimalMove(board);
    }
    
    /**
     * Gets the Optimal Move for the AI to make
     * @param board Current state of the board.
     * @return      Best possible response in the form of a board.
     */
    public Board getOptimalMove(Board board){
        boolean isAI = true;
        return minimax(board, DEPTH, isAI).getBoard();
    }

    /**
     * Minimax function. Finds the max and min of each layer of decision tree.
     * @param board Optimal board for the given layer.
     * @param depth Current depth of the board.
     * @param isAI  Whether the layer is max or min (max is AI, min is player.)
     * @return      Evaluation of the board.
     */
    private Evaluation minimax(Board board, int depth, boolean isAI){
        // Guard condition for depth.
        if (depth <= 0) return BoardEvaluator.evaluate(board); 
        
        ArrayList<Evaluation> evals = new ArrayList<>();
        
        // Evaluate all the moves.
        board.generatePossibleMoves(isAI)
            .forEach( (move) -> {
                // Assign parent boards based on depth!
                if ( depth == DEPTH ) move.setParentBoard(move); 
                else if ( depth < DEPTH ) move.setParentBoard(board.getParentBoard());
                
                evals.add(BoardEvaluator.evaluate(move)); 
                evals.add(minimax(move, depth-1, !isAI)); 
            });

        return isAI ? maximumBoard(evals) : minimumBoard(evals);
    }

    /**
     * Get the highest valued board.
     * @param evals All the Evaluated boards to compare.
     * @return      Evaluation with the higest board value.
     */
    private Evaluation maximumBoard(ArrayList<Evaluation> evals){
        Evaluation max = evals.get(0);
        for (Evaluation e : evals){
            if ( e.getValue() > max.getValue() ) max = e;
        }
        return max;
    }

    /**
     * Get the lowest valued board.
     * @param evals All the Evaluated boards to compare.
     * @return      Evaluation with the lowest board value.
     */
    private Evaluation minimumBoard(ArrayList<Evaluation> evals){
        Evaluation min = evals.get(0);
        for (Evaluation e : evals){
            if ( e.getValue() < min.getValue() ) min = e;
        }
        return min;
    }
}
