import Announcement from '@/assets/@common/Announcement.png'
import Randombox from '@/assets/@common/Randombox.png'
import Logout from '@/assets/@common/Logout.png'
import { useNavigate } from 'react-router-dom'

const ProfileActions = () => {
	const navigate = useNavigate()

	const handleAnnouncementClick = () => {
		navigate('/announcement')
	}

	const handleCharacterCollectionClick = () => {
		navigate('/chacollection')
	}

	return (
		<div className="flex flex-row h-24 justify-between items-center px-4">
			<div className="flex flex-col items-center justify-center w-28 h-20 gap-2"   onClick={handleAnnouncementClick}>
				<img className="w-7 h-8" src={Announcement} alt="Announcement" />
				<p className="text-xs">공지사항</p>
			</div>
			<div className="flex flex-col items-center justify-center pt-1 w-28 h-20 gap-3" onClick={handleCharacterCollectionClick}>
				<img className="w-6 h-[26px]" src={Randombox} alt="Randombox" />
				<p className="text-xs">캐릭터 도감</p>
			</div>
			<div className="flex flex-col items-center justify-center w-28 h-20 gap-2">
				<img className="w-7 h-8" src={Logout} alt="Logout" />
				<p className="text-xs">로그아웃</p>
			</div>
		</div>
	)
}

export default ProfileActions
