import * as s from './Comment.style'
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {getComment, writeComment} from "../../shared/api/api";
import {useState} from "react";
import Text from "../../element/Text";
import {useSelector} from "react-redux";
import Button from "../../element/Button";
import Comment from "./Comment";

const Comments = ({postId}) => {
    const user = useSelector((state) => state.user)
    const queryClient = useQueryClient()
    let [comment, setComment] = useState([])

    let result = useQuery(['comments'], () => getComment(postId))

    let commentWriteMutation = useMutation(() => writeComment(postId, comment), {
        onSuccess: (data) => {
            queryClient.invalidateQueries(['comments'])
        }
    })

    return (
        <>
            <s.CommentTitleBox>
                <Text>댓글</Text>
            </s.CommentTitleBox>
            <s.GridBox>
                <div>작성자</div>
                <div>내용</div>
            </s.GridBox>
            <s.TypeBox>
                <div>{user.current}</div>
                <s.InputBox onChange={(e) => setComment(e.target.value)}/>
                <Button _onClick={() => commentWriteMutation.mutate()}>작성하기</Button>
            </s.TypeBox>
            {
                result.data?.result.map((e, i) => <Comment comment={e} key={i}/>)
            }

        </>
    )
}

export default Comments