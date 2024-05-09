import { getInquiryReq, getNotificationReq, postNotificationReq } from "@/service/Admin/api"
import { IInquiryRes, INotification } from "@/types/Admin"
import { APIResponse } from "@/types/model"
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query"

export const useInquiryDetail = (page: number, size: number, condition: number) => {
  return useQuery<APIResponse<IInquiryRes>, Error>({
    queryKey: ['IInquiryRes', page, size, condition],
    queryFn: () => getInquiryReq(page, size, condition)
  })
}

export const useNotificationDetail = (page: number, size: number) => {
  return useQuery<APIResponse<INotification[]>, Error>({
    queryKey: ['INotificationRes', page, size],
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