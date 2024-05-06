import { configureStore, createSlice, PayloadAction } from "@reduxjs/toolkit";


interface ReplyState {
  messageId: number | null;
}

const initialState: ReplyState = {
  messageId: null,
};

const replySlice = createSlice({
  name: 'reply',
  initialState,
  reducers: {
    setReplyMessage: (state, action: PayloadAction<number|null>) => {
      state.messageId = action.payload;
    }
  }
});

// 슬라이스의 리듀서를 추출
const { reducer: replyReducer } = replySlice;

// 스토어 구성
const store = configureStore({
  reducer: {
    reply: replyReducer
  }
});

export const { setReplyMessage } = replySlice.actions;
export default store;