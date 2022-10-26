import {configureStore} from "@reduxjs/toolkit";
import user from "./userSlice";
import alarmModal from "./alarmModalSlice";
import page from "./pageSlice";

export default configureStore({
    reducer: {
        user: user.reducer,
        alarmModal: alarmModal.reducer,
        page: page.reducer
    }
});