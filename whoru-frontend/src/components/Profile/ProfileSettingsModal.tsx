import { useState } from 'react'
import Cancel from '@/assets/@common/Cancel.png'
import './Profile.css'

const ProfileSettingsModal = () => {
	const [isPushNotificationEnabled, setIsPushNotificationEnabled] = useState(false)
	const [isModalOpen, setIsModalOpen] = useState(false)

	const handleToggleChange = () => {
		setIsPushNotificationEnabled((prevState) => !prevState)
	}

	const handleNotificationSettingsClick = () => {
		setIsModalOpen(true)
	}

	return (
		<>
			<div className="pl-12 pt-4" onClick={handleNotificationSettingsClick}>
				푸시 알림 설정
			</div>
			{isModalOpen && (
				<div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
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
		</>
	)
}

export default ProfileSettingsModal
