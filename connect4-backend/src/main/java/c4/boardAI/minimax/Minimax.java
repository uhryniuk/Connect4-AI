package c4.boardAI.minimax;

import c4.boardAI.Board;
import c4.boardAI.BoardEvaluator;
import c4.boardAI.Evaluation;

import java.util.ArrayList;
import java.util.HashMap;

public class Minimax {
    
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

    private Evaluation minimax(Board board, int depth, boolean isMax, int maxValue)
    {
        boolean isWinner = maxValue >= 4;
        // Set max recursion depth, increasing this sees further into the game.
        if ( depth == 2 )
        {   // If so return the max value, since we don't calculate any further.
            return new Evaluation(board, maxValue, depth, isWinner);
        } else {
            // Calculate the current value for this node at this depth.
            Evaluation currentEval = new BoardEvaluator().calculate(board, depth);
            maxValue = currentEval.getValue();
        }
        
        // Make list of boards with all possible moves.
        ArrayList<Board> boards = new ArrayList<>();

        // Create all boards and add them to the list.
        for (int[] positions : board.getPossiblePositions()){
            Board newBoard = board.copyBoard();
            newBoard.makeMove(positions[0], positions[1]);
            boards.add(newBoard);
        }

        // Create value map for each board.
        HashMap<Board, Integer> boardValueMap = new HashMap<>();
        for (Board b : boards)
        {
            // Set currVal as a dumbie value.
            int currVal = -1;
            // Maxing in minimax
            if ( isMax )
            {
                // Do the recursion and add it to the map.
                currVal = Math.max(minimax(b, depth+1, false, maxValue).getValue(), maxValue);
                boardValueMap.put(b,currVal);
    
            // Minning in minimax
            } else {
                currVal = Math.min(minimax(b, depth+1, true, maxValue).getValue(), maxValue);
                boardValueMap.put(b,currVal);
            }
            // if ( currVal >= 4 ) return currVal;
        }

        // Find the minimum or maximum of each node layer.
        for (Board b : boardValueMap.keySet()){
            if ( isMax )
            {
                maxValue = Math.max(maxValue, boardValueMap.get(b));
            } else {
                maxValue = Math.min(maxValue, boardValueMap.get(b));
            }
            
        }

        // Return the maximum after all computation.        
        return new Evaluation(board, maxValue, depth, isWinner);
    }


    public Board getResponse(Board board){
        
        ArrayList<Board> boards = new ArrayList<>(); 
        for (int[] positions : board.getPossiblePositions()){
            Board newBoard = board.copyBoard();
            newBoard.makeMove(positions[0], positions[1]);
            boards.add(newBoard);
        }

        ArrayList<Evaluation> evals = new ArrayList<>();
        for ( Board b : boards )
        {   
            Evaluation boardEval = minimax(b, 1, false, 0);
            evals.add(boardEval);
        }
        
        // Arbirarily assign the best board to begin with.
        Evaluation bestBoard = evals.get(0);

        for ( Evaluation e : evals)
        {
            if (e.getValue() >= bestBoard.getValue() && e.getDepth() <= bestBoard.getDepth())
            {
                bestBoard = e;
            }

        }
        System.out.println(evals.size());
        return bestBoard.getBoard();
    }
}
