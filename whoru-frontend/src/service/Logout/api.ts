import { axiosCookie } from "@/apis/axiosInstance";

export const logoutApi = async () => {
  const accesstoken = localStorage.getItem('AccessToken');
  console.log(accesstoken)
  const res = await axiosCookie.post(`/member/logout`, {
    headers: {
      'Authorization': `Bearer ${accesstoken}`,
    },
    withCredentials:true
  });
  console.log(res.data)

  return res.data;
}