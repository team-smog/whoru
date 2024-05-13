import { setUser } from "@/stores/userStore";
import { useAuthReq } from "@/hooks/Auth/useAuth";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useSearchParams } from "react-router-dom";

const CallBackPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const dispatch = useDispatch();
  const { data: userData, isError, isLoading } = useAuthReq(); // API 호출의 결과를 받아오는 React Query 훅

  useEffect(() => {
    const accessToken = searchParams.get('accessToken');
    console.log(accessToken);

    if (!accessToken) {
      navigate('/login');
      return;
    }

    localStorage.setItem('AccessToken', accessToken); // 액세스 토큰을 로컬 스토리지에 저장

    if (isLoading) return; 

    if (isError) {
      navigate('/login');
      return;
    }

    if (userData) {
      try {
        dispatch(setUser(userData));
        localStorage.setItem('userData', JSON.stringify(userData))
        navigate('/');
      } catch (error) {
        console.log(error)
      }
      

    }
  }, [navigate, searchParams, dispatch, userData, isError, isLoading]);

  return <div>로그인 중...</div>;
};

export default CallBackPage;