import './App.css';
import {Route, Routes} from 'react-router-dom'
import Main from "./pages/main/Main";
import Header from "./component/header/Header";

function App() {
    return (
        <>
            <Header/>
            <Routes>
                <Route path={"/"} element={<Main/>}/>
            </Routes>
        </>
    );
}

export default App;
