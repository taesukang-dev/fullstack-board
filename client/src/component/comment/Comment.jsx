import * as s from './Comment.style'
import Button from "../../element/Button";
import {useSelector} from "react-redux";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {deleteComment, writeCommentByParent} from "../../shared/api/api";
import {useState} from "react";

const Comment = ({comment, tab = 0}) => {
    let [reply, setReply] = useState(false)
    let [commentByParent, setCommentByParent] = useState()
    const user = useSelector((state) => state.user)

    const queryClient = useQueryClient()
    let deleteCommentMutation = useMutation(() => deleteComment(comment.postId, comment.id), {
        onSuccess: (data) => {
            queryClient.invalidateQueries(['comments'])
        }
    })

    let commentWriteByParentMutation = useMutation(() => writeCommentByParent(comment.postId, comment.id, commentByParent), {
        onSuccess: (data) => {
            queryClient.invalidateQueries(['comments'])
        }
    })

    return (
        <>
            <s.TypeBox onClick={() => setReply(!reply)} cursor={"pointer"} tab={tab}>
                <div>{comment.username}</div>
                <div>{comment.content}</div>
                { user.current === comment.username ? <Button _onClick={() => deleteCommentMutation.mutate()}>삭제</Button> : ''}
            </s.TypeBox>
            {
                reply &&
                <s.TypeBox tab={tab + 20}>
                    <div>{user.current}</div>
                    <s.InputBox onChange={(e) => setCommentByParent(e.target.value)}/>
                    <Button _onClick={() => {
                        commentWriteByParentMutation.mutate()
                        setReply(false)
                    }}>작성하기</Button>
                </s.TypeBox>
            }
            {
                comment.child.map((e, i) => <Comment comment={e} tab={tab + 20} key={tab+i}/>)
            }
        </>
    )
}

export default Comment