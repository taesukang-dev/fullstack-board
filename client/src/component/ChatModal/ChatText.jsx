import styled from "styled-components";
import react from "react";

const ChatText = ({chat}) => {
    return (
        <ChatTextBox>
            <div>
                {chat.sender}:
            </div>
            <div>
                {chat.data}
            </div>
        </ChatTextBox>
    )
}

const ChatTextBox = styled.div`
    color: white;
    padding: 10px;
    display: grid;
    grid-template-columns: 0.5fr 2fr;
    gap: 10px;
`

export default react.memo(ChatText)