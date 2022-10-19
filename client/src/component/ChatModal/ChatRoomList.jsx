import * as s from './ChatModal.style'
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {chatRoomList, createChatRoom, getRoom} from "../../shared/api/api";
import Button from "../../element/Button";
import react, {useState} from "react";
import ChatRoomDetail from "./ChatRoomDetail";

const ChatRoomList = () => {
    const [presentRoom, setPresentRoom] = useState()
    const [chatRoomDetail, setChatRoomDetail] = useState(false)
    const [chatRoomTitle, setChatRoomTitle] = useState()
    const queryClient = useQueryClient()

    const roomList = useQuery(['chatRoom'], () => chatRoomList())

    const createChatRoomMutation = useMutation(() => createChatRoom(chatRoomTitle), {
        onSuccess: (data) => queryClient.invalidateQueries(['chatRoom'])
    })

    if (!chatRoomDetail) {
        return (
            <>
                <s.ChatRoomBox
                    initial={{
                        y: -100,
                    }}
                    animate={{
                        y: 0
                    }}
                >
                    <s.GridBox>
                        <s.InputBox onChange={(e) => setChatRoomTitle(e.target.value)}/>
                        <Button _onClick={() => createChatRoomMutation.mutate()}>전송하기</Button>
                    </s.GridBox>
                    {roomList.data?.result.map((e, i) => <s.ChatRoomListBox onClick={(el) => {
                        setPresentRoom(e.roomId)
                        el.stopPropagation()
                        setChatRoomDetail(!chatRoomDetail)
                    }} key={i}>{e.name}</s.ChatRoomListBox>)}
                </s.ChatRoomBox>
            </>
        );
    } else {
        return (
            <ChatRoomDetail presentRoom={presentRoom} />
        )
    }
};

export default react.memo(ChatRoomList)