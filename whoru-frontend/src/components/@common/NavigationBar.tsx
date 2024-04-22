import { useLocation, useNavigate } from 'react-router-dom';
import House from "@/assets/@common/House.png"
import FilledHouse from "@/assets/@common/FilledHouse.png"
import Post from "@/assets/@common/Post.png"
import FilledPost from "@/assets/@common/FilledPost.png"
import Profile from "@/assets/@common/Profile.png"
import FilledProfile from "@/assets/@common/FilledProfile.png"

const NavigationBar = () => {
  const navigate = useNavigate();
  const { pathname } = useLocation();

  const handleTabClick = (tab : string) => {
    switch (tab) {
      case 'home':
        navigate('/');
        break;
      case 'post':
        navigate('/post');
        break;
      case 'profile':
        navigate('/profile');
        break;
    }
  }
  
  return (
    <div className="fixed bottom-0 max-w-[500px] z-[2] w-full h-[60px] border-t-[0.5px] border-gray px-9 flex justify-between items-center">
      <div className="" onClick={() => handleTabClick('home')}>
        <img src={pathname === '/' ? FilledHouse : House} className="w-9" />
      </div>
      <div className="" onClick={() => handleTabClick('post')}>
        <img src={pathname === '/post' ? FilledPost : Post} className="w-9" />
      </div>
      <div className="" onClick={() => handleTabClick('profile')}>
        <img src={pathname === '/profile' ? FilledProfile : Profile} className="w-9 h-9" />
      </div>
    </div>
  )
}


export default NavigationBar;