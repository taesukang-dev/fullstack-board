import styled from "styled-components";

const Text = (props) => {
    const { children, fontSize, color, bold } = props
    const styles = { fontSize, color, bold }
    return(
        <>
            <TextBox {...styles}>{children}</TextBox>
        </>
    )
};

Text.defaultProps = {
    fontSize: '',
    color: '',
    bold: false
}

const TextBox = styled.p`
    color: ${(props) => props.color ? props.color : "#808080"};
    font-size: ${(props) => props.fontSize ? props.fontSize : "16px"};
    font-weight: ${(props) => props.bold ? "700" : "400"};
`

export default Text