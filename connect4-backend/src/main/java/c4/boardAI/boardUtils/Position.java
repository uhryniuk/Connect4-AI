package c4.boardAI.boardUtils;

/**
 * Special Data class to mark each available position in the board scan.
 * This is mean't to be immutable, no need to change the position class.
 */
public class Position {

    // Position the coin can be placed. [i][j]
    private int[] position;

    // The value of the move, if it is made.
    private int evalValue = -1;

    public Position(int i, int j, int evalValue){
        this.position = new int[] {i,j};
        this.evalValue = evalValue;
    }

    public int[] getPosition(){
        return this.position;
    }
    
    public int getRow(){
        return this.position[0];
    }
    
    public int getColumn(){
        return this.position[1];
    }
    
    public int getPositionValue(){
        return this.evalValue;
    }

    public void setPositionValue(int newVal){
        this.evalValue = newVal;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("I: ");
        sb.append(this.getRow());
        sb.append(" - J: ");
        sb.append(this.getColumn());
        sb.append(" : ");
        sb.append(this.getPositionValue());
        return sb.toString();
    }
}
