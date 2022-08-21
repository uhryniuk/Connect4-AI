import React from "react"
import './index.css'
import bh from '../../connect4-frontend/src/lib/BoardHelpers'

/**
 * getOpponentResponse()
 * Send the updated "serialized" board via POST to the simple server.
 * Server will handle the proxy and send it to the desired AI.
 */

export default function Board(props){
    constructBoard(props.playerState.board)

    function convertPlayerTurn(){
        if(props.playerState.playerTurn === 'player1') return 'player2'
        else return 'player1'
    }

    function constructBoard(board){

        // Traverse 6 x 7 to construct the connect 4 board,
        let cellMatrix = []
        for(let i = 0; i < 6; i++){
            let cellRow = []
            for(let j = 0; j < 7; j++){
                // Add each cell with information
                cellRow.push(Cell(`${i}:${j}`, board, board[i][j]))
            }
            // Add each row into the list of rows.
            cellMatrix.push(<div className={'row'}>{cellRow}</div>)
        }

        // Finally, return the JSX Connect4 Board.
        return(
            <div className={'matrix'}>
                {cellMatrix}
            </div>
        )
    }


    /**
     * Contacts API to get the AI's responding move.
     * @param {} board 
     * @param {*} setBoard 
     * @returns 
     */
    async function getAIMove(board, setBoard) {
        const arr = []
        const newBoardResponse = await fetch("http://localhost:8050/api/board-move", {
            method : "POST",
            body : JSON.stringify(board),
        })
        const newBoard = await newBoardResponse.json()
        setBoard(newBoard)
        return newBoard
    }

    /**
     * 
     * @param {*} type String
     * @param {*} matrixIndex String in format of i:j
     * @param {*} board Matrix of matrixIndexes
     * @returns Creates the Cell Object for the DOM
     */
    function Cell(matrixIndex, board, color){
        
        /**
         * When cell clicked in column, highlight the next highest
         */
        function findAvailableCell(playerTurnInt, jthIndex){

            for(let i = board.length-1; i >= 0; i--){
                
                let currentIndex = board[i][jthIndex]
                if (currentIndex === '0'){
                    board[i][jthIndex] = playerTurnInt
                    props.playerState.setBoard(board)
                    break;
                }
            }
            // Handle that no valid position is found.
            // This might be toggle some css to make a warning appear?
        }
        
        // Convert JSON cell value to DOM cell value
        let playerTurnInt = bh.convertPlayerToCellValue(props.playerState.playerTurn)
        const cellClass = bh.convertCellValueToPlayer(color)
        // Swap the players turns current
        const cellIndexes = matrixIndex.split(':')
        // Return our Unqiue Cell Object with contextual data
        return <div className={'cell-slot'} id={matrixIndex} onClick={() => {
            // Don't update board anymore if we have a winner.
            if( !props.playerState.gameWinner){
                findAvailableCell(playerTurnInt, Number(cellIndexes[1]))
                // check for winners and update the winner state.
            }
            if(props.playerState.playerTurn == "player1"){
                board = getAIMove(board, props.playerState.setBoard);
            }

            props.playerState.setGameWinner(bh.winnerCheck(props.playerState.board))
            // check for winners and update the winner state.
            // props.playerState.setGameWinner(bh.winnerCheck(props.playerState.board))
            // Player makes their move (subject to change with AI)
            // We won't need to convert anymore, since other opponent is AI.
            // props.playerState.setPlayerTurn(convertPlayerTurn())
            
        }}><div className={cellClass}></div></div>
    }   
    
    return(
        <div 
            className={'board'}>{constructBoard(props.playerState.board)}
        </div>
    )
}