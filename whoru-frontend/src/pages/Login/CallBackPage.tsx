import { useAuthReq } from '@/hooks/Auth/useAuth'
import { setBoxCount, setIconUrl, setPushAlarm, setRole } from '@/stores/store'
import { useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { requestPermission } from "@/FirebaseUtil.js";

const CallBackPage = () => {
	const navigate = useNavigate()
	const [searchParams] = useSearchParams()
	const dispatch = useDispatch()
	const { data: userData, isError, isLoading } = useAuthReq()
	
	const FCMSetToken = async () => {
		// const token = await requestPermission();
		// console.log("token",token)
		// return token;
		// localStorage.getItem('FCMToken');
		return await requestPermission();
	}
	
	const token = FCMSetToken();
	
	const fetchDataFCM = async (token: string|null) => {
		try {
			console.log("token1",token)
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


	useEffect(() => {
		token.then(() => {
			const FCM = localStorage.getItem('FCMToken');
			console.log("FCM",FCM);
			fetchDataFCM(FCM);
			// console.log("res token",res);
			// localStorage.setItem('FCMToken', res);
		})
		// fetchDataFCM(token);
	}, [token]);

	useEffect(() => {
		const accessToken = searchParams.get('accessToken')
		console.log(accessToken)

		if (!accessToken) {
			navigate('/login')
			return
		}

		localStorage.setItem('AccessToken', accessToken)

		if (isLoading) return

		if (isError) {
			navigate('/login')
			console.log(isError)
			return
		}

		if (userData) {
			console.log(userData)
			dispatch(setBoxCount(userData.boxCount))
			dispatch(setRole(userData.role))
			dispatch(setPushAlarm(userData.pushAlarm))
			dispatch(setIconUrl(userData.iconUrl))
			console.log(userData.iconUrl)
			navigate('/')
		} else {
			console.log('error')
		}
	}, [navigate, searchParams, dispatch, userData, isError, isLoading])

	return <div>로그인 중...</div>
}

export default CallBackPage;
