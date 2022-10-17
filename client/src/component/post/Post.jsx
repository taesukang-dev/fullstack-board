import * as s from './Post.style'
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

const Post = ({post}) => {
    let [parsedDate, setParsedDate] = useState('')
    const navigate = useNavigate()
    console.log('posts fetched')

    useEffect(() => {
        const newDates = new Date(post.registerAt).toLocaleDateString().split('2022. ')[1]
        const newTimes = new Date(post.registerAt).toTimeString().split(' ')[0]
        setParsedDate(newDates + " " + newTimes)
    }, []);

    return (
        <s.GridBox
            onClick={() => navigate(`/detail/${post.id}`)}
        >
            <div>{post.id}</div>
            <div>{post.username}</div>
            <div>{post.title}</div>
            <div>{parsedDate}</div>
        </s.GridBox>
    )
}

export default React.memo(Post)