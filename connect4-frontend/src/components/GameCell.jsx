import React from 'react';
import './component-styles.css'
import utils from "../lib/utils"

function GameCell(props){

    const cellColorIndex = utils.cellStateToBoardValue(props.cellState) 
    
    return (
        <div 
            className={`cell ${props.cellState}`} 
            style={{backgroundColor: props.cellColors[cellColorIndex]}} >
        </div>
    )
};

export default GameCell;
