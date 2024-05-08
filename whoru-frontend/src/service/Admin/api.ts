import { axiosAuthInstance } from "@/apis/axiosInstance";
import { IInquiryRes } from "@/types/Admin";
import { APIResponse } from "@/types/model";

export const getInquiryReq = async (page: number, size: number, condition: number): Promise<APIResponse<IInquiryRes>> => {
  const res = await axiosAuthInstance.get(`/admin/board/inquiry`, {
    params: {
      page,
      size,
      condition
    },
  })
  console.log(res.data)
  return res.data;
}

export const getNotificationReq = async (page:number, size:number = 10) => {
  const res = await axiosAuthInstance.get(`/board/noti`, {
    params: {page:page, size},
    headers: { Authorization: `Bearer ${localStorage.getItem('AccessToken')}` }
  });
  console.log(res.data)
  return res.data;
};

export const postNotificationReq = async(formData: { subject: string, content: string }): Promise<APIResponse<string>> => {
  const res = await axiosAuthInstance.post(`/admin/board/noti`, JSON.stringify(formData), {
    headers: {
      'Content-Type' : 'application/json',
    }
  });
  console.log(res.data);
  return res.data;
};