import { useAuthReq } from '@/hooks/Auth/useAuth'
import { setBoxCount, setIconUrl, setPushAlarm, setRole } from '@/stores/store'
import { useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate, useSearchParams } from 'react-router-dom'

const CallBackPage = () => {
	const navigate = useNavigate()
	const [searchParams] = useSearchParams()
	const dispatch = useDispatch()
	const { data: userData, isError, isLoading } = useAuthReq()

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
