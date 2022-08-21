import React, { useState } from 'react'
import GameRow from './GameColumn';

function GameBoard(props){

    const [board, setBoard] = props.boardState;

    const makeColumns = () => {
        let arr = []
        for (let i = 0; i < board.length; i++){
            arr.push(
                <GameRow 
                    key={i} 
                    colIndex={i} 
                    boardState={props.boardState}
                    winnerState={props.winnerState}
                />)
        }
        return arr;
    }

    return (
        <div className={'game-board'} >
            {makeColumns()}
        </div>
    )
};

export default GameBoard;