import { axiosCookie } from "@/apis/axiosInstance";

export const logoutApi = async () => {
  const res = await axiosCookie.post(`/member/logout`)
  return res.data;
}