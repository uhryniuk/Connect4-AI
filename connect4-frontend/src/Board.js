import React, {useState} from "react"
import './index.css'
import BoardHelpers from './util/BoardHelpers'


/**
 * createBoard()
 * We need a "serialize" board function to turn the base json in to the board.
 * Takes the board data and turns it into the DOM Object.
 * 
 * getOpponentResponse()
 * Send the updated "serialized" board via POST to the simple server.
 * Server will handle the proxy and send it to the desired AI.
 * 
 */

export default function Board(props){
    const boardState = [props.playerState.board, props.playerState.setBoard]
    renderBoard(props.playerState.board)

    function convertPlayerTurn(){
        if(props.playerState.playerTurn == 'player1') return 'player2'
        else return 'player1'
    }

    function renderBoard(board){
        let cellMatrix = []
        for(let i = 0; i < 6; i++){
            let cellRow = []
            for(let j = 0; j < 7; j++){
                console.log(board[i][j], `${i}:${j}`)
                cellRow.push(Cell(`${i}:${j}`, board))
            }
            cellMatrix.push(<div className={'row'}>{cellRow}</div>)
        }
        console.log(cellMatrix)
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
    function Cell(matrixIndex, board){
        // Convert JSON cell value to DOM cell value
        let cell;
        const turn = props.playerState.playerTurn
        switch(turn){
            case 'player1': cell = '1';
                break;
            case 'player2': cell = '2';
                break;
            default: cell = '0'
        }
        // Swap the players turns current
        // Init the cell state for Cell Object
        const [cellState, setCellState] = useState('cell')

        // Return our Unqiue Cell Object with contextual data
        return <div className={cellState} id={matrixIndex} onClick={() => {
            setCellState(turn)
            const indexes = matrixIndex.split(':')
            console.log(board[indexes[0]][indexes[1]])
            board[indexes[0]][indexes[1]] = cell
            props.playerState.setBoard(board)
            props.playerState.setPlayerTurn(convertPlayerTurn())
        }}></div>
    }   
    function buildRow(){
        let row = []
        for(let i = 0; i < 7; i ++){
            row.push(Cell({class:"cell"}))
        }
        return row
    }
    let board = []
    for(let i = 0; i < 6; i++){
        
        board.push(<div className={'row'}>{buildRow()}</div>)
    }

    return(<div className={'board'} >{renderBoard(props.playerState.board)}</div>)
}