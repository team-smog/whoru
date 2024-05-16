import { AxiosError } from "axios";
import { axiosAuthInstance, axiosWithCredentialInstance } from "./axiosInstance";
import { IUserInfo } from "@/types/User";

export const reissue = async (error: AxiosError) => {
  // refresh 토큰 만료 시
  if (error.response?.status === 400) {
    localStorage.removeItem('AccessToken');
  }
  // access 토큰 만료 시
	else if (error.response?.status === 401) {

    const res = await axiosWithCredentialInstance.post(`/member/regenerate-token`, {
      headers: {
        'Content-Type' : "application/json",
      }
    });
    console.log(res.data);
    localStorage.setItem('AccessToken', res.data.token);
    console.log(localStorage.setItem('AccessToken', res.data.token));
    // return Promise.resolve();
  }
  return Promise.reject(error);
}

export const getUserDetail = async (): Promise<IUserInfo> => {
	const res = await axiosAuthInstance.get(`/member/profile`, {
		headers: {
			'Content-Type': 'application/json',
		},
	}) 
	console.log(res);
	return res.data.data
}