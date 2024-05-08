import { getInquiryReq, getNotificationReq } from "@/service/Admin/api"
import { IInquiryRes, INotificationRes } from "@/types/Admin"
import { APIResponse } from "@/types/model"
import { useQuery } from "@tanstack/react-query"

export const useInquiryDetail = (page: number, size: number, condition: number) => {
  return useQuery<APIResponse<IInquiryRes>, Error>({
    queryKey: ['IInquiryRes', page, size, condition],
    queryFn: () => getInquiryReq(page, size, condition)
  })
}

export const useNotificationDetail = (page: number, size: number) => {
  return useQuery<APIResponse<INotificationRes>, Error>({
    queryKey: ['INotificationRes', page, size],
    queryFn: () => getNotificationReq(page, size)
  })
}