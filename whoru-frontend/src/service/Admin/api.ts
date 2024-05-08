import { axiosAuthInstance } from "@/apis/axiosInstance";
import { IInquiryRes, INotificationRes } from "@/types/Admin";
import { APIResponse } from "@/types/model";

export const getInquiryReq = async (page: number, size: number, condition: number): Promise<APIResponse<IInquiryRes>> => {
  const {data} = await axiosAuthInstance.get(`/admin/board/inquiry`, {
    params: {
      page,
      size,
      condition
    },
  })
  console.log(data)
  return data;
}

export const getNotificationReq = async (page: number, size: number) : Promise<APIResponse<INotificationRes>> => {
  const {data} = await axiosAuthInstance.get(`/board/noti`, {
    params: {
      page,
      size,
    }
  })
  console.log(data);
  return data;
}