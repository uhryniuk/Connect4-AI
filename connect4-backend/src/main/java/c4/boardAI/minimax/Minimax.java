package c4.boardAI.minimax;

import java.util.ArrayList;

import c4.boardAI.Board;

public class Minimax {
    private static Minimax ai = null; // Singleton of the AI, so memory is consistent.
    private final static int DEPTH = 4;
    private final static int WIN_BOUNDARY = 4;

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

    public Board getResponse(Board board){
        System.out.println("\nAI is thinking...");

        Board b = getOptimalMove(board);

        System.out.println("AI responded...");
        
        return b;
    }

    public Board getOptimalMove(Board board){
        boolean isAI = true;
        Evaluation e = minimax(board, DEPTH, isAI);
        System.out.println("VALUE: "+e.getValue());
        for (String[] s : e.getBoard().getBoard()){
            for ( String ss : s ){
                System.out.print(ss+" ");
            }
            System.out.println("");
        }
        System.out.println("------------------");
        for (String[] s : e.getParentBoard().getBoard()){
            for ( String ss : s ){
                System.out.print(ss+" ");
            }
            System.out.println("");
        }
        return e.getBoard();
    }

    private Evaluation minimax(Board board, int depth, boolean isAI){
        // Guard condition for depth.
        if (depth <= 0) return BoardEvaluator.evaluate(board, isAI, depth); 
        
        // Always evaluate the current board;
        ArrayList<Evaluation> evals = new ArrayList<>();
        
        // Evaluate all the moves, based on their type.
        // Board typing may be redundant, make judgement later.
        board.generatePossibleMoves(isAI)
            .forEach( (move) -> {
                // Assign parent boards based on depth!
                if ( depth == DEPTH ) move.setParentBoard(move); 
                else if ( depth < DEPTH ) move.setParentBoard(board.getParentBoard());
                
                evals.add(BoardEvaluator.evaluate(move, isAI, depth)); // evaluate based on player type
                evals.add(minimax(move, depth-1, !isAI)); // Recurse finding next idea board.
            });

        return isAI ? maximumMove(evals) : minimumMove(evals);
    }

    private Evaluation maximumMove(ArrayList<Evaluation> evals){
        Evaluation max = evals.get(0);
        for (Evaluation e : evals){
            if ( e.getValue() > max.getValue() ) max = e;
        }
        return max;
    }

    private Evaluation minimumMove(ArrayList<Evaluation> evals){
        Evaluation min = evals.get(0);
        for (Evaluation e : evals){
            if ( e.getValue() < min.getValue() ) min = e;
        }
        return min;
    }
}
