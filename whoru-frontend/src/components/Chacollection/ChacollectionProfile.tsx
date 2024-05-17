import React, { useState, useEffect } from 'react'
import ChacollectionModals from './ChacollectionModal'
import './ProfileInfo.css'
import { useDispatch, useSelector } from 'react-redux'
import { setIconUrl } from '@/stores/store'
import Swal from 'sweetalert2'
import { axiosWithCredentialInstance } from '@/apis/axiosInstance'

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
			const res = await axiosWithCredentialInstance.get(`collects/icons`, {
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
			console.error(error)
			setIcons([])
			Swal.fire('아이콘을 불러오는 중 오류가 발생했습니다.', '', 'error')
		}
	}

	const changeIcon = async (iconId: string, iconUrl: string) => {
		try {
			const res = await axiosWithCredentialInstance.patch('member/icon', null, {
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
		} catch (error) {
			console.error(error)
			Swal.fire('프로필 아이콘을 변경하는 중 오류가 발생했습니다.', '', 'error')
		}
	}

	const gradeMapping: { [key: string]: string } = {
		COMMON: '흔함',
		RARE: '레어',
		ADVANCED: '희귀',
	}

	const groupIconsByGrade = (icons: Icon[]) => {
		return icons.reduce(
			(acc, icon) => {
				if (!acc[icon.iconGrade]) {
					acc[icon.iconGrade] = []
				}
				acc[icon.iconGrade].push(icon)
				return acc
			},
			{} as Record<string, Icon[]>
		)
	}

	const groupedIcons = groupIconsByGrade(icons)

	return (
		<div>
			<div className="flex justify-center">
				<div className="pt-20 w-[120px] h-[120px] flex flex-col items-center">
					<img src={profileImageUrl} alt="Profile" className='w-20'/>
					<p className="flex justify-center text-xs pt-2">현재 프로필</p>
				</div>
			</div>
				<p className="text-xl pt-32 pl-4">캐릭터 도감</p>
				<p className="text-xs pt-2 pl-4 text-[#797979]">원하는 캐릭터로 자신의 프로필을 바꿀 수 있어요.</p>
				<div className="w-full max-w-[500px] mx-auto overflow-y-auto px-4 pb-4 h-[calc(100dvh-420px)] flex flex-col items-center">
					{Object.keys(groupedIcons).map((grade, gradeIndex) => (
						<div key={gradeIndex} className="flex flex-col justify-center">
							<h2 className="text-lg pt-4 pl-4">{`등급: ${gradeMapping[grade] || grade}`}</h2>
							<div className="flex flex-wrap">
								{groupedIcons[grade].map((icon, index) => (
									<div
										className={`rounded-[10px] m-1 p-3 ${!icon.isAvailable ? 'unavailable-icon' : 'bg-[#f0f0f0]'}`}
										key={index}
										onClick={() => icon.isAvailable && changeIcon(icon.iconId, icon.iconUrl)}
									>
										<img src={icon.iconUrl} alt={`Profile Icon ${index}`} className='w-20 h-20'/>
									</div>
								))}
							</div>
						</div>
					))}
				</div>

				<div className="fixed z-50 flex justify-center w-full max-w-[500px] bottom-20 pt-4 m-auto px-3">
					<ChacollectionModals onAction={fetchIcons} />
				</div>
		</div>
	)
}

export default ChacollectionProfile
