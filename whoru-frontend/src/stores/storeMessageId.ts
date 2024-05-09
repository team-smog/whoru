import { configureStore, createSlice, PayloadAction } from "@reduxjs/toolkit";


interface ReplyState {
  messageId: number | null;
}

const initialState: ReplyState = {
  messageId: null,
};

interface MessageIdState {
  firstId: number | null;
  lastId: number | null;
}

const messageIdInitialState: MessageIdState = {
  firstId: null,
  lastId: null,
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

const messageSlice = createSlice({
  name: 'message',
  initialState: messageIdInitialState,
  reducers: {
    setFirstId: (state, action: PayloadAction<number|null>) => {
      // console.log(action)
      state.firstId = action.payload;
      // console.log(state.firstId)
    },
    setLastId: (state, action: PayloadAction<number|null>) => {
      state.lastId = action.payload;
    }
  }
});


// 슬라이스의 리듀서를 추출
const { reducer: replyReducer } = replySlice;
const { reducer: messageReducer } = messageSlice;

// 스토어 구성
const store = configureStore({
  reducer: {
    reply: replyReducer,
    message: messageReducer
  }
});

export const { setReplyMessage } = replySlice.actions;
export const { setFirstId, setLastId } = messageSlice.actions;
export default store;