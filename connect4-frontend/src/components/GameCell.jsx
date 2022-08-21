import React from 'react';
import './component-styles.css'

function GameCell(props){
    return (
        <div className={`cell ${props.cellState}`}>
        </div>
    )
};

export default GameCell;
