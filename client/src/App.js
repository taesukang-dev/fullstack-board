import './App.css';
import {Route, Routes} from 'react-router-dom'
import Main from "./pages/main/Main";
import SignUp from "./pages/signup/SignUp";
import Login from "./pages/login/Login";
import Detail from "./pages/detail/Detail";
import Write from "./pages/write/Write";
import Headers from "./component/header/Headers";
import {useQuery} from "@tanstack/react-query";
import {userInfo} from "./shared/api/api";
import {setUserUp} from "./store/userSlice";
import {useDispatch, useSelector} from "react-redux";

function App() {
    return (
        <>
            <Headers />
            <Routes>
                <Route path={"/"} element={<Main />}/>
                <Route path={"/signup"} element={<SignUp />} />
                <Route path={"/login"} element={<Login />} />
                <Route path={"/write"} element={<Write />} />
                <Route path={"/detail/:id"} element={<Detail />} />
                <Route path={"*"} element={<div>404</div>} />
            </Routes>
        </>
    );
}

export default App;
