import * as s from './Header.style'
import Button from "../../element/Button";
import { useNavigate } from 'react-router-dom'
import react, {useEffect} from "react";
import {getCookie} from "../../shared/Cookie";

const Header = () => {
    const navigate = useNavigate()

    return (
        <s.GridBox>
            <div onClick={() => navigate('/')}>
                📪📪📪
            </div>
            <s.SideBox>
                <Button padding={"10px"} _onClick={() => navigate('/signup')}>
                    회원가입
                </Button>
                <Button margin ={"0px 10px"} padding={"10px"} _onClick={() => navigate('/login')}>
                    로그인
                </Button>
            </s.SideBox>
        </s.GridBox>
    )
}



export default react.memo(Header)