import kakao from '@/assets/login/kakao_login_medium_wide.png'

const KakaoLogin = () => {
  const link = `https://k10d203.p.ssafy.io/api/login/oauth2/authorization/kakao/?redirect\_uri=http://k10d203.p.ssafy.io/callback`

  const handleKakaoLogin = () => {
    window.location.href = link;
    console.log('카카오로그인')
  }
  return (
    <>
      <button className="flex items-center h-14 gap-3 bg-[#FEE500] p-2 px-2 rounded-xl">
        <img src={kakao} className='w-10'></img>
        <div className="absolute translate-x-1/2 pl-1" onClick={handleKakaoLogin}>
          <p>카카오로 로그인하기</p>
        </div>
      </button>
    </>
  );
}

export default KakaoLogin;