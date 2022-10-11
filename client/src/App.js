import './App.css';
import {Route, Routes} from 'react-router-dom'
import Main from "./pages/main/Main";
import Header from "./component/header/Header";
import SignUp from "./pages/signup/SignUp";
import Login from "./pages/login/Login";
import {useSelector} from "react-redux";
import {useEffect} from "react";
import SignedHeader from "./component/header/SignedHeader";

function App() {
    const name = useSelector((state) => state.user)
    useEffect(() => {
        console.log(name)
    },[name])

    return (
        <>
            {name.current ? <SignedHeader /> : <Header />}
            <Routes>
                <Route path={"/"} element={<Main />}/>
                <Route path={"/signup"} element={<SignUp />} />
                <Route path={"/login"} element={<Login />} />
            </Routes>
        </>
    );
}

export default App;
