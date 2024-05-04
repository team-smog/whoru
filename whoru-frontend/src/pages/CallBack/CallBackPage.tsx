import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useCookies } from 'react-cookie';

const CallBackPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [cookies] = useCookies(['RefreshToken']);

  useEffect(() => {
    const getTokens = () => {
      const accessToken = searchParams.get('accessToken');
      const refreshToken = cookies['RefreshToken'];

      if (accessToken && refreshToken) {
        localStorage.setItem('AccessToken', accessToken);
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
