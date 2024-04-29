import React from 'react';
import Announcement from '@/assets/@common/Announcement.png';
import Randombox from '@/assets/@common/Randombox.png';
import Logout from '@/assets/@common/Logout.png';
import { useNavigate } from 'react-router-dom';

const ProfileActions = () => {
    const navigate = useNavigate();

    const handleAnnouncementClick = () => {
        navigate('/announcement');
    };

    const handleCharacterCollectionClick = () => {
        navigate('/chacollection');
    };

    return (
        <div className="flex flex-row justify-between pt-4 px-4">
            <div className="flex flex-col items-center justify-center w-28 h-20" onClick={handleAnnouncementClick}>
                <img className="w-10 h-10" src={Announcement} alt="Announcement" />
                <p className="text-xs">공지사항</p>
            </div>
            <div className="flex flex-col items-center justify-center w-28 h-20" onClick={handleCharacterCollectionClick}>
                <img className="w-8 h-8 pb-2" src={Randombox} alt="Randombox" />
                <p className="text-xs">캐릭터 도감</p>
            </div>
            <div className="flex flex-col items-center justify-center w-28 h-20">
                <img className="w-10 h-10" src={Logout} alt="Logout" />
                <p className="text-xs">로그아웃</p>
            </div>
        </div>
    );
};

export default ProfileActions;
