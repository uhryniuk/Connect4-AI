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
                />)
        }
        return arr;
    }

    return (
        <div style={{display: "flex"}}  >
            {makeColumns()}
        </div>
    )
};

export default GameBoard;