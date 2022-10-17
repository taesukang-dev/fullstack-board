import axios from "axios";
import {deleteCookie, getCookie} from "../Cookie";
import {reissue} from "./api";

const axiosConfig = {
    timeout: 3000,
    baseURL: 'http://localhost:8080/api',
};

const axiosInstance = axios.create(axiosConfig);

axiosInstance.interceptors.request.use(
    (config) => {
        const REFRESH_TOKEN = getCookie('x_refresh')
        const USER_TOKEN = getCookie('x_auth');
        config.headers["Content-Type"] = "application/json; charset=utf-8";
        config.headers["X-Requested-With"] = "XMLHttpRequest";
        config.headers["Authorization"] = USER_TOKEN ? USER_TOKEN : REFRESH_TOKEN ? REFRESH_TOKEN : "";
        config.headers.Accept = "application/json";
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

//-- response --
axiosInstance.interceptors.response.use(
    (response) => {
        const res = response.data;
        return res;
    },
    (error) => {
        deleteCookie('x_auth')
        reissue()
        return Promise.reject(error);
    }
);

export default axiosInstance;