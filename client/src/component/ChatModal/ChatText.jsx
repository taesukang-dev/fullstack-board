import styled from "styled-components";
import react from "react";

const ChatText = ({chat}) => {
    return (
        <ChatTextBox>
            <div>
                {chat.sender}:
            </div>
            <div>
                {chat.message}
            </div>
        </ChatTextBox>
    )
}

const ChatTextBox = styled.div`
    color: white;
    font-weight: 700;
    word-break:break-all;
    padding: 10px;
    display: grid;
    grid-template-columns: 1fr 3fr;
    gap: 10px;
`

export default react.memo(ChatText)