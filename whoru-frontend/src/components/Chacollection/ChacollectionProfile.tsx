import { useState, useEffect } from 'react'
import axios from 'axios'
import ChacollectionModals from './ChacollectionModal'
import Profile from '@/assets/@common/Profile.png'
import './ProfileInfo.css'

interface Icon {
	iconId: string
	iconUrl: string
	iconGrade: string
	isDuplicat: boolean
	isAvailable: boolean
}
interface ChacollectionModalProps {
	onAction: () => void
}

const ChacollectionModal: React.FC<ChacollectionModalProps> = ({ onAction }) => {
	return (
		<div>
			<button onClick={onAction}>Refresh Icons</button>
		</div>
	)
}
console.log(ChacollectionModal)

const ChacollectionProfile: React.FC = () => {
	const [icons, setIcons] = useState<Icon[]>([])
	const [profileImageUrl, setProfileImageUrl] = useState<string>(Profile)
	const [selectedIconId, setSelectedIconId] = useState<string | null>(null)
	console.log(selectedIconId)

	useEffect(() => {
		const savedIconUrl = localStorage.getItem('selectedProfileImage')
		if (savedIconUrl) {
			setProfileImageUrl(savedIconUrl)
		}
		fetchIcons()
	}, [])

	const fetchIcons = async () => {
		try {
			const response = await axios.get(`https://k10d203.p.ssafy.io/api/collects/icons`, {
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
				},
			})
			if (response.data && response.data.data) {
				setIcons(response.data.data.data)
			} else {
				console.error('예상된 데이터 형식이 아닙니다:', response.data)
				setIcons([])
			}
		} catch (error) {
			console.error('아이콘을 가져오는 데 에러가 발생했습니다:', error)
			setIcons([])
		}
	}

	const changeIcon = async (iconId: string, iconUrl: string) => {
		try {
			const response = await axios.patch('https://k10d203.p.ssafy.io/api/member/icon', null, {
				params: { iconId },
				headers: {
					Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
				},
			})

			if (response.data) {
				setProfileImageUrl(iconUrl) // 서버 응답 성공 시, 프로필 이미지 URL 업데이트
				setSelectedIconId(iconId)
				localStorage.setItem('selectedProfileImage', iconUrl) // 선택된 이미지를 localStorage에 저장
				alert('프로필 아이콘이 성공적으로 변경되었습니다.')
			}
		} catch (error) {
			console.error('프로필 아이콘 변경 중 오류 발생:', error)
			alert('프로필 아이콘 변경에 실패하였습니다.')
		}
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
				<div className="flex justify-center flex-wrap pt-4 scrollable-container">
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
