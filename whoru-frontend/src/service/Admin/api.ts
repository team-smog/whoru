import { axiosAuthInstance } from '@/apis/axiosInstance'
import { IInquiry, INotification } from '@/types/Admin'
import { APIResponse } from '@/types/model'

export const getInquiryReq = async (page: number, size: number, condition: number): Promise<IInquiry> => {
	const res = await axiosAuthInstance.get(`/admin/board/inquiry`, {
		params: { page, size, condition },
		headers: { Authorization: `Bearer ${localStorage.getItem('AccessToken')}` },
	})
	console.log(res.data.data)
	return res.data.data
}

export const getNotificationReq = async (page: number, size: number): Promise<INotification> => {
	const res = await axiosAuthInstance.get(`/board/noti`, {
		params: { page, size },
		headers: { Authorization: `Bearer ${localStorage.getItem('AccessToken')}` },
	})
	console.log(res.data.data)
	return res.data.data
}

export const postNotificationReq = async (formData: {
	subject: string
	content: string
}): Promise<APIResponse<string>> => {
	const res = await axiosAuthInstance.post(`/admin/board/noti`, JSON.stringify(formData), {
		headers: {
			'Content-Type': 'application/json',
		},
	})
	console.log(res.data)
	return res.data
}

export const patchNotificationReq = async ({
	boardId,
	formData,
}: {
	boardId: number
	formData: { subject: string; content: string }
}): Promise<APIResponse<string>> => {
	console.log(`${boardId}`)
	const res = await axiosAuthInstance.patch(`/admin/board/noti/${boardId}`, JSON.stringify(formData), {
		headers: {
			'Content-Type': 'application/json',
		},
	})
	console.log(res.data)
	return res.data
}
