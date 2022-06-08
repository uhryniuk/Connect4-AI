import React, {useState} from 'react';
import Board from './Board';
import BoardHelpers from './util/BoardHelpers';

export default function GameWindow(){
    const [playerTurn, setPlayerTurn] = useState('player1') // just default for now
    const [board, setBoard] = useState(BoardHelpers.createBoardMap())
    console.log(board)
    // We create, or import the empty board here
    // Create state for the board
    // Pass the board into the Board object.
    // Then we can update the board as such and use it
    return <Board playerState={{
        playerTurn: playerTurn,
        setPlayerTurn: setPlayerTurn,
        board: board,
        setBoard: setBoard,
    }}/>
}
