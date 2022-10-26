import {createSlice} from "@reduxjs/toolkit";

let page = createSlice({
    name: 'page',
    initialState: {page: 0},
    reducers: {
        plusPage(state) {
            state.page += 1
        },
        minusPage(state) {
            state.page -= 1
        },
    }
})

export let {plusPage, minusPage} = page.actions

export default page