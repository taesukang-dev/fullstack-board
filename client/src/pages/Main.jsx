import {useEffect, useState} from "react";
import axiosInstance from "../shared/api/instance";
import {useQuery} from "@tanstack/react-query";

const Main = () => {
    let [posts, setPosts] = useState([]);

    let result = useQuery(['posts'], () => {
        return axiosInstance.get("/posts/list").then(res => res)
    }, {
        onSuccess: (data) => {
            setPosts(data.result)
        },
    })

    return (
        <>
            {
                result.isLoading && <div>Loading...</div>
            }
            {
                posts.map((e, i) => <div key={i}>{e.content}</div>)
            }
        </>
    )
}

export default Main