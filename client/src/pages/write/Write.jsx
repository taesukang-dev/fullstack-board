import * as s from './Write.style'
import Input from "../../element/Input";
import Button from "../../element/Button";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import {useMutation} from "@tanstack/react-query";
import {writePost} from "../../shared/api/api";

const Write = () => {
    const navigate = useNavigate()
    let [writeTitle, setWriteTitle] = useState()
    let [writeContent, setWriteContent] = useState()

    const writeMutation = useMutation(() => writePost(writeTitle, writeContent), {
        onSuccess: (data) => {
            navigate(`/detail/${data}`)
        },
        onError: (data) => {
            alert('작성 정보를 확인하세요.')
        }
    })

    return (
        <>
            <s.GridBox>
                <div>제목</div>
                <div>내용</div>
            </s.GridBox>
            <s.GridBox>
                <Input _onChange={(e) => setWriteTitle(e.target.value)}
                    multiLine/>
                <Input _onChange={(e) => setWriteContent(e.target.value)}
                    multiLine/>
            </s.GridBox>
            <s.ButtonBox>
                <Button _onClick={() => navigate('/')}
                    padding={"10px"} margin={"0px 10px"}>취소</Button>
                <Button _onClick={() => writeMutation.mutate()}
                    padding={"10px"}>작성완료</Button>
            </s.ButtonBox>
        </>
    )
}

export default Write