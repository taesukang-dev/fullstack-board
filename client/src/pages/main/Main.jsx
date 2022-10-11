import {useEffect, useState} from "react";
import {useQuery} from "@tanstack/react-query";
import {getPosts} from "../../shared/api/api";
import Post from "../../component/post/Post";
import {GridBox} from "./Main.style";
import {useSelector} from "react-redux";

const Main = () => {
    let [posts, setPosts] = useState([]);
    let result = useQuery(['posts'], () => getPosts(), {
        onSuccess: (data) => {
            setPosts(data.result)
        }
    })

    return (
        <>
            {
                result.isLoading && <div>Loading...</div>
            }
            <GridBox>
                {
                    posts.map((e, i) => <Post key={i} post={e}/>)
                }
            </GridBox>

        </>
    )
}

export default Main