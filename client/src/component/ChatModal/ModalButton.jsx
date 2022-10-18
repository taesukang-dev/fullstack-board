import styled from "styled-components";
import react, {useState} from "react";
import ChatRoom from "./ChatRoom";

const ModalButton = () => {
    const [showChatRoom, setShowChatRoom] = useState(false)

    return (
        <ModalContainer>
            <ModalBox>
                {showChatRoom && <ChatRoom />}
                <Circle onClick={() => setShowChatRoom(!showChatRoom)}>⏏</Circle>
            </ModalBox>
        </ModalContainer>
    )
}

const Circle = styled.div`
    cursor: pointer;
    background: #4c8bf5;
    color: #ECECEC;
    width: 3rem;
    height: 3rem;
    border-radius: 50%;
    font-size: 2rem;
    line-height: 2.7rem;
    text-align: center;
`

const ModalBox = styled.div`
    position: absolute;
    left: 90%;
    bottom: 30%;
    z-index: 9;
    @media screen and (max-width: 500px) {
        left: 80%;
    }
`

const ModalContainer = styled.div`
    display: flex;
    position: relative;
    justify-content: center;
    align-items: center;
`


export default react.memo(ModalButton)