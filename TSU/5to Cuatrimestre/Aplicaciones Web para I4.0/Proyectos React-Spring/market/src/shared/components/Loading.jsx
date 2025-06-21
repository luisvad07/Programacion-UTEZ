import React from "react"
import styled, {keyframes} from 'styled-components'

const rotate360 = keyframes`
    from{
        transform: rotate(0deg);
    }to{
        transform: rotate(360deg)
    }`

const Spinner = styled.div`
    margin: 16px;
    animation: ${rotate360} 1s linear infinite;
    transform: translateZ(0);
    border-top: 3px solid yellow;
    border-right: 5px solid red;
    border-bottom: 3px solid yellow;
    border-left: 5px solid orange;;
    background: transparent;
    width: 80px;
    height: 80px;
    border-radius:50%;
`
export const Loading = () => {
    return (
        <div style={{padding: '24px'}}>
            <Spinner/>
        </div>
    )
}