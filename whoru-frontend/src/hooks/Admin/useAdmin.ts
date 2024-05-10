import { getInquiryReq, getNotificationReq, patchNotificationReq, postNotificationReq } from "@/service/Admin/api"
import { IInquiry, INotification } from "@/types/Admin"
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query"

export const useInquiryDetail = (page: number, size: number, condition: number) => {
  return useQuery<IInquiry, Error>({
    queryKey: ['IInquiryRes', page, size, condition],
    queryFn: () => getInquiryReq(page, size, condition)
  })
}

export const useNotificationDetail = (page: number, size: number) => {
  return useQuery<INotification, Error>({
    queryKey: [page],
    queryFn: () => getNotificationReq(page, size),
  });
};

export const useNotificationCreate = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationKey: ['subject, content'],
    mutationFn: (formData: {subject: string, content: string}) => postNotificationReq(formData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['INotificationRes']});
      console.log('공지사항을 등록했습니다.')
    }
  })
}

export const useNotificationEdit = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ boardId, formData }: { boardId: number; formData: { subject: string; content: string } }) => 
      patchNotificationReq({ boardId, formData }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['INotificationRes']});
    }
  });
};