package c4.boardAI.boardUtils;

import java.util.ArrayList;
import c4.boardAI.boardUtils.*;

/**
 * We can check if there is a winner, but we need to derive a "value"
 * for the winner. (so called heuristics.)
 */
public class BoardEvaluator {

    /* Constructor */
    public BoardEvaluator(){}
    
    /**
     * Calculates the value of the board on self defined criteria.\
     * 
     * Values above 0 are good moves
     * 
     * Values below zero will deminish odds further.
     * 
     * @param board Board to be evaluated
     * @return Integer value, higher is better, lower is worse.
     */
    public Evaluation evaluate(Board board, int depth){

        // This maxing process below can be optimzied
        // This is just for POC.

        int max = Integer.MIN_VALUE;

        max = Math.max(max, Math.max(this.enhanceEval(board, depth, "2"), this.enhanceEval(board, depth, "1")));

        boolean isWinner = max >= 4;
        return new Evaluation(board, max, depth);
    }

    public Evaluation calculate(Evaluation eval){
        return this.evaluate(eval.getBoard(), eval.getDepth());
    }

    /**
     * 
     * @param Board Eval value generated from Board Checking
     * @param String Eval value generated from Board Checking
     * @return Enhanced heuristics value from more dynamic weighting system.
     */
    private int enhanceEval(Board board, int depth, String playerVal){
        
        int sum = 0;
        final int[] FIB_SEQ = new int[] {0,2,3,5,8};
        final float[] PRIORITY_FACTORS = new float[] {1.8f, 1.5f, 1.3f};
        
        ArrayList<Integer> boardValues = getBoardValues(board, playerVal);
        for ( int i = 0; i < boardValues.size(); i++ ){

            int val = boardValues.get(i);

            // Guard condition,  creating bias for blocking the other user.
            if ( playerVal.equals("1") ){
                sum += (int) Math.ceil( (FIB_SEQ[val] - depth) * PRIORITY_FACTORS[i] );
            } else{
                sum += (int) Math.ceil( (FIB_SEQ[val] - depth) * PRIORITY_FACTORS[i] );
            }

            

        }

        // int sum = checkHorizontal(board.getBoard(), playerVal)
        //         + checkVertical(board.getBoard(), playerVal)
        //         + checkDiagonal(board.getBoard(), playerVal);
        
        return sum ;//- (int)(depth*1.5f);
    }


    private ArrayList<Integer> getBoardValues(Board board, String playerVal){
        ArrayList<Integer> arr = new ArrayList<>();

        // The order in which these are added indicates their choice priority.
        arr.add(placeHorizontal(board.getBoard(), playerVal));
        arr.add(placeDiagonal(board.getBoard(), playerVal));
        arr.add(placeVertical(board.getBoard(), playerVal));

        return arr;
    }

    /**
     * Returns max value seen for the AI  player in horizontal axis.
     * @return Max value found.
     */
    private int placeHorizontal(String[][] board, String playerVal){

        // Number of times value has been seen consecutively in the row.
        int valueCount = 0;
        int maxValueCount = 0;
        // Loop over all indexes of the matrix. 
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[0].length; j++ )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(playerVal) ) {
                    valueCount++;
                    maxValueCount = Math.max(valueCount, maxValueCount);
                }
                else { // Otherwise, mark the new currentValue
                    valueCount = 0;
                }
            }
        }
        
        return maxValueCount >= 4 ? 4 : maxValueCount;
    }

    /**
     * Checks for max value in the vertical axis.
     * @return MAx count for AI value.
     */
    private int placeVertical(String[][] board, String playerVal){
        // Current value stored at [i][j] index.

        // Number of times value has been seen consecutively in the row.
        int valueCount = 0;
        int maxValueCount = 0;

        // Loop over all indexes of the matrix. 
        for ( int j = 0; j < board[0].length; j++ )
        {
            for ( int i = board.length-1; i >= 0; i-- )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(playerVal)  ) {
                    valueCount++;
                    maxValueCount = Math.max(valueCount, maxValueCount);
                }
                else { // Otherwise, mark the new currentValue
                    
                    valueCount = 0;
                }
            }
        }

        return maxValueCount >= 4 ? 4 : maxValueCount;
    }


    /**
     * Checks the left and right digaonal directions for a winner.
     * @return Return the value of the winner, otherwise null
     */
    private int placeDiagonal(String[][] board, String playerVal){
        
        int max = 0;

        // Check starting from the top cells of the board
        for ( int j = 0; j < board[0].length; j++ )
        {
            // Ex Path: 0,0 -> 1,1 -> 2,2
            int horizontalLeft = recurseDiagonal(0+1, j+1, 1, true, board, 0, playerVal);
            max = Math.max(horizontalLeft, max);

            // Ex Path: 0,6 -> 1,5 -> 2,4
            int horizontalRight = recurseDiagonal(0+1, j-1, 1, false, board, 0, playerVal);
            max = Math.max(horizontalRight, max);

        }

        // Check starting from the left and right sides of the board.
        for ( int i = 0; i < board.length; i++ )
        {
            // Ex Path: 2,0 -> 3,1 -> 4,2
            int verticalLeft = recurseDiagonal(i+1, 0+1, 1, true, board, 0, playerVal);
            max = Math.max(verticalLeft, max);            

            // Ex Path: 2,6 -> 3,5 -> 4,4
            int verticalRight = recurseDiagonal(i+1, 6-1, 1, false, board, 0, playerVal);
            max = Math.max(verticalRight, max);
        }

        return max;
    }

    /**
     * Recursive function to DFS all the nodes diagonally.
     * @param value      Current Value we are searching to match
     * @param row        Current row
     * @param column     Current column
     * @param depthCount Depth count for number of cells in current match.
     * @return Return the current value of the winner, otherwise null.
     */
    private int recurseDiagonal(int row, int column, int depthCount, boolean isLeft, String[][] board, int maxDepthCount, String playerVal)
    {
        // Base case for out of bounds
        if (row >= board.length || row < 0 || column >= board[0].length || column < 0)
        {
            return maxDepthCount;
        }

        if ( isLeft )
        {
            if ( board[row][column].equals(playerVal) )
            {
                depthCount++;
                // Hardcode max depth that it will search for the time being
                maxDepthCount = Math.max(maxDepthCount, depthCount);
                maxDepthCount = Math.max(recurseDiagonal(row+1, column+1, depthCount, isLeft, board, maxDepthCount, playerVal), maxDepthCount);
            
            } else {
                maxDepthCount = Math.max(recurseDiagonal(row+1, column+1, 1, isLeft, board, maxDepthCount, playerVal), maxDepthCount);
            }

        } else {
            if ( board[row][column].equals(playerVal) )
            {
                depthCount++;
                maxDepthCount = Math.max(maxDepthCount, depthCount);
                maxDepthCount = Math.max(recurseDiagonal( row+1, column-1, depthCount, isLeft, board, maxDepthCount, playerVal), maxDepthCount);
            } else { // Otherwise, keep recursing to search for new winner.
                maxDepthCount = Math.max(recurseDiagonal(row+1, column-1, 1, isLeft, board,maxDepthCount, playerVal), maxDepthCount);
            }
        }

        return maxDepthCount >= 4 ? 4 : maxDepthCount;
    }


    /**
     * Returns max value seen for the AI  player in horizontal axis.
     * @return Max value found.
     */
    private int blockHorizontal(String[][] board, String playerVal){

        // Number of times value has been seen consecutively in the row.
        int valueCount = 0;
        int maxValueCount = 0;
        // Loop over all indexes of the matrix. 
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[0].length; j++ )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(playerVal) ) {
                    valueCount++;
                    maxValueCount = Math.max(valueCount, maxValueCount);
                }
                else { // Otherwise, mark the new currentValue
                    valueCount = 0;
                }
            }
        }
        
        return maxValueCount >= 4 ? 4 : maxValueCount;
    }

    /**
     * Checks for max value in the vertical axis.
     * @return MAx count for AI value.
     */
    private int blockVertical(String[][] board, String playerVal, ArrayList<Integer[]> positions){
        // Iterate over the Matrix twice
            // First time normal direction,
            // Second time in reverse
            // this is to add missing indexes that we may have missed.
            // 

        // Number of times value has been seen consecutively in the row.
        int valueCount = 0;
        int maxValueCount = 0;

        // Loop over all indexes of the matrix. 
        for ( int j = 0; j < board[0].length; j++ )
        {
            for ( int i = board.length-1; i >= 0; i-- )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(playerVal)  ) {
                    valueCount++;
                    maxValueCount = Math.max(valueCount, maxValueCount);
                }
                else { // Otherwise, mark the new currentValue
                    
                    valueCount = 0;
                }
            }
        }

        return maxValueCount >= 4 ? 4 : maxValueCount;
    }


    /**
     * Checks the left and right digaonal directions for a winner.
     * @return Return the value of the winner, otherwise null
     */
    private int blockDiagonal(String[][] board, String playerVal){
        
        int max = 0;

        // Check starting from the top cells of the board
        for ( int j = 0; j < board[0].length; j++ )
        {
            // Ex Path: 0,0 -> 1,1 -> 2,2
            int horizontalLeft = recurseDiagonal(0+1, j+1, 1, true, board, 0, playerVal);
            max = Math.max(horizontalLeft, max);

            // Ex Path: 0,6 -> 1,5 -> 2,4
            int horizontalRight = recurseDiagonal(0+1, j-1, 1, false, board, 0, playerVal);
            max = Math.max(horizontalRight, max);

        }

        // Check starting from the left and right sides of the board.
        for ( int i = 0; i < board.length; i++ )
        {
            // Ex Path: 2,0 -> 3,1 -> 4,2
            int verticalLeft = recurseDiagonal(i+1, 0+1, 1, true, board, 0, playerVal);
            max = Math.max(verticalLeft, max);            

            // Ex Path: 2,6 -> 3,5 -> 4,4
            int verticalRight = recurseDiagonal(i+1, 6-1, 1, false, board, 0, playerVal);
            max = Math.max(verticalRight, max);
        }

        return max;
    }

    /**
     * Recursive function to DFS all the nodes diagonally.
     * @param value      Current Value we are searching to match
     * @param row        Current row
     * @param column     Current column
     * @param depthCount Depth count for number of cells in current match.
     * @return Return the current value of the winner, otherwise null.
     */
    private int recurseBlockDiagonal(int row, int column, int depthCount, boolean isLeft, String[][] board, int maxDepthCount, String playerVal)
    {
        // Base case for out of bounds
        if (row >= board.length || row < 0 || column >= board[0].length || column < 0)
        {
            return maxDepthCount;
        }

        if ( isLeft )
        {
            if ( board[row][column].equals(playerVal) )
            {
                depthCount++;
                // Hardcode max depth that it will search for the time being
                maxDepthCount = Math.max(maxDepthCount, depthCount);
                maxDepthCount = Math.max(recurseDiagonal(row+1, column+1, depthCount, isLeft, board, maxDepthCount, playerVal), maxDepthCount);
            
            } else {
                maxDepthCount = Math.max(recurseDiagonal(row+1, column+1, 1, isLeft, board, maxDepthCount, playerVal), maxDepthCount);
            }

        } else {
            if ( board[row][column].equals(playerVal) )
            {
                depthCount++;
                maxDepthCount = Math.max(maxDepthCount, depthCount);
                maxDepthCount = Math.max(recurseDiagonal( row+1, column-1, depthCount, isLeft, board, maxDepthCount, playerVal), maxDepthCount);
            } else { // Otherwise, keep recursing to search for new winner.
                maxDepthCount = Math.max(recurseDiagonal(row+1, column-1, 1, isLeft, board,maxDepthCount, playerVal), maxDepthCount);
            }
        }

        return maxDepthCount >= 4 ? 4 : maxDepthCount;
    }

}
