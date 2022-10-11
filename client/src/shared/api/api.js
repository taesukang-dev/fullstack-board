import axiosInstance from "./instance";
import {setUserUp} from "../../store/userSlice";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";

export const getPosts = (page = 0) => axiosInstance.get(`/posts/list?page=${page}`).then(res => res)

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