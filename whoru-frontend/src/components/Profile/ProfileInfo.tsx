import { useState, useEffect } from 'react'
// import axios from 'axios'
import Profile from '@/assets/@common/Profile.png'
import { useDispatch } from 'react-redux'
import { setBoxCount, setIconUrl, setPushAlarm, setRole } from '@/stores/store'
import { axiosWithCredentialInstance } from '@/apis/axiosInstance'


const ProfileInfo = () => {
	const dispatch = useDispatch()
	const [userInfo, setUserInfo] = useState({
		userName: '',
		iconUrl: Profile,
		pushAlarm: true,
		deviceName: '',
		languageType: 'korean',
	})
	https://k10d203.p.ssafy.io/api/oauth2/authorization/kakao?redirect_uri=https://k10d203.p.ssafy.io/callback
	useEffect(() => {
		const fetchUserInfo = async () => {
			try {
				const response = await axiosWithCredentialInstance.get('https://codearena.shop/api/member/profile', {
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
		<div className="w-full max-w-[500px] px-24 pt-14 flex flex-col justify-center items-center">
			{/* <div className="pt-20 w-20 h-20"> */}
				<img src={userInfo.iconUrl} alt="Profile" className='h-28'/>
			{/* </div> */}
			{/* <div className="pt-20 pl-6"> */}
				<p className="text-xl pt-4">{userInfo.userName || '이름 없음'}</p>
			{/* </div> */}
		</div>
	)
}

export default ProfileInfo
