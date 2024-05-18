import { getAdminUserDetail, getUserDetail } from "@/service/Auth/api"
import { useMutation, useQuery } from "@tanstack/react-query"

export const useAuthReq = () => {
  return useQuery({
    queryKey: ['auth'],
    queryFn: () => getUserDetail(),
  })
}

export const useAdminLoginReq = () => {
  return useMutation({
    mutationFn: (formData: {id: string, pw:string}) => getAdminUserDetail(formData),
    onSuccess: () => {
      console.log('로그인에 성공했습니다.')
    },
  })
}

