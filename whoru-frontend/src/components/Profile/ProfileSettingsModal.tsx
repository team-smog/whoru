import { useEffect, useState } from 'react'
import axios from 'axios'
import Cancel from '@/assets/@common/Cancel.png'
import './Profile.css'

const ProfileSettingsModal = () => {
	const [isPushNotificationEnabled, setIsPushNotificationEnabled] = useState(
		localStorage.getItem('isPushNotificationEnabled') === 'false' ? false : true
	)
	const [isModalOpen, setIsModalOpen] = useState(false)

	useEffect(() => {
		const fetchUserSettings = async () => {
			try {
				const response = await axios.get('https://k10d203.p.ssafy.io/api/member/profile', {
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
					},
				})
				if (response.data && response.data.pushAlarm !== undefined) {
					setIsPushNotificationEnabled(response.data.pushAlarm)
					localStorage.setItem('isPushNotificationEnabled', String(response.data.pushAlarm))
				}
			} catch (error) {
				console.error('사용자 설정 정보를 불러오는 중 에러 발생:', error)
			}
		}

		fetchUserSettings()
	}, [])

	const handleToggleChange = () => {
		setIsPushNotificationEnabled((prevState) => {
			const newState = !prevState
			updatePushNotificationSetting(newState)
			localStorage.setItem('isPushNotificationEnabled', String(newState))
			return newState
		})
	}

	const updatePushNotificationSetting = async (isEnabled: boolean) => {
		try {
			const response = await axios.patch(
				'https://k10d203.p.ssafy.io/api/member/push-alarm',
				{ pushAlarm: isEnabled },
				{
					headers: {
						'Content-Type': 'application/json',
						Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
					},
				}
			)
			console.log('푸시 알림 설정 업데이트:', response.data)
		} catch (error) {
			console.error('푸시 알림 설정 업데이트 중 에러 발생:', error)
			alert('푸시 알림 설정 업데이트 실패')
		}
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
							<p className=" pt-1 p-2 text-sm text-white">푸시 알림</p>
							<button className="pr-3 text-lg p-0" onClick={() => setIsModalOpen(false)}>
								<img src={Cancel} alt="Cancel" />
							</button>
						</div>
						<hr className="border-1 border-black" />
						<div className="centered-content">
							<p className="text-sm">
								푸시 알림
								<label className="toggle-container ml-20">
									<input type="checkbox" onChange={handleToggleChange} checked={isPushNotificationEnabled} />
									<span className="toggle-slider"></span>
								</label>
							</p>
						</div>
					</div>
				</div>
			)}
		</>
	)
}

export default ProfileSettingsModal
