import * as s from './Header.style'
import Button from "../../element/Button";

const Header = () => {
    return (
        <s.GridBox>
            <div>
                hey guys!
            </div>
            <s.SideBox>
                <Button padding={"10px"}>
                    SignUp
                </Button>
                <Button margin ={"0px 10px"} padding={"10px"}>
                    SignIn
                </Button>
            </s.SideBox>
        </s.GridBox>
    )
}



export default Header