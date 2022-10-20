import {configureStore} from "@reduxjs/toolkit";
import user from "./userSlice";
import alarmModal from "./alarmModalSlice";

export default configureStore({
    reducer: {
        user: user.reducer,
        alarmModal: alarmModal.reducer
    }
});