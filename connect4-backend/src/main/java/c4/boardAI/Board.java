package c4.boardAI;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Wrapper class for the Board Data Structure.
 */
public class Board {
    
    private String[][] board;                   // Data Structure for board
    private ArrayList<int[]> possiblePositions; // Data Structure for all child nodes

    /* Constructor */
    public Board(String[][] board){
        this.board = board;
        this.possiblePositions = new ArrayList<int[]>();
        this.generatePossiblePositions(this.possiblePositions);
    }

    /**
     * Generates the list of all possible positions.
     * These will become the respective childrens for each round that is called.
     * @param possiblePositions ArrayList of all possible positions coin can be placed.
     */
    private void generatePossiblePositions(ArrayList<int[]> possiblePositions){
        // Keep track of the visited columns
        HashSet<Integer> colMap = new HashSet<>();

        // Start looping from the bottom row of the matrix
        for ( int i = board.length-1; i >=0; i-- ){

            // Prematurely stop looping if all positions found.
            if ( colMap.size() == board[0].length ) break;

            // Loop through each row
            for ( int j = 0; j < board[0].length; j++ )
            {   
                // We find a zero in unmapped column, we add pos and mark pos visited.
                if ( board[i][j].equals("0") && colMap.contains(j) == false)
                {
                    // Mark column as visited and add cords to list of positions.
                    colMap.add(j);
                    possiblePositions.add(new int[] {i,j});
                }
            }

        }

    }

    public String winnerCheck()
    {
        String winner = checkHorizontal();
        if ( winner == null ) winner = checkVertical();
        if ( winner == null ) winner = checkDiagonal();
        return winner != null ? winner : "";
    }

    /**
     * Check the horizontal (x) axis of the matrix
     * @return Winner's String value.
     */
    public String checkHorizontal(){
        // Current value stored at [i][j] index.
        String currentValue = "";

        // Number of times value has been seen consecutively in the row.
        int valueCount = 0;

        // Loop over all indexes of the matrix. 
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[0].length; j++ )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(currentValue) ) valueCount++;
                else { // Otherwise, mark the new currentValue
                    currentValue = board[i][j];
                    valueCount = 1;
                }
                // Check if we have actually found the winner we are looking for.
                if ( (currentValue.equals("1") || currentValue.equals("2")) && valueCount >= 4 )
                {
                    return currentValue;
                }
            }
        }
        
        return null;
    }

    /**
     * Checks the vertical (y) axis for winners.
     * @return Winner's string value, otherwise null.
     */
    public String checkVertical(){
        // Current value stored at [i][j] index.
        String currentValue = "";

        // Number of times value has been seen consecutively in the row.
        int valueCount = 0;

        // Loop over all indexes of the matrix. 
        for ( int j = 0; j < board[0].length; j++ )
        {
            for ( int i = 0; i < board.length; i++ )
            {
                // If we have matching value to currentValue, we increment.
                if ( board[i][j].equals(currentValue) ) valueCount++;
                else { // Otherwise, mark the new currentValue
                    currentValue = board[i][j];
                    valueCount = 1;
                }
                // Check if we have actually found the winner we are looking for.
                if ( (currentValue.equals("1") || currentValue.equals("2")) && valueCount >= 4 )
                {
                    return currentValue;
                }
            }
        }

        return null;
    }


    /**
     * Checks the left and right digaonal directions for a winner.
     * @return Return the value of the winner, otherwise null
     */
    public String checkDiagonal(){
        
        // Check starting from the top cells of the board
        for ( int j = 0; j < board[0].length; j++ )
        {
            // Starting position for DFS
            String start = board[0][j];

            // Ex Path: 0,0 -> 1,1 -> 2,2
            String horizontalLeft = recurseDiagonal(start, 0+1, j+1, 1, true);
            if ( horizontalLeft != null ) return horizontalLeft;

            // Ex Path: 0,6 -> 1,5 -> 2,4
            String horizontalRight = recurseDiagonal(start, 0+1, j-1, 1, false);
            if ( horizontalRight != null ) return horizontalRight;

        }

        // Check starting from the left and right sides of the board.
        for ( int i = 0; i < board.length; i++ )
        {
            // Starting position for left side
            String startLeft = board[i][0];
            // Ex Path: 2,0 -> 3,1 -> 4,2
            String verticalLeft = recurseDiagonal(startLeft, i+1, 0+1, 1, true);
            if ( verticalLeft != null ) return verticalLeft;

            // Starting position for the right side.
            String startRight = board[i][6];
            // Ex Path: 2,6 -> 3,5 -> 4,4
            String verticalRight = recurseDiagonal(startRight, i+1, 6-1, 1, false);
            if ( verticalRight != null ) return verticalRight;
        }

        // Base case for no winner found.
        return null;
    }

    /**
     * Recursive function to DFS all the nodes diagonally.
     * @param value      Current Value we are searching to match
     * @param row        Current row
     * @param column     Current column
     * @param depthCount Depth count for number of cells in current match.
     * @return Return the current value of the winner, otherwise null.
     */
    private String recurseDiagonal(String value, int row, int column, int depthCount, boolean isLeft)
    {
        // String of the found winner.
        String winner = null;
        // Base case for out of bounds
        if (row >= board.length || row < 0 || column >= board[0].length || column < 0)
        {
            return winner;
        }

        if ( isLeft )
        {
            if ( board[row][column].equals(value) && !board[row][column].equals("0") )
            {
                depthCount++;
                if ( depthCount >= 4 ) {
                    return value;
                }
                winner = recurseDiagonal(board[row][column], row+1, column+1, depthCount, isLeft);
            
            } else {
                winner = recurseDiagonal(board[row][column], row+1, column+1, 1, isLeft);
            }

        } else {
            if ( board[row][column].equals(value) && !board[row][column].equals("0") )
            {
                // increment the counter for depth/cells that match
                depthCount++;
                // Return if we have a winner
                if ( depthCount >= 4 ) {
                    return value;
                }
                    // Otherwise, recurse further to check
                    winner = recurseDiagonal(board[row][column], row+1, column-1, depthCount, isLeft);
            } else { // Otherwise, keep recursing to search for new winner.
                winner = recurseDiagonal(board[row][column], row+1, column-1, 1, isLeft);
            }
        }

        return winner;
    }

    /**
     * Alter a cell in the board to make the AI move.
     * @param board Board object to be sent abck to the client.
     * @param i     Row index for the board object
     * @param j     Column index for the board object.
     */
    public void makeMove(int i, int j)
    {
        // String[][] boardObj = this.board;
        this.board[i][j] = "2";
        // board.setBoard(boardObj);
    }

    /**
     * Deep Copy over the underlying Board DS
     * @param board Board data to be copied.
     * @return New Board object with deep copy of the given board.
     */
    public Board copyBoard()
    {
        String[][] newBoardData = new String[6][7];
        String[][] boardData = this.board; 
        for ( int i = 0; i < newBoardData.length; i++ )
        {
            for ( int j = 0; j < newBoardData[0].length; j++ )
            {
                newBoardData[i][j] = boardData[i][j];
            }
        }
        return new Board(newBoardData);
    }


    // ----------- Getters & Setters --------------
    // Getters
    public String[][] getBoard(){return this.board;}
    public ArrayList<int[]> getPossiblePositions(){return this.possiblePositions;}

    // Setters
    public void setBoard(String[][] newBoard){
        this.board = newBoard;
    }

}