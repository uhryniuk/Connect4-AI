import React from "react";
import { useState } from "react";
import Button from '@mui/material/Button';
import Alert from '@mui/material/Alert';

import GameBoard from '../components/GameBoard'
import ColorPicker from "../components/ColorPicker";
import utils from '../lib/utils'
import './page-styles.css'
import { AlertTitle } from "@mui/material";

const HomePage = (props) => {
    
    // Use cookies to store state of board maybe too?
    const [board, setBoard]   = useState(utils.makeBlankBoard())
    const [winner, setWinner] = useState(null);

    const [clientColor, setClientColor] = useState(utils.randomColor());
    const [agentColor, setAgentColor]   = useState(utils.randomColor());

    // TODO This should really be it's own component
    const gameCompletedAlert = () => {
        // Can we randomize these messages for fun?
        const alerts = [
            <Alert variant="outlined" severity='success'>
                <AlertTitle>You Win?!</AlertTitle>
                Not sure how, but you pulled it off!
            </Alert>,
            <Alert variant="outlined" severity='error'>
                <AlertTitle>You Lose!</AlertTitle>
                LMAO! you thought you had a chance!
            </Alert>,
        ]
        return alerts[(Number(winner)-1)] || <div>&nbsp;</div>
    }

    return (
        <div class-name={'home-page-container'}>
            <div className="board-container">
                <br/>
                {gameCompletedAlert()}
                <br />
                <GameBoard 
                    boardState={[board,setBoard]} 
                    winnerState={[winner, setWinner]} 
                    cellColors={[clientColor, agentColor]}
                />
                <br />
                <Button 
                    onClick={() => {
                        setBoard(utils.makeBlankBoard())
                        setWinner(null)
                    }}
                    color="error"
                    variant='outlined'>
                    {"Reset Game"}
                </Button>
                <br />
            </div>
            <div className="color-picker-container">
                <ColorPicker name={"CLIENT"} colorState={[clientColor, setClientColor]} />
                <ColorPicker name={"AGENT"} colorState={[agentColor, setAgentColor] } />
            </div>
        </div>
    )
}

export default HomePage;
