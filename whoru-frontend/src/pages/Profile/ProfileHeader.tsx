import Header from '@/components/@common/Header';
import Bell from '@/assets/@common/Bell.png';

const ProfileHeader = () => {
    const info = {
        left_1: 'Profile',
        left_2: null,
        center: null,
        right: <img src={Bell} alt="Alarm" />,
    };

    return <Header info={info} />;
};

export default ProfileHeader;
