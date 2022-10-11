import axiosInstance from "./instance";

export const getPosts = (page = 0) => axiosInstance.get(`/posts/list?page=${page}`).then(res => res)

export const getPost = (postId) => axiosInstance.get(`/posts/post/${postId}`).then(res => res)

export const writePost = (title, content) => axiosInstance.post(`/posts/write`,{
    "title": title,
    "content": content
}).then(res => res.result.id)

export const updatePost = (postId, title, content) => axiosInstance.put(`/posts/${postId}/update`,{
    "title": title,
    "content": content
}).then(res => console.log(res))

export const deletePost = (postId) => axiosInstance.delete(`/posts/${postId}/delete`).then(res => console.log(res))

export const join = (username, password) => axiosInstance.post('/users/join',{
    "username": username,
    "password": password
}).then(res => console.log(res))

export const login = (username, password) => axiosInstance.post('/users/login',{
    "username": username,
    "password": password
}).then(res => {
    document.cookie = 'x_auth' + '=' + 'Bearer ' + res.result
})

