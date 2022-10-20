import * as s from './AlarmModal.style'
import {useQuery} from "@tanstack/react-query";
import {deleteAlarm, getAlarms} from "../../shared/api/api";
import {useEffect, useState} from "react";
import {AlarmListBox} from "./AlarmModal.style";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {showModal} from "../../store/alarmModalSlice";

const AlarmModal = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const alarmModal = useSelector((state) => state.alarmModal)
    const [alarmList, setAlarmList] = useState([{alarmType: '알림이 없습니다.'}])
    useQuery(['alarms'], () =>getAlarms(),{
        onSuccess: (data) => data.result.length !== 0 ? setAlarmList(data.result) : ''
    })

    useEffect(() => {
        console.log(alarmList)
    }, [alarmList])

    return (
        <s.ModalBox
            initial={{
                y: -100,
            }}
            animate={{
                y: 0
            }}
        >
            {
                alarmList.map((e, i) => {
                    return (
                        <AlarmListBox key={i}>
                        <div onClick={() => {
                            navigate(`/detail/${e.args.postId}`)
                            deleteAlarm(e.id)
                            dispatch(showModal(!alarmModal.visible))
                        }}>{e.alarmType}</div>
                    </AlarmListBox>
                    )
                })
            }
        </s.ModalBox>
    )
}

export default AlarmModal