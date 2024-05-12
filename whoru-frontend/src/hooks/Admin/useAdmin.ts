import { getInquiryReq, getNotificationReq, patchInquiryReq, patchNotificationReq, postInquiryCommentReq, postNotificationReq } from '@/service/Admin/api'
import { useInfiniteQuery, useMutation, useQueryClient } from '@tanstack/react-query'


// 조회
export const useInquiryDetail = () => {
	return useInfiniteQuery({
		queryKey: ['IInquiryRes'],
		queryFn: ({ pageParam }) => getInquiryReq(pageParam, 10, 0),
		initialPageParam: 0,
		getNextPageParam: (lastPage, allPages) => {
			if (!lastPage || lastPage.last === undefined) return undefined;
			const nextPage = allPages.length
			// 마지막 페이지면
			if (lastPage.last) return

			return nextPage
		},
	})
}

export const useNotificationDetail = () => {
	return useInfiniteQuery({
		queryKey: ['INotificationRes'],
		queryFn: ({ pageParam }) => getNotificationReq(pageParam, 10),
		initialPageParam: 0,
		getNextPageParam: (lastPage, allPages) => {
			if (!lastPage || lastPage.last === undefined) return undefined;
			const nextPage = allPages.length
			// 마지막 페이지면
			if (lastPage.last) return

			return nextPage
		},
	})
}

// 작성
export const useNotificationCreate = () => {
	const queryClient = useQueryClient();

	return useMutation({
		mutationKey: ['subject, content'],
		mutationFn: (formData: { subject: string; content: string }) => postNotificationReq(formData),
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['INotificationRes'] })
			console.log('공지사항을 등록했습니다.')
		},
	})
}

export const useInquiryCreate = () => {
	const queryClient = useQueryClient();

	return useMutation({
		mutationFn: ({boardId, commenterId, content} : {boardId: number, commenterId: number, content: string}) => postInquiryCommentReq({boardId, commenterId, content}),
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['IInquiryRes']})
		}
	})
}

// 수정
export const useNotificationEdit = () => {
	const queryClient = useQueryClient();
	return useMutation({
		mutationFn: ({ boardId, formData }: { boardId: number; formData: { subject: string; content: string } }) =>
			patchNotificationReq({ boardId, formData }),
		onSuccess: () => {
			queryClient.invalidateQueries({ queryKey: ['INotificationRes'] })
			
		},
	})
}

export const useInquiryEdit = () => {
	const queryClient = useQueryClient();

	return useMutation({
		mutationFn: ({commentId, content} : {commentId:number; content:string}) => patchInquiryReq(commentId, content),
		onSuccess: () => {
			queryClient.invalidateQueries({queryKey: ['IInquiryRes']})
		},
	})
}