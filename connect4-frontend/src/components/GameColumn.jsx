import React from 'react';
import GameCell from './GameCell';
import utils from '../lib/utils'
import api from '../lib/api'

const GameColumn = (props) => {

    const [board, setBoard]   = props.boardState;
    const [winner, setWinner] = props.winnerState;
    const columnData = board[props.colIndex];

    function makeRows(){
        let arr = []
        for (let i = 0; i < columnData.length; i++){
            arr.push(
                <GameCell 
                    key={i}
                    index={[props.colIndex, i]} 
                    cellState={utils.cellToClassMap[Number(columnData[i])]}
                />)
        }
        return arr;
    }

    function handleWinnerFound(){
        // Not sure if this should do anything.
        // Maybe it sends message or unlocks another features?
        // Send who the winner is to the backend.
        // Retrive a updated value of wins and losses by AI by which type it is.
        console.log('ADD SOME LOGIC HERE')
    }

    function handleColumnClick() {
        for(let i = columnData.length-1; i >=0; i--){
            if (columnData[i] === '0'){
                board[props.colIndex][i] = '1';
                setBoard(utils.cheekyDeepCopy(board));
                break;
            }
        }
        api.getAgentMove([board, setBoard], [winner, setWinner]);
    };
    
    const clickHandler = winner ? handleWinnerFound : handleColumnClick 
    return (
        <div onClick={clickHandler} >
            {makeRows()}
        </div>
    )
};

export default GameColumn;
