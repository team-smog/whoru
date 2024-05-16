import { useState, useEffect } from 'react'
import axios from 'axios'
import Profile from '@/assets/@common/Profile.png'
import { useDispatch } from 'react-redux'
import { setBoxCount, setIconUrl, setPushAlarm, setRole } from '@/stores/store'


const ProfileInfo = () => {
	const dispatch = useDispatch()
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
				const response = await axios.get('https://codearena.shop/api/member/profile', {
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
					},
				})
				if (response.data && response.data.data) {
					const iconUrl = response.data.data.iconUrl !== 'null' ? response.data.data.iconUrl : Profile
					setUserInfo((prev) => ({ ...prev, ...response.data.data, iconUrl: iconUrl }))
					dispatch(setBoxCount(response.data.data.boxCount))
					dispatch(setRole(response.data.data.role))
					dispatch(setPushAlarm(response.data.data.pushAlarm))
          if(response.data.data.iconUrl !== "null"){
            dispatch(setIconUrl(response.data.data.iconUrl))
          }else{
            dispatch(setIconUrl(Profile))
          }
				} else {
					setUserInfo((prev) => ({ ...prev, ...response.data }))
				}
			} catch (error) {
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
