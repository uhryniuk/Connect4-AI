package c4.boardAI;

/**
 * Special Data class for Board Evalutator
 * Stores matrix position values and length of game "line" (inclusive)
 * - Inclusive being the line is 3, indcluding if the AI makes the move at these coordinates.
 */
public class Position {

    private int[] positions;
    private int evalValue;

    public Position(int i, int j, int evalValue){
        this.positions = new int[] {i,j};
        this.evalValue = evalValue;
    }

    public int[] getIndex(){
        return positions;
    }

    public int getValue(){
        return evalValue;
    }
}
