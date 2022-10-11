import {createSlice} from "@reduxjs/toolkit";

let user = createSlice({
    name: 'user',
    initialState: {current: ''},
    reducers: {
        setUserUp(state, action) {
            state.current = action.payload
        },
    },
})

export let {setUserUp} = user.actions

export default user