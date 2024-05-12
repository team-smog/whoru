import { axiosAuthInstance } from '@/apis/axiosInstance'

export const logoutApi = async () => {
	const accesstoken = localStorage.getItem('AccessToken')
	console.log(accesstoken)
	const res = await axiosAuthInstance.get(`/member/logout?fcmToken=${localStorage.getItem('FCMToken')}`, {
		headers: {
			Authorization: `Bearer ${accesstoken}`,
		},
		withCredentials: true
	})
	console.log(res.data);

	return res.data;
}