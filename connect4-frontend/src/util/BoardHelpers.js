

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
const BoardHelpers = {
    createBoardMap:createBoardMap,
}

export default BoardHelpers
