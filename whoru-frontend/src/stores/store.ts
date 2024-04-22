import { configureStore, createSlice } from "@reduxjs/toolkit";

// 카운터 슬라이스 생성
const counterSlice = createSlice({
  name: 'counter',
  initialState: 0,
  reducers: {
    incremented(state) {
      return state + 1;
    },
    decremented(state) {
      return state - 1;
    }
  }
});

// 슬라이스의 리듀서를 추출
const { reducer: counterReducer } = counterSlice;

// 스토어 구성
const store = configureStore({
  reducer: {
    counter: counterReducer
  }
});

export default store;