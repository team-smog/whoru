import { getUserDetail } from "@/service/Auth/api"
import { useQuery } from "@tanstack/react-query"

export const useAuthReq = () => {
  return useQuery({
    queryKey: ['auth'],
    queryFn: () => getUserDetail(),
  })
}