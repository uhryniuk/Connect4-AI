/**
 * Utility functions that are focused on helping the board.
 */

/**
 * @return JSON of a blank Connect4 Board
 */
function createBoardMap(){
    let board = []

    for(let i = 0; i < 6; i++){
        let row = []
        for(let j = 0; j < 7; j++){
            row.push('0')
        }
        board.push(row)
    }
    return board
}

/**
 * Convert JSON Cell value into the player value
 * @param {string} cellValue int as string value repersenting position on board
 * @returns 
 */
function convertCellValueToPlayer(cellValue){
    switch(cellValue){
        case '1' : return 'player1'
        case '2' : return 'player2'
        default: return 'cell'
    }
}

/**
 * Convert Player state into the Map value.
 * @param {string} player string of the current player state of cell.
 * @returns 
 */
function convertPlayerToCellValue(player){
    switch(player){
        case 'player1': return '1';
        case 'player2': return '2';
        default: return '0'
    }
}

/**
 * Checks a row in the matrix for a winner.
 * @param {*} row 
 * @returns the playerValue that has a valid winner in the rowl.
 */
function horizonalCheck(row){
    // Keeps the sliding window count
    let currentValue = null;
    let valueCount = 0;

    // Iterate over the row
    for(let i = 0; i < row.length; i++){
        // Add it to thr row count
        if(row[i] == currentValue) valueCount++;
        else{
            currentValue = row[i]
            valueCount = 1
        }
        // Check for winner (certainly can optimize this)
        if( (currentValue == '1' || currentValue == '2') && valueCount >= 4 ){
            console.log("REMOVE ME::::: Winner is found!: ", currentValue)
            return currentValue
        }
    }
    // No winner was found horizontally.
    return null
}

/**
 * Iterate through the columns of the array.
 * Unfortunately this is horrible slow and neds optimization later.
 * @param {*} board 
 * @returns the playerValue that has a valid winner in the rowl.
 */
function verticalCheck(board) {

    // Store sliding window values
    let currentValue = null;
    let valueCount = 0;

    // We need outer inex for [j][i] style indexing (ik so bad to do)
    let index = 0
    while(index < 6){
        for(let j = 0; j < board.length; j++){
            if(board[j][index] == currentValue) valueCount++;
            else{
                currentValue = board[j][index]
                valueCount = 1
            }
            // Check for winner (certainly can optimize this)
            if( (currentValue == '1' || currentValue == '2') && valueCount >= 4 ){
                console.log("REMOVE ME::::: Winner is found!: ", currentValue)
                return currentValue
            }
        }
        index++;
    }
    return null
}

/**
 * Check all the possible diagonal positions of the matrix.
 * @param {*} board Matrix n*m gameboard.
 */
function diagonalCheck(board){

    /**
     * Utility function to search from top left -> bottom right side of board.
     * @param {*} value 
     * @param {*} row 
     * @param {*} column 
     * @param {*} depthCount 
     * @returns 
     */
    const searchLeftDiagonal = (value, row, column, depthCount) => {
        // Base case that we are out bounds of matrix.
        if ( board[row] === undefined || board[row][column] === undefined) return null

        let winner; // Global var to assign a winner

        // If we found another value in matching line.
        if ( board[row][column] === value && board[row][column] !== '0'){
            depthCount++;
            
            // Check if we have winning line
            if (depthCount >= 4) return board[row][column]
            // Otherwise go deeper to the next node.
            winner = searchLeftDiagonal(value, row+1, column+1, depthCount)
        } // Otherwise reset counter and value type, go to next node.
        else {
            // TODO -> Further logic optimization possible.
            winner = searchLeftDiagonal(board[row][column], row+1, column+1, 1)
        }

        // Return a winner if we have actually found one.
        return winner
    }

    const searchRightDiagonal = (value, row, column, depthCount) => {
        
    }

    // Check starting from the top slots of the board.
    for (let i = 0; i < board[0].length; i++){
        const start = board[0][i]
        const horizonalWinner = searchLeftDiagonal(start, 0+1, i+1, 1)
        if ( horizonalWinner ) return horizonalWinner
    }

    // Check starting from the left and right sides of board
    for ( let i = 0; i < board.length; i++ ){
        const start = board[i][0]
        const verticalWinner  = searchLeftDiagonal(start, i+1, 0+1, 1)
        if ( verticalWinner ) return verticalWinner
    }

    // Base case for no winner found.
    return null
}

/**
 * Checks the to see if a winner has been found.
 * @param {*} board 
 */
function winnerCheck(board){
    // verticalCheck(board)
    for(const row of board){
        // horizonalCheck(row)
    }
    const winner = diagonalCheck(board)
    console.log("REMOVE ME::::: Winner is found!: ", winner)
}

/**
 * Export object with all functions
 */
const BoardHelpers = {
    createBoardMap : createBoardMap,
    convertCellValueToPlayer : convertCellValueToPlayer,
    convertPlayerToCellValue : convertPlayerToCellValue,
    winnerCheck : winnerCheck,
}

export default BoardHelpers
