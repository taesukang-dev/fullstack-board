import styled from "styled-components";

export const InputBox = styled.input`
    border-radius: 5px;
    border: 1px solid #808080;
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
`;

export const CommentTitleBox = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 16px;
`

export const GridBox = styled.div`
    display: grid;
    grid-template-columns: 1fr 2fr;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px;
    padding: 16px;
`

export const TypeBox = styled.div`
    cursor: ${(props) => props.cursor ? props.cursor : ''};
    margin: ${(props) => props.tab ? "0px 0px 0px " + props.tab + "px" : "0px"};
    display: grid;
    grid-template-columns: 1fr 2fr 0.5fr;
    gap: 10px;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px;
    padding: 16px;
`