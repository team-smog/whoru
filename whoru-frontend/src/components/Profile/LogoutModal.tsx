import Modal from '../@common/Modal';
import { useNavigate } from 'react-router-dom';
import useLogout from '@/hooks/Logout/useLogout';
import Swal from 'sweetalert2';
import Sad from '@/assets/Sad.png';

const LogoutModal = ({ onClose }: { onClose: () => void }) => {
  const navigate = useNavigate();
  const logout = useLogout();

  const handleLogout = () => {
    logout.mutate();
    Swal.fire({
      title: '로그아웃되었습니다.',
      icon: 'success'
    });
    navigate('/login');
  };

  return (
    <Modal width="300px" height="auto" title="" onClose={onClose}>
      <div className="flex flex-col items-center px-4 pb-6">
        <div className="">
          <img className="w-16" src={Sad} />
        </div>
        <div className="py-2">
          <p className="text-xs">로그아웃 하시겠습니까 ?</p>
        </div>
        <div className="flex justify-center space-x-2 pt-4">
          <button className="bg-[#5959E7] w-20 text-white px-4 py-1 rounded-lg text-xs" onClick={handleLogout}>
            로그아웃
          </button>
          <button className="bg-gray-500 w-20 text-white px-4 py-1 rounded-lg text-xs" onClick={onClose}>
            취소
          </button>
        </div>
      </div>
    </Modal>
  );
};

export default LogoutModal;