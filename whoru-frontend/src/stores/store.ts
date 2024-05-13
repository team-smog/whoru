import { configureStore, createSlice, PayloadAction } from "@reduxjs/toolkit";

export interface UserState {
  id: number | null;
  userName: string | null;
  provider: string | null;
  memberIdentifier: string | null;
  boxCount: number | null;
  role: string | null;
  createDate: string | null;
  reportCount: number | null;
  languageType: string | null;
  iconUrl: string | null;
  fcmToken: string | null;
  pushAlarm: boolean | null;
}

export const initial: UserState = {
  id: null,
  userName: null,
  provider: null,
  memberIdentifier: null,
  boxCount: null,
  role: null,
  createDate: null,
  reportCount: null,
  languageType: null,
  iconUrl: null,
  fcmToken: null,
  pushAlarm: null,
};

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

const userSlice = createSlice({
  name: 'user',
  initialState:initial,
  reducers: {
    setUser:(state, action:PayloadAction<UserState>) => {
      console.log(state)
      return action.payload;
    },
  }
})

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
    setBoxCountP: (state) => {
      if (state.boxCount !== null) {
        state.boxCount += 1;
      }
    },
    setBoxCountM: (state) => {
      if (state.boxCount !== null) {
        state.boxCount -= 1;
      }
    },
  }
});


// 슬라이스의 리듀서를 추출
const { reducer: replyReducer } = replySlice;
const { reducer: messageReducer } = messageSlice;
const { reducer: boxCounterReducer } = boxCounterSlice;
const { reducer: userReducer} = userSlice;

// 스토어 구성
const store = configureStore({
  reducer: {
    user: userReducer,
    reply: replyReducer,
    message: messageReducer,
    boxCounter: boxCounterReducer,
  }
});

export const { setUser } = userSlice.actions;
export const { setReplyMessage } = replySlice.actions;
export const { setFirstId, setLastId } = messageSlice.actions;
export const { setBoxCountP, setBoxCountM } = boxCounterSlice.actions;
export default store;