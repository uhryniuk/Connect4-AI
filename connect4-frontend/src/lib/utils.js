
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

const COLOR_OPTIONS = ["#f44336", "#FFF000", 
                            "#9c27b0", "#673ab7", 
                            "#3f51b5", "#2196f3", 
                            "#03a9f4", "#00bcd4", 
                            "#009688", "#4caf50"];

const randomColor = () => {
    return COLOR_OPTIONS[Math.floor(Math.random() * COLOR_OPTIONS.length)]
}

const cellStateToBoardValue = (cellState) => {
    switch(cellState){
        case "player" : return 0;
        case "agent" : return 1;
        default: null;
    }
}

const utils = {
    makeBlankBoard : makeBlankBoard,
    cellToClassMap : cellToClassMap,
    cheekyDeepCopy : cheekyDeepCopy,
    transposeBoard : transposeBoard,
    COLOR_OPTIONS  : COLOR_OPTIONS,
    randomColor    : randomColor,
    cellStateToBoardValue : cellStateToBoardValue,
};

export default utils;