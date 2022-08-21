import React from "react";
import { useState } from "react";

import GameBoard from '../components/GameBoard'
import utils from '../lib/utils'
import './page-styles.css'

const HomePage = (props) => {
    
    const PLAYER = 'player'

    // Use cookies to store state of board maybe too?
    let [board, setBoard] = useState(utils.makeBlankBoard())
    let [turn, setTurn]   = useState(PLAYER);

    return (
        <div>
            <div className="board-container">
                <GameBoard boardState={[board,setBoard]} turnState={[turn, setTurn]}/>
            </div>
            <div 
                className="temp-reset-button" 
                onClick={() => {setBoard(utils.makeBlankBoard())}} >
                Reset Button
            </div>
        </div>
    )
}

export default HomePage;