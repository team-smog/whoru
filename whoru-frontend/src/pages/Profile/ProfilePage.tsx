import React from 'react';
import { Navigate } from 'react-router-dom';
import ProfileHeader from './ProfileHeader';
import ProfileInfo from './ProfileInfo';
import ProfileActions from './ProfileActions';
import ProfileSettingsModal from './ProfileSettingsModal';
import NavigationBar from '@/components/@common/NavigationBar';

const ProfilePage = () => {
    const handleInquiryClick = () => {
        return <Navigate to="/Inquiry" />;
    };

    return (
        <>
            <ProfileHeader />
            <div className="pl-20">
                <ProfileInfo />
            </div>
            <hr className="border-1 border-black mt-10 px-10" />
            <ProfileActions />
            <hr className="border-1 border-black pt-5 px-10" />
            <p className="pl-12 pt-4">언어 설정</p>
            <ProfileSettingsModal />
            <p className="pl-12 pt-4" onClick={handleInquiryClick}>
                문의하기
            </p>
            <NavigationBar />
        </>
    );
};

export default ProfilePage;
