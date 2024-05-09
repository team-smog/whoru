import logo from '@/assets/components/login/logo.png'
import cd from '@/assets/components/login/cd.png'
import KakaoLogin from '@/components/LoginPage/KakaoLogin';
import NaverLogin from '@/components/LoginPage/NaverLogin';

const LoginPage = () => {
  return (
    <>
      <div className='relative'>
        <img src={logo} className='px-8 pt-20'/>
        <img src={cd} className='absolute w-12 top-16 right-3'/>
        <div className="fixed bottom-48 flex flex-col px-8 gap-5 w-full max-w-[500px]">
          <KakaoLogin />
          <NaverLogin />
        </div>
      </div>
    </>
  )
};

export default LoginPage;