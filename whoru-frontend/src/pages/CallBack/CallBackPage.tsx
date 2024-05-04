import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { Cookies } from 'react-cookie';

const CallBackPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const cookies = new Cookies();
  
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const interval = setInterval(() => {
      const accessToken = searchParams.get('accessToken');
      const refreshToken = cookies.get('Refresh');
      
      if (accessToken && refreshToken) {
        clearInterval(interval);
        localStorage.setItem('AccessToken', accessToken);
        localStorage.setItem('RefreshToken', refreshToken);
        setLoading(false);
        navigate('/');
      }
    }, 500);

    return () => clearInterval(interval);
  }, [navigate, searchParams, cookies]);

  useEffect(() => {
    const timeout = setTimeout(() => {
      setLoading(false);
      navigate('/login');
    }, 5000);

    return () => clearTimeout(timeout);
  }, [navigate]);

  return (
    <>
      {loading ? <div>로그인 중...</div> : null}
    </>
  );
};

export default CallBackPage;