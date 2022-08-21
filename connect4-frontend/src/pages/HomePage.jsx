import React from "react";
import { useState } from "react";

import GameBoard from '../components/GameBoard'
import utils from '../lib/utils'
import './page-styles.css'

const HomePage = (props) => {
    
    // Use cookies to store state of board maybe too?
    const [board, setBoard]   = useState(utils.makeBlankBoard())
    const [winner, setWinner] = useState(null);

    const gameCompletedAlert = () => {
        // Can we randomize these messages for fun?
        const alerts = [
            <div>you win!!</div>,
            <div>you lose fucker!</div>
            // <Alert severity='success'>Wow! Somehow you won!</Alert>,
            // <Alert severity='error'>Dang! Looks like I smoked you!</Alert>,
        ]
        return alerts[(Number(winner)-1)] || <div>&nbsp;</div>
    }

    return (
        <div>
            <div className="board-container">
                <GameBoard boardState={[board,setBoard]} winnerState={[winner, setWinner]} />
            </div>
            {gameCompletedAlert()}
            <div 
                className="temp-reset-button" 
                onClick={() => {
                        setBoard(utils.makeBlankBoard())
                        setWinner(null)
                    }} >
                Reset Button
            </div>
        </div>
    )
}

export default HomePage;