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

        if (depth == 4){
            
        }

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
                final int rawValue = checkHorizontal(board, pos, player);
                boardValues.add(rawValue);
            });
        return findBestValue(boardValues);
    }

    /**
     * Generates All possible 4 slot combos for the given row.
     * @return List of list of positions.
     */
    private static ArrayList<ArrayList<Integer>> getHorizontalSlots(Board b, int[] pos){
        int i = pos[0], j = pos[1];
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

    private static int checkRowScore(ArrayList<Integer> subArray){
        // 0: zero, 1: player, 2: ai
        int[] counter = {0,0,0};
        subArray.forEach( (val) -> { counter[val] += 1; } );
        return heuristicEnhancement(counter[2], counter[1], counter[0]);
    }

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
        int valueCount = 0;
        int maxValueCount = 0;
        String[][] b = board.getBoard();

        ArrayList<Integer> subRowScores = new ArrayList<>();
        getHorizontalSlots(board, pos).forEach( (val) -> {subRowScores.add(checkRowScore(val));});
        int total = subRowScores.stream().reduce(0, Integer::sum);
        // final int max = subRowScores.stream().max(Integer::compare).get();
        int max = 0;
        for (int i : subRowScores){
            max = Math.max(max, i);
        }
        // subRowScores.
        /**
         * 1. Generate all possible variations.
         *   - Perhaps we generate all sets of positions
         *   - Then we search those positions in the Matrix?
         *   - Alternative would be calculating the ranges on the fly.
         *     - This is more error prone IMO.
         * 2. Count amount in them
         * 3. Pass through heuristic function
         * 4. Get the return value.
         */
        maxValueCount = 1;
        // return maxValueCount;
        return total + checkVertical(board, pos, player);
    }

    private static int checkVertical(Board board, int[] pos, String player){
        int value = 0;
        return value + checkDiagonal(board, pos, player);
    }

    private static int checkDiagonal(Board board, int[] pos, String player){
        int value = 0;
        return value;
    }

    // Notes:
    /**
     * There are two different types: counting for PLAYER and then AGENT
     * - This comes in the from of:
     *  - Add to AGENT's line.
     *  - Blocking the Player's line.
     * 
     * Strategy:
     * - Iterate from AGENT POV if AGENT board
     * - Calculate normally
     * 
     * - Iterate from PLAYER POV, if PLAYER
     * - Calculate normally and add block bias 
     * 
     * NOTES:
     * - Utilize the board's "AllPossibleMoves" function.
     * - We should count if a move is good fi it isnt even valid...
     * 
     * IDEAS:
     * - Create function that scans each VALID positions
     *  - Scans them in each of the directions
     *      - Horizontal, Vetical, both Diagonals
     *  - Currently, I love this idea
     *  - Seems so simple and elegant on how to see how god a hand is.
     *  - Create all possible configurations of 4 cells that contain the possible move.
     *  - If we have 3 good, then it's winning move
     *  - If we have 2, and 1 bad
     */
}

