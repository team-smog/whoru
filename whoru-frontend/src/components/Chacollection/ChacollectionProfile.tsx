import Profile from '@/assets/@common/Profile.png'
import './ProfileInfo.css'
import Cancel from '@/assets/@common/Cancel.png'
import ChacollectionModal from './ChacollectionModal'

const ChacollectionProfile = () => {
	return (
		<div className="relative chacollection-profile-container">
			<p className="text-xl pt-32 pl-4">캐릭터 도감</p>
			<p className="text-xs pt-2 pl-4 text-[#797979]">원하는 캐릭터로 자신의 프로필을 바꿀 수 있어요.</p>
			<div className="flex flex-wrap pt-4 scrollable-container">
				<div className="profile-container">
					<img src={Profile} alt="Profile" />
				</div>
				<div className="profile-container">
					<img src={Cancel} alt="Profile" />
				</div>
				<div className="profile-container">
					<img src={Cancel} alt="Profile" />
				</div>
				<div className="profile-container">
					<img src={Profile} alt="Profile" />
				</div>
				<div className="profile-container">
					<img src={Profile} alt="Profile" />
				</div>
				<div className="profile-container">
					<img src={Profile} alt="Profile" />
				</div>
			</div>
			<div className="fixed flex justify-center w-full max-w-[500px] bottom-20 pt-4 m-auto px-3">
				<ChacollectionModal />
			</div>
		</div>
	)
}

export default ChacollectionProfile
