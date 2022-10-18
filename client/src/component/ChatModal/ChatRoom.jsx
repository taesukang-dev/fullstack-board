import styled from "styled-components";
import {motion} from "framer-motion";
import {mockChatRoomList} from "./Mock";

const ChatRoom = () => {
    return (
        <>
            <ChatRoomBox
                initial={{
                    y: -100,
                }}
                animate={{
                    y: 0
                }}
            >
                {mockChatRoomList.map((e, i) => <ChatRoomListBox key={i}>{e.roomTitle}</ChatRoomListBox>)}
            </ChatRoomBox>
        </>
    )
};

const ChatRoomListBox = styled.div`
    padding: 10px;
    color: #ECECEC;
    font-weight: 700;
`

const ChatRoomBox = styled(motion.div)`
    background: #4c8bf5;
    position: absolute;
    right: 50%;
    bottom: 100%;
    width: 15rem;
    height: 15rem;
    border-radius: 10%;
    overflow: auto;
`

export default ChatRoom