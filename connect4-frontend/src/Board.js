import React from "react"
import './index.css'
import bh from './util/BoardHelpers'


/**
 * getOpponentResponse()
 * Send the updated "serialized" board via POST to the simple server.
 * Server will handle the proxy and send it to the desired AI.
 */

export default function Board(props){
    const boardState = [props.playerState.board, props.playerState.setBoard]
    constructBoard(props.playerState.board)

    /**
     * Passes the play state to the other player.
     * @returns String of current player
     */
    function convertPlayerTurn(){
        if(props.playerState.playerTurn == 'player1') return 'player2'
        else return 'player1'
    }

    /**
     * 
     * @param {6x7 matrix repersentation of the board} board 
     * @returns Completed Board DOM JSX object to be rendered.
     */
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
            <div>
                {cellMatrix}
            </div>
        )
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
                    console.log(board)
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
        
        // Return our Unqiue Cell Object with contextual data
        return <div className={cellClass} id={matrixIndex} onClick={() => {
            const cellIndexes = matrixIndex.split(':')
            findAvailableCell(playerTurnInt, Number(cellIndexes[1]))
            props.playerState.setPlayerTurn(convertPlayerTurn())
        }}></div>
    }   
    
    return(
        <div 
            className={'board'}>{
                constructBoard(props.playerState.board)
            }
        </div>
    )
}