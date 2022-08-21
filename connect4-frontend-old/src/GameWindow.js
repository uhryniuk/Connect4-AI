import React, {useState} from 'react';
import Board from './Board';
import bh from '../../connect4-frontend/src/lib/BoardHelpers';
import Button from '@mui/material/Button';
import Alert from '@mui/material/Alert';


export default function GameWindow(){
    const [playerTurn, setPlayerTurn] = useState('player1') // just default for now
    const [board, setBoard] = useState(bh.createBoardMap())
    const [gameWinner, setGameWinner] = useState(null)
    // Create Board object and pass different states.

    const gameCompletedAlert = () => {
        // Can we randomize these messages for fun?
        const alerts = [
            <Alert severity='success'>Wow! Somehow you won!</Alert>,
            <Alert severity='error'>Dang! Looks like I smoked you!</Alert>,
        ]
        return alerts[(Number(gameWinner)-1)]
    }
    
    return (<div className='container'>
        <br />
        <Board playerState={{
            playerTurn: playerTurn,
            setPlayerTurn: setPlayerTurn,
            board: board,
            setBoard: setBoard,
            gameWinner:gameWinner,
            setGameWinner:setGameWinner,
        }}/>
        <br />
        <Button 
            onClick={() => {
                setBoard(bh.createBoardMap())
                setGameWinner(null)
            }}
            color="error"
            variant='outlined'>
            Reset Game
        </Button>
        <br/>
        {gameCompletedAlert()}
        {console.log(process.env.REACT_APP_MINIMAX_ENDPOINT)}
        </div>
    )
}
