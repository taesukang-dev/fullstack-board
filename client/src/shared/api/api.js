import axiosInstance from "./instance";

export const getPosts = (page = 0) => axiosInstance.get(`/posts/list?page=${page}`).then(res => res)
export const login = (username, password) => axiosInstance.post('/users/login',{
    "username": username,
    "password": password
}).then(res => document.cookie = 'x_auth'+'='+'Bearer '+res.result)