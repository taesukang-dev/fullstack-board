import styled from "styled-components";
import {motion} from "framer-motion";

const Button = (props) => {
    const {
        children,
        margin,
        padding,
        bg,
        width,
        _onClick,
        color,
        type,
        height,
    } = props

    const styles = {margin, padding, bg, width, color, height}

    return (
        <>
            <ButtonBox type={type} onClick={_onClick} {...styles}
                       whileHover={{scale: 1.1,}}>
                {children}
            </ButtonBox>
        </>
    )
}

Button.defaultProps = {
    children: false,
    margin: "",
    padding: "",
    width: "",
    bg: "false",
    color: "",
    height: ""
}

const ButtonBox = styled(motion.button)`
    border-radius: 5px;
    margin: ${(props) => props.margin};
    width: ${(props) => props.width};
    height: ${(props) => props.height};
    padding: ${(props) => props.padding};
    background: ${(props) => props.bg !== "false" ? props.bg : "white"};
    color: ${(props) => props.color ? props.color : "#304458"};
    font-size: 16px;
    border: 1px solid #ABABAB;
`

export default Button