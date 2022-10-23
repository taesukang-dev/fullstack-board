import React from "react";
import * as s from './Header.style'
import Button from "../../element/Button";
import {useNavigate} from 'react-router-dom'
import {useDispatch, useSelector} from "react-redux";
import Text from "../../element/Text";
import {deleteCookie} from "../../shared/Cookie";
import {setUserUp} from "../../store/userSlice";
import AlarmModal from "../AlarmModal/AlarmModal";
import {showModal} from "../../store/alarmModalSlice";

const SignedHeader = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const username = useSelector((state) => state.user)
    const alarmModal = useSelector((state) => state.alarmModal)

    return (
        <>
            <s.GridBox>
                <div onClick={() => navigate('/')}>
                    hey guys!
                </div>
                <s.LoggedBox>
                    <Text>{username.current}</Text>
                    <div>
                        <Button _onClick={() => dispatch(showModal(!alarmModal.visible))} padding={"10px"}>알림</Button>
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