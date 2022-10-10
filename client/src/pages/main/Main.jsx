import {useState} from "react";
import {useQuery} from "@tanstack/react-query";
import {getPosts} from "../shared/api/api";
import Post from "../component/post/Post";

const Main = () => {
    let [posts, setPosts] = useState([]);

    let result = useQuery(['posts'], () => getPosts(), {
        onSuccess: (data) => { setPosts(data.result)}
    })

    return (
        <>
            {
                result.isLoading && <div>Loading...</div>
            }
            {
                posts.map((e, i) => <Post key={i} post={e}/>)
            }
        </>
    )
}

export default Main