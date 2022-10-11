import * as s from './Detail.style'
import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {deletePost, getPost, login, updatePost} from "../../shared/api/api";
import Button from "../../element/Button";
import {useSelector} from "react-redux";
import Input from "../../element/Input";

const Detail = () => {
    let [post, setPost] = useState()
    let [parsedDate, setParsedDate] = useState()
    let [updateStatue, setUpdateStatus] = useState(false)
    let [updateTitle, setUpdateTitle] = useState()
    let [updateContent, setUpdateContent] = useState()
    const queryClient = useQueryClient()
    const navigate = useNavigate()
    const user = useSelector((state) => state.user)
    const id = useParams()

    let result = useQuery(['post'], () => getPost(id.id), {
        onSuccess: (data) => {
            setPost(data.result)
            const newDates = new Date(data.result.registerAt).toLocaleDateString().split('2022. ')[1]
            const newTimes = new Date(data.result.registerAt).toTimeString().split(' ')[0]
            setParsedDate(newDates + " " + newTimes)
        }
    })

    let updateMutation = useMutation(() => updatePost(id.id, updateTitle, updateContent),{
        onSuccess: (data) => {
            setUpdateStatus(false)
            queryClient.invalidateQueries(['post'])
        },
        onError: (data) => {
          alert("수정 정보를 확인하세요.")
        }
    })

    let deleteMutation = useMutation(() => deletePost(id.id),{
        onSuccess: (data) => {
            navigate('/')
        },
        onError: (data) => {
            alert("계정 정보를 확인하세요.")
        }
    })

    return (
        <div>
            {result.isLoading && <div>Loading...</div>}
            <s.GridBox>
                <div>글번호</div>
                <div>작성자</div>
                <div>제목</div>
                <div>일자</div>
            </s.GridBox>
            <s.GridBox>
                <div>{post?.id}</div>
                <div>{post?.username}</div>
                {updateStatue ? <s.InputBox onChange={(e) => setUpdateTitle(e.target.value)} />
                    : <div>{post?.title}</div> }
                <div>{parsedDate}</div>
            </s.GridBox>
            <s.ContentGridBox>
                <div>내용</div>
                {updateStatue ? <Input multiLine _onChange={(e) => setUpdateContent(e.target.value)}/>
                    : <div>{post?.content}</div>}
            </s.ContentGridBox>
            <s.ButtonBox>
                {updateStatue ? <Button _onClick={() => updateMutation.mutate()}
                        padding={"10px"}>수정완료</Button>
                    : <Button _onClick={() => navigate('/')} padding={"10px"}>목록으로</Button>}
                { user.current === post?.username &&
                    <Button _onClick={() => setUpdateStatus(true)}
                        padding={"10px"} margin={"0px 0px 0px 10px"}>수정</Button>
                }
                { user.current === post?.username &&
                    <Button _onClick={() => deleteMutation.mutate()}
                            padding={"10px"} margin={"0px 0px 0px 10px"}>삭제</Button>
                }
            </s.ButtonBox>
        </div>
    )
}

export default Detail