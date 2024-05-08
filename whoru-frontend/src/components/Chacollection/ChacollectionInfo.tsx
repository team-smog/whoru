import { useEffect, useState } from 'react';
import axios from 'axios';
import Profile from '@/assets/@common/Profile.png'

const ChacollectionInfo = () => {
    const [profileImageUrl, setProfileImageUrl] = useState(Profile);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchProfileImage = async () => {
            try {
                const response = await axios.get('API_URL_HERE');
                if (response.data.imageUrl) {
                    setProfileImageUrl(response.data.imageUrl);
                }
                else{
                  setProfileImageUrl(Profile);
                }
                setLoading(false);
            } catch (error) {
                console.error('이미지를 가져오는 중 오류가 발생했습니다:', error);
                setLoading(false);
                setProfileImageUrl(Profile);
            }
        };

        fetchProfileImage();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="flex justify-center">
            <div className="pt-20 w-[120px] h-[120px]">
                <img src={profileImageUrl} alt="Profile" />
                <p className="flex justify-center text-xs pt-2">현재 프로필</p>
            </div>
        </div>
    );
};

export default ChacollectionInfo;
