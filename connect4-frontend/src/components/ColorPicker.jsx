import React from "react";
import { CirclePicker } from "react-color";
import utils from "../lib/utils";
import './component-styles.css'

function ColorPicker(props){
    const [color, setColor] = props.colorState;

    // Keeping single line function, potentially add more logic later
    const handleColorChange = (color) => {
        setColor(color.hex)
    }

    return (
        <div className={"color-picker-wrapper"} >
            <div className={"color-picker-subwrapper"} style={{margin: "10px"}}>

            <div className={"color-info"}>
                <div className={"current-color"} 
                    style={{borderColor: color}} >{props.name}</div>
            </div>

                <CirclePicker 
                    onChangeComplete={(color) => { handleColorChange(color)}} 
                    width={210} 
                    colors={utils.COLOR_OPTIONS} 
                />
            
            </div>
        </div>
    );
}

export default ColorPicker;