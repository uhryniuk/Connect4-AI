import React from 'react';
import GameCell from './GameCell';
import utils from '../lib/utils'
import api from '../lib/api'

const GameColumn = (props) => {

    const [board, setBoard] = props.boardState;
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

    function handleColumnClick() {
        for(let i = columnData.length-1; i >=0; i--){
            if (columnData[i] === '0'){
                board[props.colIndex][i] = '1';
                setBoard(utils.cheekyDeepCopy(board));
                break;
            }
        }
        
        api.getAgentMove([board, setBoard]);
    };
    
    return (
        <div onClick={handleColumnClick} >
            {makeRows()}
        </div>
    )
};

export default GameColumn;
