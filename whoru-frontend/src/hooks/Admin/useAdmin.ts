import { getInquiryReq } from "@/service/Admin/api"
import { IInquiryRes } from "@/types/Admin"
import { APIResponse } from "@/types/model"
import { useQuery } from "@tanstack/react-query"

export const useInquiryDetail = (page: number, size: number, condition: number) => {
  return useQuery<APIResponse<IInquiryRes>, Error>({
    queryKey: ['IInquiryRes', page, size, condition],
    queryFn: () => getInquiryReq(page, size, condition)
  })
}