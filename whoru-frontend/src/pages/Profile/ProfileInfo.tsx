import React from 'react';
import Profile from '@/assets/@common/Profile.png';

const ProfileInfo = () => {
    return (
        <div className="flex flex-row">
            <div className="pt-20 w-20 h-20">
                <img src={Profile} alt="Profile" />
            </div>
            <div className="pt-20 pl-6">
                <p className="text-xl pt-2">유저이름</p>
                <p className="text-xs pt-1">유저이메일</p>
            </div>
        </div>
    );
};

export default ProfileInfo;
