import {
	getInquiryReq,
	getNotificationReq,
	getReportDetailReq,
	getReportReq,
	patchInquiryReq,
	patchNotificationReq,
	postInquiryCommentReq,
	postNotificationReq,
	postReportUser,
} from '@/service/Admin/api'
import { useInfiniteQuery, useMutation, useQuery, useQueryClient } from '@tanstack/react-query'

// 문의사항 조회
export const useInquiryDetail = () => {
	return useInfiniteQuery({
		queryKey: ['IInquiryRes'],
		queryFn: ({ pageParam }) => getInquiryReq(pageParam, 10, 0),
		initialPageParam: 0,
		getNextPageParam: (lastPage, allPages) => {
			if (!lastPage || lastPage.last === undefined) return undefined
			const nextPage = allPages.length
			// 마지막 페이지면
			if (lastPage.last) return

			return nextPage
		},
	})
}

// 공지사항 조회
export const useNotificationDetail = () => {
	return useInfiniteQuery({
		queryKey: ['INotificationRes'],
		queryFn: ({ pageParam }) => getNotificationReq(pageParam, 10),
		initialPageParam: 0,
		getNextPageParam: (lastPage, allPages) => {
			if (!lastPage || lastPage.last === undefined) return undefined
			const nextPage = allPages.length
			// 마지막 페이지면
			if (lastPage.last) return

			return nextPage
		},
	})
}

// 신고사항 조회
export const useReportDetail = () => {
	return useInfiniteQuery({
		queryKey: ['IReportRes'],
		queryFn: ({ pageParam=0 }) => getReportReq(pageParam, 12),
		initialPageParam: 0,
		getNextPageParam: (lastPage, allPages) => {
			if (!lastPage || lastPage.last === undefined) return undefined
			const nextPage = allPages.length
			// 마지막 페이지면
			if (lastPage.last) return

			return nextPage
		},
	})
}

// 신고 세부사항 조회
export const useReportDetailItem = (messageId:number|null) => {
	return useQuery({
		queryKey: ['report'],
		queryFn: () => getReportDetailReq(messageId),
	})
}



// 공지사항 작성
export const useNotificationCreate = () => {
	const queryClient = useQueryClient()

	return useMutation({
		mutationKey: ['subject, content'],
		mutationFn: (formData: { subject: string; content: string }) => postNotificationReq(formData),
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['INotificationRes'] })
			console.log('공지사항을 등록했습니다.')
		},
	})
}


// 문의사항 작성
export const useInquiryCreate = () => {
	const queryClient = useQueryClient();
	
	return useMutation({
		mutationFn: ({ boardId, commenterId, content }: { boardId: number; commenterId: number; content: string }) =>
		postInquiryCommentReq({ boardId, commenterId, content }),
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['IInquiryRes'] })
		},
	})
}

// 유저 신고
export const useReportUser = () => {
	const queryClient = useQueryClient();

	return useMutation({
		mutationFn: ({senderId, reportId} : {senderId:number; reportId:number;}) => postReportUser(senderId, reportId),
		onSuccess: () => {
			queryClient.invalidateQueries({queryKey: ['IReportRes']})
			console.log('신고했음 ㅅㄱ')
		}
	})
}


// 공지사항 수정
export const useNotificationEdit = () => {
	const queryClient = useQueryClient()
	return useMutation({
		mutationFn: ({ boardId, formData }: { boardId: number; formData: { subject: string; content: string } }) =>
			patchNotificationReq({ boardId, formData }),
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['INotificationRes'] })
		},
	})
}

// 문의사항 수정
export const useInquiryEdit = () => {
	const queryClient = useQueryClient()

	return useMutation({
		mutationFn: ({ commentId, content }: { commentId: number; content: string }) => patchInquiryReq(commentId, content),
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['IInquiryRes'] })
		},
	})
}
