import styled from "styled-components";

export const GridBox = styled.div`
    display: grid;
    grid-template-columns: 0.5fr 1fr 2fr 1fr;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 1px;
    padding: 16px;
`

export const ContentGridBox = styled.div`
    display: grid;
    grid-template-columns: 0.5fr 1fr;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 1px;
    height: 20vh;
    padding: 16px;
`

export const ButtonBox = styled.div`
    display: flex;
    width: 100%;
    margin: 10px 0px;
    align-items: center;
    justify-content: center;
`

export const InputBox = styled.input`
    border-radius: 5px;
    border: 1px solid #808080;
    width: 80%;
    padding: 10px;
    box-sizing: border-box;
`;