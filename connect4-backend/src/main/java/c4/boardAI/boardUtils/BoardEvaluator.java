package c4.boardAI.boardUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

        max = Math.max(max, Math.max(this.enhanceEval(board, depth, "2"), this.enhanceEval(board, depth, "1")));

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
        
        int sum = 0;
        final int[] FIB_SEQ = new int[] {0,2,3,5,8};
        final float[] PRIORITY_FACTORS = new float[] {1.8f, 1.5f, 1.3f};
        
        ArrayList<Position> boardValues = getBoardValues(board, playerVal);
        for ( int i = 0; i < boardValues.size(); i++ ){

            int val = boardValues.get(i).getPositionValue();

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


    private ArrayList<Position> getBoardValues(Board board, String playerVal){

        ArrayList<Position> positions = new ArrayList<>();

        scanHorizontalPositions(board.getBoard(), playerVal, positions);
        scanVerticalPositions(board.getBoard(), playerVal, positions);
        combinePositionLists(positions, scanDiagonalPositions(board.getBoard(), playerVal)); 
        
        for (Position p : positions){
            System.out.println(p + " -> " + playerVal + " === ");
        }
        System.out.println("");

        return positions;
    }

    public void combinePositionLists(ArrayList<Position> positions, ArrayList<Position> newPositions){
        for (Position p : newPositions){
            positions.add(p);
        }
    }

    /**
     * Returns max value seen for the AI  player in horizontal axis.
     * @return Max value found.
     */
    private void scanHorizontalPositions(String[][] board, String playerVal, ArrayList<Position> positions){

        // Number of times value has been seen consecutively in the row.
        int forwardValueCount = 0;
        int backwardValueCount = 0;
        // Loop over all indexes of the matrix. 
        
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[0].length; j++ )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(playerVal) ) {
                    forwardValueCount++;
                    Position tempPosition = new Position(i, j, forwardValueCount);
                    if ( forwardValueCount > 0 ){
                        positions.add(tempPosition);
                    }
                }
                else { // Otherwise, mark the new currentValue
                    forwardValueCount = 0;
                }
            }

            for ( int j = board[0].length-1; j >= 0; j-- )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(playerVal) ) {
                    backwardValueCount++;
                    Position tempPosition = new Position(i, j, backwardValueCount);
                    if ( backwardValueCount > 0 ){
                        positions.add(tempPosition);
                    }
                }
                else { // Otherwise, mark the new currentValue
                    backwardValueCount = 0;
                }
            }

        }
    }

    /**
     * Checks for max value in the vertical axis.
     * @return Max count for AI value.
     */
    private void scanVerticalPositions(String[][] board, String playerVal, ArrayList<Position> positions){
        // Current value stored at [i][j] index.

        // Number of times value has been seen consecutively in the row.
        int ascendValueCount = 0;
        int descendValueCount = 0;

        // Loop over all indexes of the matrix. 
        for ( int j = 0; j < board[0].length; j++ )
        {
            for ( int i = board.length-1; i >= 0; i-- )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(playerVal)  ) {
                    ascendValueCount++;
                    Position tempPosition = new Position(i, j, ascendValueCount);
                    if ( ascendValueCount > 0 ){
                        positions.add(tempPosition);
                    }
                }
                else { // Otherwise, mark the new currentValue
                    ascendValueCount = 0;
                }
            }

            for ( int i = 0; i < board.length; i++ )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(playerVal)  ) {
                    descendValueCount++;
                    Position tempPosition = new Position(i, j, descendValueCount);
                    if ( descendValueCount > 0 ){
                        positions.add(tempPosition);
                    }
                }
                else { // Otherwise, mark the new currentValue
                    descendValueCount = 0;
                }
            }
        }

    }


    /**
     * Checks the left and right digaonal directions for a winner.
     * @return Return the value of the winner, otherwise null
     */
    private ArrayList<Position> scanDiagonalPositions(String[][] board, String playerVal){
        
        ArrayList<Position> newPositions = new ArrayList<>();

        // Check starting from the top cells of the board
        for ( int j = 0; j < board[0].length; j++ )
        {
            // Ex Path: 0,0 -> 1,1 -> 2,2
            combinePositionLists(newPositions, recurseDiagonalPostions(new int[] {0+1, j+1}, 1, true, board, playerVal)); 
            
            // Ex Path: 0,6 -> 1,5 -> 2,4
            // combinePositionLists(newPositions, recurseDiagonalPostions(new int[] {0+1, j-1}, 1, false, board, playerVal)); 

        }

        // Check starting from the left and right sides of the board.
        for ( int i = 0; i < board.length; i++ )
        {
            // Ex Path: 2,0 -> 3,1 -> 4,2
            // combinePositionLists(newPositions, recurseDiagonalPostions(new int[] {i+1, 0-1}, 1, true, board, playerVal)); 
                       
            // Ex Path: 2,6 -> 3,5 -> 4,4
            // combinePositionLists(newPositions, recurseDiagonalPostions(new int[] {i+1, 6-1}, 1, false, board, playerVal));
            
        }

        return newPositions;
    }

    
    private ArrayList<Position> recurseDiagonalPostions(int[] position, int depthCount, boolean isLeft, String[][] board, String playerVal)
    {
        
        ArrayList<Position> newPositions = new ArrayList<>();
        final int row = position[0];
        final int column = position[1];

        // Base case for out of bounds
        
        if ( (row < board.length || row >= 0 || column < board[0].length || column >= 0) && depthCount > 0 )
        {

            newPositions.add(new Position(row, column, depthCount));

            if ( isLeft )
            {
            if ( board[row][column].equals(playerVal) )
            {   
                combinePositionLists(newPositions, recurseDiagonalPostions(new int[] {row+1, column+1}, depthCount+1, isLeft, board, playerVal));;
            
            } else {
                combinePositionLists(newPositions, recurseDiagonalPostions(new int[] {row+1, column+1}, 1, isLeft, board, playerVal));;
            }

            } else {
                if ( board[row][column].equals(playerVal) )
                {
                    combinePositionLists(newPositions, recurseDiagonalPostions(new int[] {row+1, column-1}, depthCount+1, isLeft, board, playerVal));;
                } else { // Otherwise, keep recursing to search for new winner.
                    combinePositionLists(newPositions, recurseDiagonalPostions(new int[] {row+1, column-1}, 1, isLeft, board, playerVal));;
                }
            }
        }

        return newPositions;
    }

}
