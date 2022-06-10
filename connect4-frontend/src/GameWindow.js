import React, {useState} from 'react';
import Board from './Board';
import bh from './util/BoardHelpers';

export default function GameWindow(){
    const [playerTurn, setPlayerTurn] = useState('player1') // just default for now
    const [board, setBoard] = useState(bh.createBoardMap())

    // Create Board object and pass different states.
    return <Board playerState={{
        playerTurn: playerTurn,
        setPlayerTurn: setPlayerTurn,
        board: board,
        setBoard: setBoard,
    }}/>
}
