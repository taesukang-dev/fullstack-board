import styled from "styled-components";
import {motion} from "framer-motion";

export const ModalBox = styled(motion.div)`
    margin: 5px 0px 0px 0px;
    background: #4c8bf5;
    position: absolute;
    right: 10%;
    width: 15rem;
    height: 15rem;
    border-radius: 10%;
    overflow: auto;
`

export const AlarmListBox = styled.div`
    padding: 10px;
    color: #ECECEC;
    font-weight: 700;
`