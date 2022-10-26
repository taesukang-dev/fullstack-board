import styled from "styled-components";

export const GridContainer = styled.div`
    display: flex;
    align-items: left;
    justify-content: center;
    flex-direction: column;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 1px;
    padding: 32px;
`

export const GridBox = styled.div`
    display: grid;
    grid-template-columns: 0.5fr 1fr 2fr 1fr;
    text-decoration: #808080 wavy underline;
    padding: 16px;
`

export const ArrowBox = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
`