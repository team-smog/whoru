import axios from "axios";
import setAuthorization from './interceptors';

const axiosRequestConfig = {
  baseURL: import.meta.env.VITE_BASE_URL,
};

const axiosWithCredentialConfig = {
  baseURL: import.meta.env.VITE_BASE_URL,
  withCredentials: true,
};

export const axiosCookie = axios.create(axiosWithCredentialConfig);

export const axiosCommonInstance = axios.create(axiosRequestConfig);

export const axiosAuthInstance = axios.create(axiosRequestConfig);


axiosAuthInstance.interceptors.request.use(setAuthorization);