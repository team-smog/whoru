import axios from 'axios'
import setAuthorization from './Interceptors'

const axiosRequestConfig = {
	baseURL: import.meta.env.VITE_BASE_URL,
}

// 쿠키 접근용
const axiosWithCredentialConfig = {
	baseURL: import.meta.env.VITE_BASE_URL,
	withCredentials: true,
}

export const axiosCommonInstance = axios.create(axiosRequestConfig)

export const axiosAuthInstance = axios.create(axiosRequestConfig)

axiosAuthInstance.interceptors.request.use(setAuthorization)

// AccessToken 재발급
export const axiosWithCredentialInstance = axios.create(axiosWithCredentialConfig)

// axiosWithCredentialInstance.interceptors.request.use(setAuthorization)
axiosWithCredentialInstance.interceptors.response.use(
  (res) => res,
  async(err) => {
    console.log(err)
    if (err.response.status === 401) {
      const res = await axiosWithCredentialInstance.post(`/member/regenerate-token`, {
        headers: {
          'Content-Type' : "application/json",
        }
      });
      // console.log(res.data.data.token);
      localStorage.setItem('AccessToken', res.data.data.token);
      // console.log(localStorage.getItem('AccessToken')); 
      err.config.headers.Authorization = `Bearer ${localStorage.getItem('AccessToken')}`
      return axiosWithCredentialInstance(err.config);
    }
  }
)
