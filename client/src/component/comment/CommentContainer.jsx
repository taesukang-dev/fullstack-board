import {useSelector} from "react-redux";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {createAlarm, getComment, writeComment} from "../../shared/api/api";
import Comment from "./Comment";
import * as s from "./Comment.style";
import Button from "../../element/Button";
import {useState} from "react";

const CommentContainer = ({postId, receivedUsername}) => {
    const queryClient = useQueryClient()
    const user = useSelector((state) => state.user)
    let [comment, setComment] = useState([])
    let result = useQuery(['comments'], () => getComment(postId),{
        enabled: user.current !== ''
    })

    let commentWriteMutation = useMutation(() => writeComment(postId, comment), {
        onSuccess: (data) => {
            createAlarm(receivedUsername, postId)
            queryClient.invalidateQueries(['comments'])
        }
    })

    if (user.current !== '') {
        return (
            <>
                <s.TypeBox>
                    <div>{user.current}</div>
                    <s.InputBox onChange={(e) => setComment(e.target.value)}/>
                    <Button _onClick={() => commentWriteMutation.mutate()}>작성하기</Button>
                </s.TypeBox>
                {result.data?.result.map((e, i) => <Comment comment={e} postId={postId} receivedUsername={receivedUsername} key={i}/>)}
            </>
        );
    } else {
        return <s.TypeBox>로그인 후 이용하세요.</s.TypeBox>
    }
}

export default CommentContainer