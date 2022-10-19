import * as s from './ChatModal.style'
import {useQuery} from "@tanstack/react-query";
import {getRoom} from "../../shared/api/api";
import Button from "../../element/Button";
import SockJS from "sockjs-client";
import Stomp from 'stomp-websocket'
import react, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {getCookie} from "../../shared/Cookie";
import Text from "../../element/Text";
import ChatText from "./ChatText";

const ChatRoomDetail = ({presentRoom}) => {
    const header = {Authorization: getCookie('x_auth')};
    const [pubChat, setPubChat] = useState('')
    const [chatList, setChatList] = useState([])
    const user = useSelector((state) => state.user)

    const roomDetail = useQuery(['getRoom'], () => getRoom(presentRoom), {
        onSuccess: (data) => console.log(data)
    })

    let sock = new SockJS("http://localhost:8080/ws-stomp")
    let ws = Stomp.over(sock)

    const sendMessage = () => {
        ws.send("/pub/chat/message", header, JSON.stringify({type: 'TALK', roomId: presentRoom, sender: user.current, message: pubChat}))
    }

    const connect = () => {
        let reconnect = 0
        ws.connect(header, (frame) => {
            ws.subscribe(`/sub/chat/room/${presentRoom}`, (message) => {
                const received = JSON.parse(message.body)
                setChatList((_chatList) => [received, ..._chatList])
                console.log(received)
            })
            ws.send("/pub/chat/message", header, JSON.stringify({type: 'ENTER', roomId: presentRoom, sender: user.current}))
        }, (error) => {
            if (reconnect++ <= 5) {
                setTimeout(() => {
                    console.log("connectiong...")
                    sock = new SockJS("http://localhost:8080/ws-stomp")
                    ws = Stomp.over(sock)
                    connect()
                }, 10 * 1000)
            }
        })
    }

    useEffect(() => {
        connect()
    }, [presentRoom])

    return (
        <s.ChatRoomBox>
            <s.ChatRoomListBox>
                {roomDetail && roomDetail.data?.result.name}
            </s.ChatRoomListBox>
            <s.GridBox>
                <s.InputBox onChange={(e) => setPubChat(e.target.value)}/>
                <Button _onClick={() => sendMessage()}>전송하기</Button>
            </s.GridBox>
            {
                chatList.map((e, i) => {
                    return (<ChatText chat={e} key={i} />)
                })
            }
        </s.ChatRoomBox>
    )
}

export default react.memo(ChatRoomDetail)