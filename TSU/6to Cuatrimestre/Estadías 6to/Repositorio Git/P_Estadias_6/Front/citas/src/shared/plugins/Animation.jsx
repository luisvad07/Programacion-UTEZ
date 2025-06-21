import React from 'react'
import { CloudWaveEffect } from "react-background-animation";
const Animation = () => {
    return (
        <div style={{ position: "fixed", top: 0, left: 0, right: 0, bottom: 0, zIndex: -1 }}>
            <div style={{ backgroundColor: "#E0E0E0", height: "100vh" }}>
                <CloudWaveEffect snowflakeCount={100} />
            </div>
        </div>
    )
}

export default Animation