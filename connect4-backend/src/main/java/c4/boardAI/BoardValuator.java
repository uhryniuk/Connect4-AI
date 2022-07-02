package c4.boardAI;

/**
 * We can check if there is a winner, but we need to derive a "value"
 * for the winner. (so called heuristics.)
 */
public class BoardValuator {
    
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
    public int calculate(Board board, int depth){

        // Default Starting Value
        int max = 0;

        max = Math.max(max, checkHorizontal(board.getBoard()));
        max = Math.max(max, checkVertical(board.getBoard()));
        max = Math.max(max, checkDiagonal(board.getBoard()));

        return max;
    }



    /**
     * Returns max value seen for the AI  player in horizontal axis.
     * @return Max value found.
     */
    private int checkHorizontal(String[][] board){

        // Number of times value has been seen consecutively in the row.
        int valueCount = 0;
        int maxValueCount = 0;
        // Loop over all indexes of the matrix. 
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[0].length; j++ )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals("2") ) {
                    valueCount++;
                    maxValueCount = Math.max(valueCount, maxValueCount);
                }
                else { // Otherwise, mark the new currentValue
                    valueCount = 0;
                }
            }
        }
        
        System.out.println("Horizontal: "+maxValueCount);
        return maxValueCount;
    }

    /**
     * Checks for max value in the vertical axis.
     * @return MAx count for AI value.
     */
    private int checkVertical(String[][] board){
        // Current value stored at [i][j] index.

        // Number of times value has been seen consecutively in the row.
        int valueCount = 0;
        int maxValueCount = 0;

        // Loop over all indexes of the matrix. 
        for ( int j = 0; j < board[0].length; j++ )
        {
            for ( int i = 0; i < board.length; i++ )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals("2") ) {
                    valueCount++;
                    maxValueCount = Math.max(valueCount, maxValueCount);
                }
                else { // Otherwise, mark the new currentValue
                    
                    valueCount = 0;
                }
            }
        }

        System.out.println("Vertical: "+maxValueCount);
        return maxValueCount;
    }


    /**
     * Checks the left and right digaonal directions for a winner.
     * @return Return the value of the winner, otherwise null
     */
    private int checkDiagonal(String[][] board){
        
        int max = 0;

        // Check starting from the top cells of the board
        for ( int j = 0; j < board[0].length; j++ )
        {
            // Ex Path: 0,0 -> 1,1 -> 2,2
            int horizontalLeft = recurseDiagonal(0+1, j+1, 1, true, board, 0);
            max = Math.max(horizontalLeft, max);

            // Ex Path: 0,6 -> 1,5 -> 2,4
            int horizontalRight = recurseDiagonal(0+1, j-1, 1, false, board, 0);
            max = Math.max(horizontalRight, max);

        }

        // Check starting from the left and right sides of the board.
        for ( int i = 0; i < board.length; i++ )
        {
            // Ex Path: 2,0 -> 3,1 -> 4,2
            int verticalLeft = recurseDiagonal(i+1, 0+1, 1, true, board, 0);
            max = Math.max(verticalLeft, max);            

            // Ex Path: 2,6 -> 3,5 -> 4,4
            int verticalRight = recurseDiagonal(i+1, 6-1, 1, false, board, 0);
            max = Math.max(verticalRight, max);
        }

        System.out.println("Diagonal (all ways): "+max);
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
    private int recurseDiagonal(int row, int column, int depthCount, boolean isLeft, String[][] board, int maxDepthCount)
    {
        // Base case for out of bounds
        if (row >= board.length || row < 0 || column >= board[0].length || column < 0)
        {
            return maxDepthCount;
        }

        if ( isLeft )
        {
            if ( board[row][column].equals("2") )
            {
                depthCount++;
                // Hardcode max depth that it will search for the time being
                maxDepthCount = Math.max(recurseDiagonal(row+1, column+1, depthCount, isLeft, board, maxDepthCount), maxDepthCount);
            
            } else {
                maxDepthCount = Math.max(recurseDiagonal(row+1, column+1, 1, isLeft, board, maxDepthCount), maxDepthCount);
            }

        } else {
            if ( board[row][column].equals("2") )
            {
                // increment the counter for depth/cells that match
                depthCount++;
                // Return if we have a winner
                
                    // Otherwise, recurse further to check
                maxDepthCount = Math.max(recurseDiagonal( row+1, column-1, depthCount, isLeft, board, maxDepthCount), maxDepthCount);
            } else { // Otherwise, keep recursing to search for new winner.
                maxDepthCount = Math.max(recurseDiagonal(row+1, column-1, 1, isLeft, board,maxDepthCount), maxDepthCount);
            }
        }

        return maxDepthCount;
    }

}
