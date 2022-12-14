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
                πͺπͺπͺ
            </div>
            <s.SideBox>
                <Button padding={"10px"} _onClick={() => navigate('/signup')}>
                    νμκ°μ
                </Button>
                <Button margin ={"0px 10px"} padding={"10px"} _onClick={() => navigate('/login')}>
                    λ‘κ·ΈμΈ
                </Button>
            </s.SideBox>
        </s.GridBox>
    )
}



export default react.memo(Header)