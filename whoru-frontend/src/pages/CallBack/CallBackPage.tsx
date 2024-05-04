import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { Cookies } from 'react-cookie';

const CallBackPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const cookies = new Cookies();

  useEffect(() => {
    const getTokens = () => {
      const accessToken = searchParams.get('accessToken');
      const userId = searchParams.get('user-id');
      const refreshToken = cookies.get('RefreshToken');

      if (accessToken && userId && refreshToken) {
        localStorage.setItem('AccessToken', accessToken);
        localStorage.setItem('UserId', userId);
        localStorage.setItem('RefreshToken', refreshToken);
        navigate('/');
      } else {
        navigate('/login');
      }
    }
    getTokens();
  }, [navigate, searchParams, cookies]);

  return (
    <>
      로그인 중...
    </>
  )
}

export default CallBackPage;
