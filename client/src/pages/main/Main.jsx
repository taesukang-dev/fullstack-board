import React, {useState} from "react";
import {useQuery} from "@tanstack/react-query";
import {getPosts} from "../../shared/api/api";
import Post from "../../component/post/Post";
import {GridBox} from "./Main.style";
import ModalButton from "../../component/ChatModal/ModalButton";
import {useSelector} from "react-redux";

const Main = () => {
    const user = useSelector((state) => state.user)
    const [posts, setPosts] = useState(false);
    const result = useQuery(['posts'], () => getPosts(), {
        onSuccess: (data) => {
            setPosts(data.result)
        },
        enabled: !posts
    })
    // if (result.isLoading) {
    //     return <div>Loading...</div>;
    // } else {
        return (
            <>
                <GridBox>
                    {
                       posts && posts.map((e, i) => <Post key={i} post={e}/>)
                    }
                </GridBox>
                {user.current && <ModalButton />}
            </>
        );
    // }
}

export default React.memo(Main)