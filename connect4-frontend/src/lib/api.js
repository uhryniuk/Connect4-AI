import utils from './utils'
import checkWinner from './check-winner';

const URLs = {
    MINIMAX_ENDPOINT : 'http://localhost:8050/api/board-move'
}

// TODO consider writing this, a bit dirty.
async function getAgentMove(boardState, winnerState){
    const [board, setBoard]   = boardState;
    const [winner, setWinner] = winnerState;
    const transposedBoard = utils.transposeBoard(board)
    let newBoard = board;

    let win = checkWinner(transposedBoard)

    if (!win){
        const newBoardResponse = await fetch(URLs.MINIMAX_ENDPOINT, {
            method : "POST",
            body : JSON.stringify(transposedBoard),
            })
        newBoard = await newBoardResponse.json()
        win = checkWinner(newBoard) // Check before Transposing again.
    }
    
    // Render engine needs matrix transposed form of board.
    const newTransposedBoard = utils.transposeBoard(newBoard)
    setBoard(newTransposedBoard)
    setWinner(win)

    return null
}

const api = {
    getAgentMove : getAgentMove,
    URLs : URLs
}

export default api