import { axiosAuthInstance, axiosCommonInstance } from '@/apis/axiosInstance'
import { IUserInfo } from '@/types/User'
import { APIResponse } from '@/types/model';

export const getUserDetail = async (): Promise<IUserInfo> => {
	const res = await axiosAuthInstance.get(`/member/profile`, {
		headers: {
			'Content-Type': 'application/json',
		},
	}) 
	console.log(res);
	return res.data.data
}

export const getAdminUserDetail = async (formData: {id: string, pw:string}): Promise<APIResponse<string>> => {
	const res = await axiosCommonInstance.post(`/admin/login`, JSON.stringify(formData), {
		headers: {
			'Content-Type' : 'application/json',
		}
	})
	console.log(res.data)
	return res.data;
}
