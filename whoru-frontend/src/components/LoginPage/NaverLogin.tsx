import naver from '@/assets/components/login/btnG_icon_square.png'

const NaverLogin = () => {
  return (
    <>
      <button className="flex items-center h-14 border-none bg-[#03C75A] p-2 px-2 rounded-xl">
        <img src={naver} className='w-10'/>
        <div className="absolute translate-x-1/2 pl-1 text-white">
          네이버로 로그인하기
        </div>
      </button>
    </>
  );
}

export default NaverLogin;