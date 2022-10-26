import React, {useEffect, useState} from "react";
import {useQuery, useQueryClient} from "@tanstack/react-query";
import {getPosts} from "../../shared/api/api";
import Post from "../../component/post/Post";
import * as s from "./Main.style";
import ModalButton from "../../component/ChatModal/ModalButton";
import {useDispatch, useSelector} from "react-redux";
import Button from "../../element/Button";
import {minusPage, plusPage} from "../../store/pageSlice";

const Main = () => {
    const dispatch = useDispatch()
    const query = useQueryClient()
    const user = useSelector((state) => state.user)
    const page = useSelector((state) => state.page)
    const [posts, setPosts] = useState(false);
    const result = useQuery(['posts', page.page], () => getPosts(page.page), {
        onSuccess: (data) => {
            setPosts(data.result)
            console.log(data)
        }
    })

    if (result.isLoading) {
        return <div>Loading...</div>;
    } else {
        return (
            <>
                <s.GridContainer>
                    <s.GridBox>
                        <div>번호</div>
                        <div>작성자</div>
                        <div>제목</div>
                        <div>일자</div>
                    </s.GridBox>
                    {
                       posts && posts.map((e, i) => <Post key={i} post={e}/>)
                    }
                    <s.ArrowBox>
                        <Button width={"20%"} margin={"10px"}
                            _onClick={() => page.page > 0 ? dispatch(minusPage()) : ''}
                        >⬅</Button>
                        <Button width={"20%"}
                            _onClick={() => posts.length >= 10 ? dispatch(plusPage()) : ''}
                        >➡</Button>
                    </s.ArrowBox>
                </s.GridContainer>
                {user.current && <ModalButton />}
            </>
        );
    }
}

export default React.memo(Main)