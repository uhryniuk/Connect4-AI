package c4.boardAI;

public class BoardRequestWrapper {
    String[][] board = null;
    String winner = null;
    public BoardRequestWrapper(String[][] board, String winner){
        this.board = board;
        this.winner = winner;
    }
    public String[][] getBoard(){
        return this.board;
    }

    public String getWinner(){
        return this.winner;
    }
}
