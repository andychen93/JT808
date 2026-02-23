import { configureStore, createSlice } from '@reduxjs/toolkit'

const authSlice = createSlice({
  name: 'auth',
  initialState: { token: '', permissions: [], username: '' },
  reducers: {
    setAuth: (state, action) => ({ ...state, ...action.payload }),
    logout: () => ({ token: '', permissions: [], username: '' })
  }
})

const telemetrySlice = createSlice({
  name: 'telemetry',
  initialState: { list: [] },
  reducers: {
    addTelemetry: (state, action) => {
      state.list.unshift(action.payload)
      state.list = state.list.slice(0, 50)
    },
    setTelemetry: (state, action) => {
      state.list = action.payload
    }
  }
})

export const { setAuth, logout } = authSlice.actions
export const { addTelemetry, setTelemetry } = telemetrySlice.actions

export const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
    telemetry: telemetrySlice.reducer
  }
})
