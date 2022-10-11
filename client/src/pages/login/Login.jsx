import {useMutation} from "@tanstack/react-query";
import * as s from "../signup/SignUp.style";
import Text from "../../element/Text";
import Input from "../../element/Input";
import Button from "../../element/Button";
import {login} from "../../shared/api/api";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {setUserUp} from "../../store/userSlice";

const Login = () => {
    let [username, setUsername] = useState()
    let [password, setPassword] = useState()
    const navigate = useNavigate()
    const dispatch = useDispatch()
    let loginMutation = useMutation(() => login(username, password),{
        onSuccess: (data) => {
            navigate("/")
            dispatch(setUserUp(username))
        },
        onError: (data) => {
            alert('회원정보를 확인하세요. ' + data.response.data.resultCode)
        }
    })

    return (
        <s.GridBox>
            <Text fontSize={"32px"} bold>로그인</Text>
            <Input label={"아이디"} _onChange={(e) => setUsername(e.target.value)}/>
            <Input type={"password"} label={"패스워드"} _onChange={(e) => setPassword(e.target.value)}/>
            <Button type={"submit"} padding={"10px"} _onClick={() => loginMutation.mutate()}>로그인</Button>
        </s.GridBox>
    )
}

export default Login