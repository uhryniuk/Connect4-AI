package c4.boardAI.minimax;

import java.util.ArrayList;

import c4.boardAI.boardUtils.Board;
import c4.boardAI.boardUtils.BoardEvaluator;
import c4.boardAI.boardUtils.Evaluation;

public class Minimax {
    private static Minimax ai = null; // Singleton of the AI, so memory is consistent.
    private final static int MAX_RECURSION_DEPTH = 3;
    private final static int WIN_BOUNDARY = 4;
    final int START_DEPTH = 1;

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

    private Evaluation minimax(Board board, boolean isMax, int depth){
        final boolean IS_AI = isMax;

        // Get all possiblities for next layer
        ArrayList<Board> layer = board.generatePossibleMoves(IS_AI);
        layer.forEach( (b) -> b.setParentBoard(board.getParentBoard()) ); // Make sure the parentBoard is a root board.

        // Evaluate all of the layer, either best or worst
        Evaluation nextMove = isMax ? getBestEvaluation(layer, depth) : getWorstEvaluation(layer, depth);
        
        // Recurse all possibilities
        if ( depth < MAX_RECURSION_DEPTH ){
            // init next layer
            ArrayList<Evaluation> nextLayer = new ArrayList<>();

            // run minimax on each possible board.
            layer.forEach( (currBoard) -> nextLayer.add( minimax(currBoard, !isMax, depth+1) ));

            // Compare against current layer's best board.
            for ( Evaluation eval : nextLayer ) {
                // Either min or max, magic happens here.
                if ( isMax ){
                    if ( eval.getValue() > nextMove.getValue() ) nextMove = eval;
                } else {
                    if ( eval.getValue() < nextMove.getValue() ) nextMove = eval;
                }
            }
        }
        
        // Returning the final best evaluation.
        return nextMove;
    }

    private Board getOptimalMove(Board board){
        // Always trying to get the AI's optimal move.
        final boolean IS_MAX = false;
        final boolean IS_AI = true;
        // Init minimax, finding best possible move.
        ArrayList<Evaluation> nextMoves = new ArrayList<>();
        ArrayList<Board> topLayer = board.generatePossibleMoves(IS_AI);

        topLayer.forEach( (b) -> {
            b.setParentBoard(b);
            nextMoves.add(minimax(b, IS_MAX, START_DEPTH));
        });

        Evaluation optimalMove = nextMoves.get(0);
        for ( Evaluation eval : nextMoves ){
            if ( eval.getValue() > optimalMove.getValue() ) optimalMove = eval;
        }

        return optimalMove.getParentBoard();
    }

    public Board getResponse(Board board){
        return getOptimalMove(board);
    }

    private ArrayList<Evaluation> evaluateBoards(ArrayList<Board> boards, String playerVal, int depth){
        ArrayList<Evaluation> evaluations = new ArrayList<>();
        boards.forEach( (board) -> evaluations.add(new BoardEvaluator(playerVal, depth, board).evaluate()) );
        return evaluations;
    }

    private Evaluation getBestEvaluation(ArrayList<Board> boards, int depth){
        final String AI_VAL = "2";
        ArrayList<Evaluation> evals = evaluateBoards(boards, AI_VAL, depth);
        Evaluation bestEval = evals.get(0);

        for ( Evaluation eval : evals ){
            if ( eval.getValue() > bestEval.getValue() ) bestEval = eval;
        }
        return bestEval;
    }

    private Evaluation getWorstEvaluation(ArrayList<Board> boards, int depth){
        final String PLAYER_VAL = "1";
        ArrayList<Evaluation> evals = evaluateBoards(boards, PLAYER_VAL, depth);
        Evaluation worstEval = evals.get(0);
        
        for ( Evaluation eval : evals ){
            if ( eval.getValue() > worstEval.getValue() ) worstEval = eval;
        }
        return worstEval;
    }
}
