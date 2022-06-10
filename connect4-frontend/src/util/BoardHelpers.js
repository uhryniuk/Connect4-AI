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
 * Export object with all functions
 */
const BoardHelpers = {
    createBoardMap : createBoardMap,
    convertCellValueToPlayer : convertCellValueToPlayer,
    convertPlayerToCellValue : convertPlayerToCellValue,
}

export default BoardHelpers
