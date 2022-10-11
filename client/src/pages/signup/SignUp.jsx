import * as s from './SignUp.style'
import Input from "../../element/Input";
import Button from "../../element/Button";
import Text from "../../element/Text";
import {useState} from "react";
import {useMutation} from "@tanstack/react-query";
import {join} from "../../shared/api/api";
import {useNavigate} from "react-router-dom";

const SignUp = () => {
    let [username, setUsername] = useState()
    let [password, setPassword] = useState()
    const navigate = useNavigate()
    let joinMutation = useMutation(() => join(username, password)
        .then(res => navigate('/login'))
        .catch(res => alert('로그인 정보를 확인하세요.')))

    return (
        <s.GridBox>
            <Text fontSize={"32px"} bold>회원가입</Text>
            <Input label={"아이디"} _onChange={(e) => setUsername(e.target.value)}/>
            <Input type={"password"} label={"패스워드"} _onChange={(e) => setPassword(e.target.value)}/>
            <Button type={"submit"} padding={"10px"}
                    _onClick={() => joinMutation.mutate(username, password)}>회원가입</Button>
        </s.GridBox>
    )
}

export default SignUp