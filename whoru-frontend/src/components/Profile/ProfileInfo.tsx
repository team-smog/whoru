import { useState, useEffect } from 'react'
import axios from 'axios'
import Profile from '@/assets/@common/Profile.png'
// import { useDispatch } from 'react-redux'
// import { setBoxCount } from '@/stores/userStore'

const ProfileInfo = () => {
  // const dispatch = useDispatch()
	const [userInfo, setUserInfo] = useState({
		userName: '',
		iconUrl: Profile,
		pushAlarm: true,
		deviceName: '',
		languageType: 'korean',
	})

	useEffect(() => {
		const fetchUserInfo = async () => {
			try {
				const response = await axios.get('https://k10d203.p.ssafy.io/api/member/profile', {
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
					},
				})
				console.log('사용자 정보?:', response.data)
				if (response.data && response.data.data) {
					const iconUrl = response.data.data.iconUrl !== 'null' ? response.data.data.iconUrl : Profile
					setUserInfo((prev) => ({ ...prev, ...response.data.data, iconUrl: iconUrl }))
          console.log("hhhhh",response.data.data.boxCount)
          // dispatch(setBoxCount(response.data.data.boxCount))
				} else {
					setUserInfo((prev) => ({ ...prev, ...response.data }))
				}
			} catch (error) {
				console.error('사용자 정보를 불러오는 중 에러 발생:', error)
			}
		}

		fetchUserInfo()
	}, [])
  

	return (
		<div className="flex flex-row justify-center">
			<div className="pt-20 w-20 h-20">
				<img src={userInfo.iconUrl} alt="Profile" />
			</div>
			<div className="pt-20 pl-6">
				<p className="text-xl pt-6">{userInfo.userName || '이름 없음'}</p>
			</div>
		</div>
	)
}

export default ProfileInfo
