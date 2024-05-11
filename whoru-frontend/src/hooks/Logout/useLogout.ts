import { useNavigate } from "react-router-dom"
import { logoutApi } from "@/service/Logout/api";
import { useMutation } from "@tanstack/react-query";

const useLogout = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: () => logoutApi(),
    onSuccess: () => {
      console.log('로그아웃됬습니다.');
      localStorage.removeItem('AccessToken');
      navigate('/login');
    },
    onError: (err: Error) => {
      console.error('로그아웃에 실패했습니다.', err.message);
    }
  })
}

export default useLogout;