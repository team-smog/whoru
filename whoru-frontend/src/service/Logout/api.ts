import { axiosAuthInstance } from '@/apis/axiosInstance'

export const logoutApi = async () => {
	const accesstoken = localStorage.getItem('AccessToken')
	const fcmToken = localStorage.getItem('FCMToken')
	console.log(accesstoken)
	const res = await axiosAuthInstance.get(`member/logout`, {
		params: {
			fcmToken
		},
		headers: {
			Authorization: `Bearer ${accesstoken}`,
		},
		withCredentials: true
	})
	console.log(res.data);

	return res.data;
}