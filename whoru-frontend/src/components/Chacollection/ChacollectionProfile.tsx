import { useState, useEffect } from 'react'
import axios from 'axios'
import ChacollectionModals from './ChacollectionModal'
import './ProfileInfo.css'
import { useDispatch, useSelector } from 'react-redux'
import { setIconUrl } from '@/stores/store'
import Swal from 'sweetalert2'

interface Icon {
	iconId: string
	iconUrl: string
	iconGrade: string
	isDuplicat: boolean
	isAvailable: boolean
}

const ChacollectionProfile: React.FC = () => {
	const [icons, setIcons] = useState<Icon[]>([])
	const iconUrl = useSelector((state: any) => state.user.iconUrl)
	const [profileImageUrl, setProfileImageUrl] = useState<string>(iconUrl)
	// const [profileImageUrl, setProfileImageUrl] = useState<string>(Profile);
	const dispatch = useDispatch()

	useEffect(() => {}, [iconUrl])

	useEffect(() => {
		const savedIconUrl = localStorage.getItem('selectedProfileImage')
		if (savedIconUrl) {
			setProfileImageUrl(savedIconUrl)
		}
		fetchIcons()
	}, [])

	const fetchIcons = async () => {
		try {
			const res = await axios.get(`https://k10d203.p.ssafy.io/api/collects/icons`, {
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
				},
			})
			if (res.data && res.data.data) {
				setIcons(res.data.data.data)
			} else {
				setIcons([])
			}
		} catch (error) {
			setIcons([])
		}
	}

	const changeIcon = async (iconId: string, iconUrl: string) => {
		try {
			const res = await axios.patch('https://k10d203.p.ssafy.io/api/member/icon', null, {
				params: { iconId },
				headers: {
					Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
				},
			})

			if (res.data.data) {
				dispatch(setIconUrl(iconUrl))
				setProfileImageUrl(iconUrl)
				localStorage.setItem('selectedProfileImage', iconUrl)
				Swal.fire('프로필 아이콘이 변경되었습니다.', '', 'success')
			}
		} catch (error) {}
	}

	return (
		<div>
			<div className="flex justify-center">
				<div className="pt-20 w-[120px] h-[120px]">
					<img src={profileImageUrl} alt="Profile" />
					<p className="flex justify-center text-xs pt-2">현재 프로필</p>
				</div>
			</div>
			<div className="relative chacollection-profile-container">
				<p className="text-xl pt-32 pl-4">캐릭터 도감</p>
				<p className="text-xs pt-2 pl-4 text-[#797979]">원하는 캐릭터로 자신의 프로필을 바꿀 수 있어요.</p>
				<div className="flex justify-center flex-wrap pt-4 scrollable-container h-[calc(100vh-420px)]">
					{icons.map((icon, index) => (
						<div
							className={`profile-container ${!icon.isAvailable ? 'unavailable-icon' : ''}`}
							key={index}
							onClick={() => icon.isAvailable && changeIcon(icon.iconId, icon.iconUrl)}
							style={!icon.isAvailable ? { backgroundColor: 'rgba(128, 128, 128, 0.5)' } : {}}
						>
							<img src={icon.iconUrl} alt={`Profile Icon ${index}`} />
						</div>
					))}
				</div>

				<div className="fixed flex justify-center w-full max-w-[500px] bottom-20 pt-4 m-auto px-3">
					<ChacollectionModals onAction={fetchIcons} />
				</div>
			</div>
		</div>
	)
}

export default ChacollectionProfile
