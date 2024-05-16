import { axiosAuthInstance, axiosWithCredentialInstance } from '@/apis/axiosInstance'
import { IUserInfo } from '@/types/User'
import { AxiosError } from 'axios'

export const reissue = async (error: AxiosError) => {
  if (error.response?.status === 400) {
    localStorage.removeItem('AccessToken');
  }
	if (error.response?.status === 401) {
    const res = await axiosWithCredentialInstance.post(`/member/regenerate-token`);
    console.log(res.data)
    localStorage.setItem('AccessToken', res.data.token);
    return Promise.resolve();
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
