import { configureStore, createSlice, PayloadAction } from '@reduxjs/toolkit'

export interface UserState {
	boxCount: number | null
	role: string | null
	pushAlarm: boolean | null
	iconUrl: string | null
}

export const initial: UserState = {
	boxCount: null,
	role: null,
	pushAlarm: true,
	iconUrl: '',
}

interface ReplyState {
	messageId: number | null
}

const initialState: ReplyState = {
	messageId: null,
}

interface MessageIdState {
	firstId: number | null
	lastId: number | null
}

const messageIdInitialState: MessageIdState = {
	firstId: null,
	lastId: null,
}

interface boxCountState {
	boxCount: number | null
}

const boxCounterInitialState: boxCountState = {
	boxCount: 0,
}

const userSlice = createSlice({
	name: 'user',
	initialState: initial,
	reducers: {
		setBoxCount: (state, action: PayloadAction<number | null>) => {
			state.boxCount = action.payload
		},
		setRole: (state, action: PayloadAction<string | null>) => {
			state.role = action.payload
		},
		setPushAlarm: (state, action: PayloadAction<boolean | null>) => {
      // console.log(action.payload)
			state.pushAlarm = action.payload
		},
		setIconUrl: (state, action: PayloadAction<string | null>) => {
			state.iconUrl = action.payload
		}

	},
})

const replySlice = createSlice({
	name: 'reply',
	initialState,
	reducers: {
		setReplyMessage: (state, action: PayloadAction<number | null>) => {
			state.messageId = action.payload
		},
	},
})

const messageSlice = createSlice({
	name: 'message',
	initialState: messageIdInitialState,
	reducers: {
		setFirstId: (state, action: PayloadAction<number | null>) => {
			// console.log(action)
			state.firstId = action.payload
			// console.log(state.firstId)
		},
		setLastId: (state, action: PayloadAction<number | null>) => {
			state.lastId = action.payload
		},
	},
})

const boxCounterSlice = createSlice({
  name: 'boxCounter',
  initialState: boxCounterInitialState,
  reducers: {
    setBoxCountP: (state) => {
      if (state.boxCount !== null) {
        state.boxCount += 1;
        console.log("box Count P",state.boxCount)
      }
    },
    setBoxCountM: (state) => {
      if (state.boxCount !== null) {
        state.boxCount -= 1;
        console.log("box Count M",state.boxCount)
      }
    },
    setBoxCountState: (state, action: PayloadAction<number|null>) => {
      state.boxCount = action.payload;
    },
  }
});


// 슬라이스의 리듀서를 추출
const { reducer: replyReducer } = replySlice
const { reducer: messageReducer } = messageSlice
const { reducer: boxCounterReducer } = boxCounterSlice
const { reducer: userReducer } = userSlice

// 스토어 구성
const store = configureStore({
	reducer: {
		user: userReducer,
		reply: replyReducer,
		message: messageReducer,
		boxCounter: boxCounterReducer,
	},
})

export const { setBoxCount, setRole, setPushAlarm, setIconUrl } = userSlice.actions
export const { setReplyMessage } = replySlice.actions
export const { setFirstId, setLastId } = messageSlice.actions
export const { setBoxCountP, setBoxCountM } = boxCounterSlice.actions
export type RootState = ReturnType<typeof store.getState>

export default store
