import styled from "styled-components";
import {motion} from "framer-motion";

export const InputBox = styled.input`
    border-radius: 10px;
`

export const GridBox = styled.div`
    padding: 10px;
    display: flex;
    justify-content: center;
    aligns-item: center;
`

export const ChatRoomListBox = styled.div`
    padding: 10px;
    color: #ECECEC;
    font-weight: 700;
`

export const ChatRoomBox = styled(motion.div)`
    background: #4c8bf5;
    position: absolute;
    right: 50%;
    bottom: 100%;
    width: 15rem;
    height: 15rem;
    border-radius: 10%;
    overflow: auto;
`