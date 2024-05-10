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

interface boxCountState {
  boxCount: number | null;
}

const boxCounterInitialState: boxCountState = {
  boxCount: 0,
}

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

const boxCounterSlice = createSlice({
  name: 'boxCounter',
  initialState: boxCounterInitialState,
  reducers: {
    setBoxCount: (state) => {
      if (state.boxCount !== null) {
        state.boxCount += 1;
      }
    }
  }
});


// 슬라이스의 리듀서를 추출
const { reducer: replyReducer } = replySlice;
const { reducer: messageReducer } = messageSlice;
const { reducer: boxCounterReducer } = boxCounterSlice;

// 스토어 구성
const store = configureStore({
  reducer: {
    reply: replyReducer,
    message: messageReducer,
    boxCounter: boxCounterReducer,

  }
});

export const { setReplyMessage } = replySlice.actions;
export const { setFirstId, setLastId } = messageSlice.actions;
export const { setBoxCount } = boxCounterSlice.actions;
export default store;