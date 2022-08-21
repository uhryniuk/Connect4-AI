import React from 'react';
import './component-styles.css'

function GameCell(props){
    return (
        <div className={`cell ${props.cellState}`}>
            {props.index[0]+","+props.index[1]}
        </div>
    )
};

export default GameCell;
