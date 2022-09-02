package c4.boardAI;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Wrapper class for the Board Data Structure.
 */
public class Board {
    
    private String[][] board;                   // Data Structure for board
    private ArrayList<int[]> possiblePositions; // Data Structure for all child nodes
    private Board parentBoard = null;           // Board that stems from the root node.

    // Base implementation
    public Board(String[][] board){
        this.board = board;
        this.possiblePositions = new ArrayList<int[]>();
        this.generatePossiblePositions(this.possiblePositions);
    }

    // Makes this easy to apply the parentboard.
    public Board(String[][] board, Board parentBoard){
        this.board = board;
        this.possiblePositions = new ArrayList<int[]>();
        this.generatePossiblePositions(this.possiblePositions);
        this.setParentBoard(parentBoard);
    }

    // Getters and Setters
    public String[][] getBoard(){return this.board;}

    public ArrayList<int[]> getPossiblePositions(){return this.possiblePositions;}
    
    public Board getParentBoard(){return this.parentBoard;}

    public void setBoard(String[][] newBoard){
        this.board = newBoard;
    }

    public void setParentBoard(Board parentBoard){
        this.parentBoard = parentBoard;
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

    /**
     * Alter a cell in the board to make the AI move.
     * @param board Board object to be sent abck to the client.
     * @param i     Row index for the board object
     * @param j     Column index for the board object.
     */
    public void makeMove(int i, int j, boolean isAI)
    {
        this.board[i][j] = isAI ? "2" : "1";
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

    /**
     * Get all the possible moves that can be made from the origin board.
     * @param isAI Whether we are looking for AI moves or for Player moves.
     * @return List of all possible moves.
     */
    public ArrayList<Board> generatePossibleMoves(boolean isAI){
        ArrayList<Board> boards = new ArrayList<>(); 
        Board boardWrapper = new Board(board);
        for (int[] positions : boardWrapper.getPossiblePositions()){
            Board newBoard = boardWrapper.copyBoard();
            newBoard.makeMove(positions[0], positions[1], isAI);
            newBoard.setParentBoard(
                parentBoard != null ? parentBoard : null
            );
            boards.add(newBoard);
        }
        return boards;
    }

}