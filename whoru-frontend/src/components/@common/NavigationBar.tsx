import { useLocation, useNavigate } from 'react-router-dom';
import House from "@/assets/@common/House.png"
import FilledHouse from "@/assets/@common/FilledHouse.png"
import Post from "@/assets/@common/Post.png"
import FilledPost from "@/assets/@common/FilledPost.png"
import Profile from "@/assets/@common/Profile.png"
import FilledProfile from "@/assets/@common/FilledProfile.png"
import Pencil from "@/assets/@common/pencil-icon.svg"
import FilledPencil from "@/assets/@common/fill-pencil-icon.svg"
import FolderIcon from "@/assets/@common/folder-icon.svg"
import FilledFolderIcon from "@/assets/@common/fill-folder-icon.svg"

const NavigationBar = () => {
  const navigate = useNavigate();
  const { pathname } = useLocation();

  const handleTabClick = (tab : string) => {
    switch (tab) {
      case 'home':
        navigate('/');
        break;
      case 'mymessage':
        navigate('/mymessage');
        break;
      case 'post':
        navigate('/post');
        break;
      case 'daily':
        navigate('/daily');
        break;
      case 'profile':
        navigate('/profile');
        break;
    }
  }
  
  return (
    <div className="fixed bottom-0 max-w-[500px] z-[2] w-full h-[60px] bg-[#AEC3F1] px-9 flex justify-between items-center">
      <div className="" onClick={() => handleTabClick('home')}>
        <img src={pathname === '/' ? FilledHouse : House} className="w-8" />
      </div>
      <div className="" onClick={() => handleTabClick('mymessage')}>
        <img src={pathname === '/mymessage' ? FilledFolderIcon : FolderIcon} className="w-8" />
      </div>
      <div className="" onClick={() => handleTabClick('post')}>
        <img src={pathname === '/post' ? FilledPencil : Pencil} className="w-8 h-9" />
      </div>
      <div className="" onClick={() => handleTabClick('daily')}>
        <img src={pathname === '/daily' ? FilledPost : Post} className="w-8 h-9" />
      </div>
      <div className="" onClick={() => handleTabClick('profile')}>
        <img src={pathname === '/profile' ? FilledProfile : Profile} className="w-8 h-7" />
      </div>
    </div>
  )
}


export default NavigationBar;