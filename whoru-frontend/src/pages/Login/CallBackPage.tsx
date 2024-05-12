import { useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";

const CallBackPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const getTokens = () => {
      const accessToken = searchParams.get('accessToken');
      console.log(accessToken)
      if (accessToken) {
        localStorage.setItem('AccessToken', accessToken);
        navigate('/');
      } else {
        navigate('/login');
      }
    }
    getTokens();
  }, [navigate, searchParams]);

  return (
    <>
      로그인 중...
    </>
  )
}

export default CallBackPage;
