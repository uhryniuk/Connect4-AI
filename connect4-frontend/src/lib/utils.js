// TODO Write a Matrix Transposer for teh board.

const makeBlankBoard = () => {
    let board = []
    for(let i = 0; i < 7; i++){
        let row = []
        for(let j = 0; j < 6; j++){
            row.push('0')
        }
        board.push(row)
    }
    return board
}

const transposeBoard = (board) => {
    const newBoard = []
    for (let i = 0; i < board[0].length; i++){
        const tempRow = []
        for (let j = 0; j < board.length; j++){
            tempRow.push(board[j][i])
        }
        newBoard.push(tempRow)
    }
    return newBoard
}

const cheekyDeepCopy = (data) => {
    return JSON.parse(JSON.stringify(data))
}

const cellToClassMap = ['empty', 'player', 'agent']

const utils = {
    makeBlankBoard : makeBlankBoard,
    cellToClassMap : cellToClassMap,
    cheekyDeepCopy : cheekyDeepCopy,
    transposeBoard : transposeBoard,
};

export default utils;