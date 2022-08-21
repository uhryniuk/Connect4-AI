import utils from './utils'

const URLs = {
    MINIMAX_ENDPOINT : 'http://localhost:8050/api/board-move'
}

async function getAgentMove(boardState){
    const [board, setBoard] = boardState;
    const result = []
    const newBoardResponse = await fetch(URLs.MINIMAX_ENDPOINT, {
        method : "POST",
        body : JSON.stringify(utils.transposeBoard(board)),
        })
    const newBoard = await newBoardResponse.json()
    setBoard(utils.transposeBoard(newBoard))
}

const api = {
    getAgentMove : getAgentMove,
    URLs : URLs
}

export default api