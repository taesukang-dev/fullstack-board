import * as s from './Header.style'
import Button from "../../element/Button";
import { useNavigate } from 'react-router-dom'
import {useSelector} from "react-redux";
import {useEffect} from "react";

const Header = () => {
    const navigate = useNavigate()

    return (
        <s.GridBox>
            <div onClick={() => navigate('/')}>
                hey guys!
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



export default Header