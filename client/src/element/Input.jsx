import Text from "./Text";
import styled from "styled-components";

const Input = (props) => {
    const { label, multiLine, placeholder, type, _onChange } = props;

    if (multiLine) {
        return (
            <>
                {label && <Text>{label}</Text>}
                <TextareaBox
                    rows={20}
                    placeholder={placeholder}
                    onChange={_onChange}
                >
                </TextareaBox>
            </>
        )
    }

    return(
        <GridBox>
            {label && <Text>{label}</Text>}
            <InputBox placeholder={placeholder} type={type} onChange={_onChange}></InputBox>
        </GridBox>
    )
};

Input.defaultProps = {
    label: "",
    multiLine: false,
    placeholder: "",
    type: '',
    _onChange: () => {}
}

const GridBox = styled.div`
    text-align: left;
    width: 100%;
    padding: 16px;
`

const InputBox = styled.input`
    border-radius: 5px;
    margin: 10px 0px;
    border: 1px solid #808080;
    width: 100%;
    padding: 16px 0px;
    box-sizing: border-box;
`;

const TextareaBox = styled.textarea`
    border-radius: 5px;
    margin: 10px 0px;
    color: black;
    border: 2px solid #808080;
    width: 100%;
    padding: 16px 10px;
    resize: none;
    box-sizing: border-box;
`

export default Input;