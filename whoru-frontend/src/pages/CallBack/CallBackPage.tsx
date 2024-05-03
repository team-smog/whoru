import { useEffect } from "react";
// import { useDispatch } from "react-redux";
import { useNavigate, useSearchParams } from "react-router-dom";
import { Cookies } from 'react-cookie';

const CallBackPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  // const dispatch = useDispatch();
  const cookies = new Cookies();

  
  useEffect(() => {
    const getTokens = () => {
      const accessToken = searchParams.get('accessToken');
      const userId = searchParams.get('user-id');

      if (accessToken !== null && userId !== null ) {
        cookies.set('AccessToken', accessToken, { path: '/', secure: true, httpOnly: false });
        cookies.set('UserId', userId, { path: '/', secure: true, httpOnly: false });
        navigate('/');
      } else {
        navigate('/login')
      }
    }
      getTokens()
    }, [])

  return (
    <>
      로그인 중...
    </>
  )
}

export default CallBackPage;