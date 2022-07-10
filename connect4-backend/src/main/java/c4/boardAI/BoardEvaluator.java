package c4.boardAI;

import java.util.ArrayList;

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
    public Evaluation calculate(Board board, int depth){

        // This maxing process below can be optimzied
        // This is just for POC.

        int max = Integer.MIN_VALUE;

        // int h = checkHorizontal(board.getBoard(), "2");
        // int v = checkVertical(board.getBoard(), "2");
        // int d = checkDiagonal(board.getBoard(), "2");

        // System.out.println("AI:     ");
        // System.out.println("H: "+h);
        // System.out.println("V: "+v);
        // System.out.println("D: "+d);

        // // Add 1 for prioritizing the blocking of player moves.
        // h = Math.max(h, checkHorizontal(board.getBoard(), "1"));
        // v = Math.max(v, checkVertical(board.getBoard(), "1"));
        // d = Math.max(d, checkDiagonal(board.getBoard(), "1"));

        // System.out.println("Player:     ");
        // System.out.println("H: "+h);
        // System.out.println("V: "+v);
        // System.out.println("D: "+d+"\n");

        // // Final maxing process
        // // max = Math.max(max, h);
        // max = Math.max(max, v);
        // max = Math.max(max, h);
        // max = Math.max(max, d);
        max = Math.max(max, Math.max(this.enhanceEval(board, depth, "2"), this.enhanceEval(board, depth, "2")));

        boolean isWinner = max >= 4;
        return new Evaluation(board, max, depth, isWinner);
    }

    public Evaluation calculate(Evaluation eval){
        return this.calculate(eval.getBoard(), eval.getDepth());
    }

    /**
     * 
     * @param Board Eval value generated from Board Checking
     * @param String Eval value generated from Board Checking
     * @return Enhanced heuristics value from more dynamic weighting system.
     */
    private int enhanceEval(Board board, int depth, String playerVal){
        int h = checkHorizontal(board.getBoard(), playerVal);
        int v = checkVertical(board.getBoard(), playerVal);
        int d = checkDiagonal(board.getBoard(), playerVal);

        // TODO Remap the value from 0,1,2,3,4+ to higher valued numbers for better positions.

        int sum = 0;
        // final int HORIZONTAL_VAL = 0;
        // ArrayList<Integer> bv = getBoardValues(board, playerVal);
        // for ( int i = 0; i < bv.size(); i++){
        //     int val = bv.get(i);
        //     if ( val >= 4 ){

        //     } else switch (val) {
        //         case 3: sum += val;
        //             break;
        //         case 2: ;
        //             break;
        //         case 1: ;
        //             break;
        //         default: 
        //     }

        //     // Add small priority to horizontal, for blocking.
        //     if ( i ==  HORIZONTAL_VAL) sum += 1;

        // }

        sum = 0;
        int[] fibNumbers = new int[] {0,2,3,5,8};
        for ( int val : this.getBoardValues(board, playerVal) ){

            switch( val ){
                case 4 : sum += fibNumbers[val];
                    break;
                case 3 : sum += fibNumbers[val];
                    break;
                case 2 : sum += fibNumbers[val];
                    break;
                case 1 : sum += fibNumbers[val];
                    break;
                default: sum += fibNumbers[0];
            }

        }


        // int sum = checkHorizontal(board.getBoard(), playerVal)
        //         + checkVertical(board.getBoard(), playerVal)
        //         + checkDiagonal(board.getBoard(), playerVal);
        
        return sum - depth;
    }


    private ArrayList<Integer> getBoardValues(Board board, String playerVal){
        ArrayList<Integer> arr = new ArrayList<>();

        final int PRIORITY_WEIGHT = 1;

        arr.add(checkHorizontal(board.getBoard(), playerVal));
        arr.add(checkVertical(board.getBoard(), playerVal));
        arr.add(checkDiagonal(board.getBoard(), playerVal));

        return arr;
    }
    /**
     * Returns max value seen for the AI  player in horizontal axis.
     * @return Max value found.
     */
    private int checkHorizontal(String[][] board, String playerVal){

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
    private int checkVertical(String[][] board, String playerVal){
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
     * Checks the left and right digaonal directions for a winner.
     * @return Return the value of the winner, otherwise null
     */
    private int checkDiagonal(String[][] board, String playerVal){
        
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

}
