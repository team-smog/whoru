import Header, { IHeaderInfo } from '@/components/@common/Header'
import Bell from '@/assets/@common/Bell.png'
import NavigationBar from '@/components/@common/NavigationBar'
import Profile from '@/assets/@common/Profile.png'
import Announcement from '@/assets/@common/Announcement.png'
import Logout from '@/assets/@common/Logout.png'
import Randombox from '@/assets/@common/Randombox.png'
import Cancel from '@/assets/@common/Cancel.png'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import './Profile.css'

const ProfilePage = () => {
	const info: IHeaderInfo = {
		left_1: 'Profile',
		left_2: null,
		center: null,
		right: <img src={Bell} alt="Alarm" />,
	}

	const [isModalOpen, setIsModalOpen] = useState(false)
	const [isPushNotificationEnabled, setIsPushNotificationEnabled] = useState(false)

	const handleNotificationSettingsClick = () => {
		setIsModalOpen(true)
	}
	const navigate = useNavigate()

	const handleAnnouncementClick = () => {
		navigate('/announcement')
	}

	const handleCharacterCollectionClick = () => {
		navigate('/chacollection')
	}

	const handleInquiryClick = () => {
		navigate('/Inquiry')
	}

	const handleToggleChange = () => {
		setIsPushNotificationEnabled((prevState) => !prevState)
	}

	return (
		<>
			<div>
				<Header info={info} />
			</div>
			<div className="pl-20">
				<div className="flex flex-row">
					<div className="pt-20 w-20 h-20">
						<img src={Profile} alt="Profile" />
					</div>
					<div className="pt-20 pl-6">
						<p className="text-xl pt-2">유저이름</p>
						<p className="text-xs pt-1">유저이메일</p>
					</div>
				</div>
			</div>
			<hr className="border-1 border-black mt-10 px-10" />
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
			<hr className="border-1 border-black pt-5 px-10" />
			<p className="pl-12 pt-4">언어 설정</p>
			<p className="pl-12 pt-4" onClick={handleNotificationSettingsClick}>
				푸시 알림 설정
			</p>
			<p className="pl-12 pt-4" onClick={handleInquiryClick}>
				문의하기
			</p>
			{isModalOpen && (
				<div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 ">
					<div className="w-80 h-32 bg-white rounded-lg border-solid border-2 border-black">
						<div className="flex flex-row justify-between rounded-t-lg bg-[#D78DDD]">
							<h2 className="pt-1 p-2 ">푸시 알림 설정</h2>
							<button className="pr-3 text-lg p-0" onClick={() => setIsModalOpen(false)}>
								<img src={Cancel} alt="Cancel" />
							</button>
						</div>
						<hr className="border-1 border-black" />
						<div className="flex flex-row">
							<p className="pt-3 pl-3 text-sm">현재 연결된 기기</p>
							<p className="pt-3 pl-3 text-sm text-[#CCCCCC]">기기명</p>
						</div>
						<p className="pt-3 pl-3 text-sm">
							푸시 알림
							<label className="toggle-container ml-5">
								<input type="checkbox" onChange={handleToggleChange} checked={isPushNotificationEnabled} />
								<span className="toggle-slider"></span>
							</label>
						</p>
					</div>
				</div>
			)}
			<div>
				<NavigationBar />
			</div>
		</>
	)
}

export default ProfilePage
