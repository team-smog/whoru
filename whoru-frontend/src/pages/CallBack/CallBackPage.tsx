import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { Cookies } from 'react-cookie';

const CallBackPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const cookies = new Cookies();
  let refreshToken = cookies.get('Refresh');

  useEffect(() => {
    const getTokens = () => {
      const accessToken = searchParams.get('accessToken');

      if (accessToken && refreshToken) {
        localStorage.setItem('AccessToken', accessToken);
        localStorage.setItem('RefreshToken', refreshToken);
        navigate('/');
      } else {
        navigate('/login');
      }
    };
    getTokens();
  }, [navigate, searchParams, refreshToken]);

  return (
    <>
      로그인 중...
    </>
  );
};

export default CallBackPage;
