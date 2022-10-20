import * as s from './Comment.style'
import Text from "../../element/Text";
import CommentContainer from "./CommentContainer";

const Comments = ({postId, receivedUsername}) => {
    return (
        <>
            <s.CommentTitleBox>
                <Text>댓글</Text>
            </s.CommentTitleBox>
            <s.GridBox>
                <div>작성자</div>
                <div>내용</div>
            </s.GridBox>
            <CommentContainer receivedUsername={receivedUsername} postId={postId} />
        </>
    )
}

export default Comments