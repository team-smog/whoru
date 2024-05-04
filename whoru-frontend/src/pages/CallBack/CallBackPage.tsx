import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { Cookies } from 'react-cookie';

const CallBackPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const cookies = new Cookies();
  
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const accessToken = searchParams.get('accessToken');
    const refreshToken = cookies.get('Refresh');

    if (accessToken && refreshToken) {
      localStorage.setItem('AccessToken', accessToken);
      localStorage.setItem('RefreshToken', refreshToken);
      setLoading(false);
      navigate('/');
    } else if (loading) {
      setTimeout(() => {
        setLoading(false);
        navigate('/login');
      }, 1000);
    }
  }, [navigate, searchParams]);

  return (
    <>
      {loading ? <div>로그인 중...</div> : null}
    </>
  );
};

export default CallBackPage;