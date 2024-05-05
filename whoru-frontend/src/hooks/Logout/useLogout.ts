import { useNavigate } from "react-router-dom"
import { logoutApi } from "@/service/Logout/api";
import { useMutation } from "@tanstack/react-query";
import { useCookies } from 'react-cookie';

const useLogout = () => {
  const [, , removeCookie] = useCookies(['Refresh']);
  const navigate = useNavigate();
  return useMutation({
    mutationFn: () => logoutApi(),
    onSuccess: () => {
      console.log('로그아웃됬습니다.');
      localStorage.removeItem('AccessToken');
      removeCookie('Refresh', { path: '/' });
      navigate('/login');
    },
    onError: (err: Error) => {
      console.error('로그아웃에 실패했습니다.', err.message);
    }
  })
}

export default useLogout;