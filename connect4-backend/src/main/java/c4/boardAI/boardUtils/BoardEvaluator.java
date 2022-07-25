package c4.boardAI.boardUtils;

import java.util.ArrayList;

/**
 * We can check if there is a winner, but we need to derive a "value"
 * for the winner. (so called heuristics.)
 */
public class BoardEvaluator {

    private String playerVal = null;
    private int depth = 0;
    private Board board = null;

    /* Constructor */
    public BoardEvaluator(String playerVal, int depth, Board board){
        this.playerVal = playerVal;
        this.depth = depth;
        this.board = board;
    }
    
    public Evaluation evaluate(){
        Position p = getOptimalPosition();
        int positionalValue = p.getPositionValue();
        positionalValue = positionalValue - depth;
        return new Evaluation(board, positionalValue, depth);
    }

    private ArrayList<Position> checkPositions(){
        return checkHorizontal();
    }

    private ArrayList<Position> checkHorizontal(){
        ArrayList<Position> positions = new ArrayList<>();
        String[][] b = board.getBoard();

        int rowCount = 0;
        int maxRowCount = 0;

        for (int i = 0; i < b.length; i++){
            for ( int j = 0; j < b[0].length; j++ ){
                // Count how many matching cells we have in a row.
                if ( b[i][j].equals(playerVal) ){
                    rowCount++;
                    maxRowCount = Math.max(maxRowCount, rowCount);
                } else {
                    if ( maxRowCount > 0 ) positions.add(new Position(i, j, maxRowCount));
                    rowCount = 0;
                    maxRowCount = 0;
                }

            }
        }

        for (int i = b.length-1; i >= 0; i--){
            for ( int j = 0; j < b[0].length; j++ ){
                // Count how many matching cells we have in a row.
                if ( b[i][j].equals(playerVal) ){
                    rowCount++;
                    maxRowCount = Math.max(maxRowCount, rowCount);
                } else {
                    if ( maxRowCount > 0 ) positions.add(new Position(i, j, maxRowCount));
                    rowCount = 0;
                    maxRowCount = 0;
                }
            }
        }

        // Proceed deeper into getting more positions.
        positions.addAll(checkVertical());
        return positions;
    }
    
    private ArrayList<Position> checkVertical(){
        ArrayList<Position> positions = new ArrayList<>();
        


        positions.addAll(checkDiagonal());
        return positions;
    }
    
    private ArrayList<Position> checkDiagonal(){
        ArrayList<Position> positions = new ArrayList<>();


        return positions;
    }

    private Position getOptimalPosition(){
        ArrayList<Position> positions = checkPositions();
        Position maxPosition = positions.get(0);
        for ( Position p : positions ){
            maxPosition = maxPosition.getPositionValue() < p.getPositionValue() ? p : maxPosition;
        }
        return maxPosition;
    }
}
