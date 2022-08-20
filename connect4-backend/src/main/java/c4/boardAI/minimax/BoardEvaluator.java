package c4.boardAI.minimax;

import java.util.ArrayList;

import c4.boardAI.Board;

/**
 * We can check if there is a winner, but we need to derive a "value"
 * for the winner. (so called heuristics.)
 */
public class BoardEvaluator {

    /**
     * Board agnostic function to return an Evaluation of the board
     * @param board Board to be evaluated
     * @param isMax Whether the board is max or not.
     * @return      Evalution containing, board, value of the board and isMax.
     */
    public static Evaluation evaluate(Board board){
        final int boardValue = evaluteBoard(board);
        return new Evaluation(board, boardValue);
    }

    /**
     * Begins evaluation process of the given board.
     * @param board Board ot be evaluated.
     * @return      Largest possible value found.
     */
    private static int evaluteBoard(Board board){
        ArrayList<Integer> boardValues = new ArrayList<>();
        boardValues.add(checkHorizontal(board)); 
        return findHighestValue(boardValues);
    } 

    /**
     * Iterates through list of values and finds the best board.
     * @param values List of values that are unordered.
     * @return       Highest possible value in the list.
     */
    private static int findHighestValue(ArrayList<Integer> values){
        if ( values.size() == 1 ) return values.get(0);
        // Pop off last value, recurse comparing values.
        return Math.max( values.remove( (values.size()-1) ), findHighestValue(values) );
    }

    /**
     * Create array of all scores found in a 4 slot section.
     * @param subArray 4 Slot section that needs to be examined.
     * @return         Heuristic integer based on values found in 4 slot section.
     */
    private static int checkRowScore(ArrayList<Integer> subArray){
        // 0: zero, 1: player, 2: ai
        int[] counter = {0,0,0};
        subArray.forEach( (val) -> { counter[val] += 1; } );
        return heuristicEnhancement(counter[2], counter[1], counter[0]);
    }

    /**
     * Sum scoring values together to evalute the actual section of the board.
     * @param     ai Number AI coins in slot 
     * @param player Number Player coins in slot
     * @param   zero Number of empty slots.
     * @return       Sum based of heuristic value of number of coins.
     */
    private static int heuristicEnhancement(int ai, int player, int zero){
        int score = 0;
        if (ai == 4) { score += 500001; } // preference to go for winning move vs. block
        else if (ai == 3 && zero == 1) { score += 5000; }
        else if (ai == 2 && zero == 2) { score += 500; }
        else if (player == 2 && zero == 2) { score -= 501; } // preference to block
        else if (player == 3 && zero == 1) { score -= 5001; } // preference to block
        else if (player == 4) { score -= 500000; }
        return score;
    }

    /**
     * Sums all horizontal 4 slot possible positions. 
     * @param board  Board Object to search.
     * @return List of list of positions.
     */
    private static int checkHorizontal(Board board){
        ArrayList<Integer> subRowScores = new ArrayList<>();
        getHorizontalSlots(board).forEach( (val) -> { subRowScores.add(checkRowScore(val)); });
        final int total = subRowScores.stream().reduce(0, Integer::sum);

        return total + checkVertical(board);
    }

    /**
     * Generates All possible 4 slot combos for the given row.
     * @param board  Board Object to search.
     * @return List of list of positions.
     */
    private static ArrayList<ArrayList<Integer>> getHorizontalSlots(Board b){
        String[][] board = b.getBoard();
        // We iterate over spreading the distance of the board.
        ArrayList<ArrayList<Integer>> positionLists = new ArrayList<>();
        for ( int row = 5; row >= 0; row--){
            for ( int idx = 0; idx < board[0].length-3; idx++ ){
                ArrayList<Integer> tempList = new ArrayList<>();
                for ( int cell = 0; cell < 4; cell++ ){
                    int curr = idx+cell;
                    tempList.add(Integer.parseInt(board[row][curr]));
                }
                positionLists.add(tempList);
            }
        }

        return positionLists;
    }

    private static int checkVertical(Board board){
        ArrayList<Integer> subRowScores = new ArrayList<>();
        getVerticalSlots(board).forEach( (val) -> { subRowScores.add(checkRowScore(val)); });
        final int total = subRowScores.stream().reduce(0, Integer::sum);

        return total + checkDiagonal(board);
    }

    /**
     * Generates All possible 4 slot combos for the given column.
     * @param board  Board Object to search.
     * @return List of list of positions.
     */
    private static ArrayList<ArrayList<Integer>> getVerticalSlots(Board b){
        String[][] board = b.getBoard();
        // We iterate over spreading the distance of the board.
        ArrayList<ArrayList<Integer>> positionLists = new ArrayList<>();
        // Number of Columns
        for ( int column = 6; column >= 0; column--){
            // Number of 4 cell slots per column
            for ( int idx = 0; idx < board.length-3; idx++ ){
                ArrayList<Integer> tempList = new ArrayList<>();
                // For each cell in the 4 slot iteration.
                for ( int cell = 0; cell < 4; cell++ ){
                    int curr = idx+cell;
                    tempList.add(Integer.parseInt(board[curr][column]));
                }
                positionLists.add(tempList);
            }
        }

        return positionLists;
    }

    private static int checkDiagonal(Board board){
        ArrayList<Integer> subRowScores = new ArrayList<>();
        getDiagonalSlots(board).forEach( (val) -> { subRowScores.add(checkRowScore(val)); });
        final int total = subRowScores.stream().reduce(0, Integer::sum);

        return total;
    }

    /**
     * Generates All possible 4 slot combos for the given diagonal column.
     * @param board  Board Object to search.
     * @return List of list of positions.
     */
    private static ArrayList<ArrayList<Integer>> getDiagonalSlots(Board b){
        String[][] board = b.getBoard();
        // We iterate over spreading the distance of the board.
        ArrayList<ArrayList<Integer>> positionLists = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            for(int j = 3; j < 6; j++){
                ArrayList<Integer> tempList = new ArrayList<>();
                // For each cell in the 4 slot iteration.
                for ( int cell = 0; cell < 4; cell++ ){
                    // Get diagonal offset of indicies.
                    int ith = j - cell;
                    int jth = i + cell;
                    tempList.add(Integer.parseInt(board[ith][jth]));
                }
                positionLists.add(tempList);
            }
        }

        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                ArrayList<Integer> tempList = new ArrayList<>();
                // For each cell in the 4 slot iteration.
                for ( int cell = 0; cell < 4; cell++ ){
                    // Get diagonal offset of indicies.
                    int ith = j + cell;
                    int jth = i + cell;
                    tempList.add(Integer.parseInt(board[ith][jth]));
                }
                positionLists.add(tempList);
            }
        }

        return positionLists;
    }

}

