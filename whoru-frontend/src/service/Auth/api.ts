import { axiosAuthInstance, axiosCommonInstance } from '@/apis/axiosInstance'
import { IAdminUserInfo, IUserInfo } from '@/types/User'
import { APIResponse } from '@/types/model'

export const getUserDetail = async (): Promise<IUserInfo> => {
	const res = await axiosAuthInstance.get(`/member/profile`, {
		headers: {
			'Content-Type': 'application/json',
			Authorization: `Bearer ${localStorage.getItem('AccessToken')}`
		},
	})
	console.log(res)
	return res.data.data
}

export const getAdminRoleDetail = async (): Promise<IAdminUserInfo> => {
	const res = await axiosAuthInstance.get(`/admin/profile`, {
		headers: {
			'Content-Type' : 'application/json'
		}
	})
	console.log(res.data)
	return res.data.data;
}

export const getAdminUserDetail = async (formData: { id: string; pw: string }): Promise<APIResponse<string>> => {
	const res = await axiosCommonInstance.post(`/admin/login`, JSON.stringify(formData), {
		headers: {
			'Content-Type': 'application/json',
		},
	})
	console.log(res.headers)
	if (res.status === 200) {
		localStorage.setItem('AccessToken', res.headers.authorization);
	};

	return res.data;
}
