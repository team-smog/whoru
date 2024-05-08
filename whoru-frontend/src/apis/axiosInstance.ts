import axios from "axios";
import setAuthorization from "./Interceptors";

const axiosRequestConfig = {
  baseURL: import.meta.env.VITE_BASE_URL,
};

const axiosWithCredentialConfig = {
  baseURL: import.meta.env.VITE_BASE_URL,
  withCredentials: true,
};

export const axiosCookie = axios.create(axiosWithCredentialConfig);

axiosCookie.interceptors.request.use(setAuthorization);

export const axiosCommonInstance = axios.create(axiosRequestConfig);

export const axiosAuthInstance = axios.create(axiosRequestConfig);

axiosAuthInstance.interceptors.request.use(setAuthorization);

