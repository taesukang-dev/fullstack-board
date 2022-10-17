import * as s from './Header.style'
import Button from "../../element/Button";
import { useNavigate } from 'react-router-dom'
import {useDispatch, useSelector} from "react-redux";
import Text from "../../element/Text";
import {deleteCookie} from "../../shared/Cookie";
import {setUserUp} from "../../store/userSlice";

const SignedHeader = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const username = useSelector((state) => state.user)

    return (
        <s.GridBox>
            <div onClick={() => navigate('/')}>
                hey guys!
            </div>
            <s.LoggedBox>
                <Text>{username.current}</Text>
                <Button padding={"10px"} margin={"0px 10px"}
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
    )
}

export default SignedHeader