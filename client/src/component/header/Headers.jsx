import {useDispatch, useSelector} from "react-redux";
import SignedHeader from "./SignedHeader";
import Header from "./Header";
import {useQuery} from "@tanstack/react-query";
import {userInfo} from "../../shared/api/api";
import {setUserUp} from "../../store/userSlice";

const Headers = () => {
    const dispatch = useDispatch()
    const name = useSelector((state) => state.user)

    useQuery(["username"], () => userInfo(), {
        onSuccess: (data) => dispatch(setUserUp(data.result.username)),
        retry: 1,
        enabled: !!document.cookie.match('(^|;) ?' + 'x_auth' + '=([^;]*)(;|$)') && name.current === ''
    })

    if (name.current) {
        return <SignedHeader/>;
    } else {
        return <Header />;
    }
}

export default Headers