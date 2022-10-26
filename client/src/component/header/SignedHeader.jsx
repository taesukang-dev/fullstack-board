import React, {useEffect, useState} from "react";
import * as s from './Header.style'
import Button from "../../element/Button";
import {useNavigate} from 'react-router-dom'
import {useDispatch, useSelector} from "react-redux";
import Text from "../../element/Text";
import {deleteCookie, getCookie} from "../../shared/Cookie";
import {setUserUp} from "../../store/userSlice";
import AlarmModal from "../AlarmModal/AlarmModal";
import {showModal} from "../../store/alarmModalSlice";

const SignedHeader = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const username = useSelector((state) => state.user)
    const alarmModal = useSelector((state) => state.alarmModal)
    const [warn, setWarn] = useState(false)

    useEffect(() => {
        const eventSource = new EventSource("http://localhost:8080/api/alarm/subscribe?token=" + getCookie('x_auth').split(' ')[1])
        eventSource.addEventListener("open", function (event) {
            console.log("connection opened");
            setWarn(false)
        });

        eventSource.addEventListener("alarm", function (event) {
            if (event.data !== 'connect completed') {
                setWarn(true)
            }
        });

        eventSource.addEventListener("error", function (event) {
            console.log(event.target.readyState);
            if (event.target.readyState === EventSource.CLOSED) {
                console.log("eventsource closed (" + event.target.readyState + ")");
            }
            eventSource.close();
        });
    }, [])

    return (
        <>
            <s.GridBox>
                <div onClick={() => navigate('/')} style={{cursor: "pointer"}}>
                    📪📪📪
                </div>
                <s.LoggedBox>
                    <Text>{username.current}</Text>
                    <div>
                        <Button warn={warn.toString()} _onClick={() => {
                            setWarn(false)
                            dispatch(showModal(!alarmModal.visible))
                        }} padding={"10px"}>알림</Button>
                        { alarmModal.visible && <AlarmModal/> }
                    </div>
                    <Button padding={"10px"}
                            _onClick={() => {
                                deleteCookie('x_auth')
                                deleteCookie('x_refresh')
                                dispatch(setUserUp(''))
                            }}
                    >로그아웃</Button>
                    <Button padding={"10px"}
                            _onClick={() => navigate('/write')}
                    >글쓰기</Button>
                </s.LoggedBox>
            </s.GridBox>
        </>
    )
}

export default React.memo(SignedHeader)