package c4.boardAI.minimax;

import java.util.ArrayList;

import c4.boardAI.Board;

/**
 * We can check if there is a winner, but we need to derive a "value"
 * for the winner. (so called heuristics.)
 */
public class BoardEvaluator {

    public static Evaluation evaluate(Board board, boolean isMax, int depth){
        final int boardValue = evaluteBoard(board, depth, isMax);
        return new Evaluation(board, boardValue, isMax);
    }

    private static String booleanToPlayer(boolean isAI){
        return isAI ? "2" : "1";
    }

    private static int findBestValue(ArrayList<Integer> values){
        if ( values.size() == 1 ) return values.get(0);
        final int val = values.remove(0), val2 = findBestValue(values);
        return val > val2 ? val : val2;
    }

    private static int evaluteBoard(Board board, int depth, boolean isAI){
        ArrayList<Integer> boardValues = new ArrayList<>();
        String player = booleanToPlayer(isAI);
        board.getPossiblePositions()
            .forEach((pos) -> { 
                boardValues.add(checkHorizontal(board, pos, player)); 
            });
        return findBestValue(boardValues);
    }    

    private static int checkRowScore(ArrayList<Integer> subArray){
        // 0: zero, 1: player, 2: ai
        int[] counter = {0,0,0};
        subArray.forEach( (val) -> { counter[val] += 1; } );
        return heuristicEnhancement(counter[2], counter[1], counter[0]);
    }

    /**
     * Sum scoring values together
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

    private static int checkHorizontal(Board board, int[] pos, String player){
        ArrayList<Integer> subRowScores = new ArrayList<>();
        getHorizontalSlots(board, pos).forEach( (val) -> { subRowScores.add(checkRowScore(val)); });

        final int total = subRowScores.stream().reduce(0, Integer::sum);
        return total + checkVertical(board, pos, player);
    }

    /**
     * Generates All possible 4 slot combos for the given row.
     * @return List of list of positions.
     */
    private static ArrayList<ArrayList<Integer>> getHorizontalSlots(Board b, int[] pos){
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

    private static int checkVertical(Board board, int[] pos, String player){
        ArrayList<Integer> subRowScores = new ArrayList<>();
        getVerticalSlots(board, pos).forEach( (val) -> { subRowScores.add(checkRowScore(val)); });
        final int total = subRowScores.stream().reduce(0, Integer::sum);

        return total + checkDiagonal(board, pos, player);
    }

    /**
     * Generates All possible 4 slot combos for the given column.
     * @return List of list of positions.
     */
    private static ArrayList<ArrayList<Integer>> getVerticalSlots(Board b, int[] pos){
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

    private static int checkDiagonal(Board board, int[] pos, String player){
        ArrayList<Integer> subRowScores = new ArrayList<>();
        getDiagonalSlots(board, pos).forEach( (val) -> { subRowScores.add(checkRowScore(val)); });
        final int total = subRowScores.stream().reduce(0, Integer::sum);

        return total;
    }

    /**
     * Generates All possible 4 slot combos for the given diagonal.
     * @return List of list of positions.
     */
    private static ArrayList<ArrayList<Integer>> getDiagonalSlots(Board b, int[] pos){
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

}

