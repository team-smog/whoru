import { useEffect, useState } from 'react'
import Cancel from '@/assets/@common/Cancel.png'
import './Profile.css'
import { useDispatch, useSelector } from 'react-redux'
import { setPushAlarm } from '@/stores/store'
import { axiosWithCredentialInstance } from '@/apis/axiosInstance'
import { requestPermission } from "@/FirebaseUtil.js";

const ProfileSettingsModal = () => {
	const pushAlarm = useSelector((state: any) => state.user.pushAlarm);
	// const boxCount = useSelector((state: any) => state.user.boxCount);
	const dispatch = useDispatch();
	const [isPushNotificationEnabled, setIsPushNotificationEnabled] = useState<boolean>(pushAlarm);
	const [isModalOpen, setIsModalOpen] = useState(false);

	// useEffect(() => {
	// 	console.log(boxCount)
	// 	console.log(pushAlarm)
	// }, [boxCount, pushAlarm])

	const FCMSetToken = async () => {
		// const token = await requestPermission();
		// console.log("token",token)
		// return token;
		// localStorage.getItem('FCMToken');
		return await requestPermission();
	}
	
	// const token = FCMSetToken();
	
	const fetchDataFCM = async (token: string|null) => {
		try {
			// console.log("token1",token)
			if (token === null) {
				return;
			}
			await fetch(`https://codearena.shop/api/member/updatefcm?fcmToken=${token}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
				},
			});
			console.log("fcm 토큰 저장 api 요청 완료")
		} catch (error: any) {
			console.error(error);
			console.log("fcm 토큰 저장 api 요청 실패")
		}
	};


	// useEffect(() => {
	// 	token.then(() => {
	// 		const FCM = localStorage.getItem('FCMToken');
	// 		console.log("FCM",FCM);
	// 		fetchDataFCM(FCM);
	// 		// console.log("res token",res);
	// 		// localStorage.setItem('FCMToken', res);
	// 	})
	// 	// fetchDataFCM(token);
	// }, [token]);

	useEffect(() => {
		const fetchUserSettings = async () => {
			try {
				const res = await axiosWithCredentialInstance.get('member/profile', {
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
					},
				})
				// console.log(res.data.data.pushAlarm)
				if (res.data && res.data.data.pushAlarm !== undefined) {
					setIsPushNotificationEnabled(res.data.data.pushAlarm)
				}
			} catch (error) {
				console.error('사용자 설정 정보를 불러오는 중 에러 발생:', error);
      }
		}

		fetchUserSettings()
	}, [])

	const handleToggleChange = () => {
		setIsPushNotificationEnabled((prevState) => {
			const newState = !prevState
			updatePushNotificationSetting(newState)
			console.log('asliejfialsejfalisejf', setPushAlarm(newState))
			return newState
		})
	}

	const updatePushNotificationSetting = async (isEnabled: boolean) => {
		try {
			const res = await axiosWithCredentialInstance.patch(
				'member/push-alarm',
				{ pushAlarm: isEnabled },
				{
					headers: {
						'Content-Type': 'application/json',
						Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
					},
				}
			)
			console.log('푸시 알림 설정 업데이트:', res.data, isEnabled)
			dispatch(setPushAlarm(isEnabled))
			if (isEnabled) {
				const token = await FCMSetToken();
				fetchDataFCM(token);
			}
		} catch (error) {
		}
	}

	const handleNotificationSettingsClick = () => {
		setIsModalOpen(true)
	}

	return (
		<>
			<div className="pl-12 pt-2" onClick={handleNotificationSettingsClick}>
				푸시 알림 설정
			</div>
			{isModalOpen && (
				<div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
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
