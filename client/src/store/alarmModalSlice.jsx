import {createSlice} from "@reduxjs/toolkit";

let alarmModal = createSlice({
    name: 'alarmModal',
    initialState: {visible: false},
    reducers: {
        showModal(state, action) {
            state.visible = action.payload
        },
    },
})

export let {showModal} = alarmModal.actions

export default alarmModal