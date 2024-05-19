import google from '@/assets/login/google.png'

const GoogleLogin = () => {
  // 배포용
  // const link = `https://codearena.shop/api/login/oauth2/authorization/google/?redirect\_uri=https://codearena.shop/callback`
  const link = `https://k10d203.p.ssafy.io/api/oauth2/authorization/kakao?redirect_uri=https://k10d203.p.ssafy.io/callback`
  
  // const link = `https://codearena.shop/api/login/oauth2/authorization/google/`

  const handleGoogleLogin = () => {
    window.location.href = link
    console.log('구글 로그인')
  }
  return (
    <>
      <button className="flex items-center h-14 gap-3 bg-white p-2 px-2 rounded-xl" onClick={handleGoogleLogin}>
        <img src={google} className='w-10'></img>
        <div className="absolute translate-x-1/2" >
          <p>Google 계정으로 로그인</p>
        </div>
      </button>
    </>
  );
}

export default GoogleLogin;