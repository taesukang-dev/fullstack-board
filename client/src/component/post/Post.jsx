import * as s from './Post.style'
import {useEffect, useState} from "react";

const Post = ({post}) => {
    let [parsedDate, setParsedDate] = useState('')
    useEffect(() => {
        const newDates = new Date(post.registerAt).toLocaleDateString().split('2022. ')[1]
        const newTimes = new Date(post.registerAt).toTimeString().split(' ')[0]
        setParsedDate(newDates + " " + newTimes)
    }, [])
    return (
        <s.GridBox>
            <div>{post.id}</div>
            <div>{post.username}</div>
            <div>{post.title}</div>
            <div>{parsedDate}</div>
        </s.GridBox>
    )
}

export default Post