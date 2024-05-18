import { getAdminRoleDetail, getAdminUserDetail, getUserDetail } from '@/service/Auth/api'
import { useMutation, useQuery } from '@tanstack/react-query'
import { useNavigate } from 'react-router-dom'
import Swal from 'sweetalert2'

export const useAuthReq = () => {
	return useQuery({
		queryKey: ['auth'],
		queryFn: () => getUserDetail(),
	})
}

export const useAdminAuthReq = () => {
  return useQuery({
    queryKey: ['admin'],
    queryFn: () => getAdminRoleDetail(),
  })
}

export const useAdminLoginReq = () => {
  const navigate = useNavigate();
  return useMutation({
    mutationFn: (formData: {id: string, pw:string}) => getAdminUserDetail(formData),
    onSuccess: () => {
      console.log('로그인에 성공했습니다.')
      navigate('/admin')
      Swal.fire({
        title: '로그인에 성공했습니다',
        icon: 'success',
        timer: 3000,
      })
    },
    onError: () => {
      console.log('로그인에 실패했습니다.')
      Swal.fire({
        title: '로그인에 실패했습니다.',
        icon: 'error',
        timer: 3000,
      });
    }
  })
}
