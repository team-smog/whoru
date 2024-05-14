import { axiosAuthInstance } from "@/apis/axiosInstance";
import { IUserInfo } from "@/types/User";

export const getUserDetail = async () : Promise<IUserInfo> => {
  const res = await axiosAuthInstance.get(`/member/profile`, {
    headers: {
      'Content-Type' : 'application/json'
    }
  })
  console.log(res);
  return res.data.data
}