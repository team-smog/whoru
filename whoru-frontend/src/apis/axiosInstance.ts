import axios from "axios";
import setAuthorization from "./Interceptors";
import { reissue } from "./refresh";

const axiosRequestConfig = {
  baseURL: import.meta.env.VITE_BASE_URL,
};

// 쿠키 접근용
const axiosWithCredentialConfig = {
  baseURL: import.meta.env.VITE_BASE_URL,
  withCredentials: true,
};

export const axiosCommonInstance = axios.create(axiosRequestConfig);

export const axiosAuthInstance = axios.create(axiosRequestConfig);

axiosAuthInstance.interceptors.request.use(setAuthorization);

// AccessToken 재발급
export const axiosWithCredentialInstance = axios.create(axiosWithCredentialConfig);

axiosWithCredentialInstance.interceptors.request.use();
axiosWithCredentialInstance.interceptors.response.use(null, reissue);