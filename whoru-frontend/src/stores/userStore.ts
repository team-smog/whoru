import { configureStore, createSlice, PayloadAction } from "@reduxjs/toolkit"

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

export const initialState: UserState = {
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

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser:(state, action:PayloadAction<UserState>) => {
      console.log(state)
      return action.payload;
    },
    clearUser: (state) => {
      console.log(state)
      return initialState;
    },
    setBoxCount:(state, action:PayloadAction<number|null>) => {
      state.boxCount = action.payload
      console.log("state", state)
      console.log("boxCount",state.boxCount)
    }
  }
})

const userReducer = userSlice.reducer;

const store = configureStore({
  reducer: {
    user: userReducer,
  }
});

export const { setUser, clearUser, setBoxCount } = userSlice.actions;

export default store;