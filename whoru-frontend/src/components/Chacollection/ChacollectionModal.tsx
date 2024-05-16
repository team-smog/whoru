import React, { useState, useRef } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { setBoxCount } from '@/stores/store'
import OpenImage from '@/assets/@common/Randomopenbox.png'
import Cancel from '@/assets/@common/Cancel.png'
import './Modal.css'
import JSConfetti from 'js-confetti'
import { axiosWithCredentialInstance } from '@/apis/axiosInstance'

interface Icon {
	id: string
	iconUrl: string
	iconGrade: 'COMMON' | 'RARE' | 'ADVANCED'
	isDuplicat: boolean
}

interface ChacollectionModalProps {
	onAction: () => void
}

const ChacollectionModal: React.FC<ChacollectionModalProps> = ({ onAction }) => {
	const dispatch = useDispatch()
	const boxCount = useSelector((state: any) => state.user.boxCount)
	const [isModalOpen, setIsModalOpen] = useState(false)
	const [imageSrc, setImageSrc] = useState(OpenImage)
	const [drawnImages] = useState<Icon[]>([])
	const [isAnimating, setIsAnimating] = useState(false)
	const [isShaking, setIsShaking] = useState(false)
	const [selectedIcon, setSelectedIcon] = useState<Icon | null>(null)
	const [isDisabled, setIsDisabled] = useState(false)
	const jsConfetti = useRef(new JSConfetti())

	const handleNotificationSettingsClick = () => {
		setIsModalOpen(true)
	}

	const closeModal = () => {
		setIsModalOpen(false)
		setImageSrc(OpenImage)
		setSelectedIcon(null)
	}

	const fetchUserIcons = async () => {
		setIsDisabled(true)
		setIsShaking(true)
		setTimeout(async () => {
			try {
				const response = await axiosWithCredentialInstance.post(
					'collects/icons/redeem-random',
					{},
					{
						headers: {
							Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
						},
					}
				)
				setSelectedIcon(response.data.data)
				onAction()
				setImageSrc(response.data.data.iconUrl)
				dispatch(setBoxCount(response.data.data.boxCount))
				if (response.data.data) {
					const confettiColors = {
						COMMON: ['#F0F8FF', '#F5F5F5', '#FAF0E6'],
						RARE: ['#FFD700', '#FFA500', '#FF6347'],
						ADVANCED: ['#8B008B', '#8A2BE2', '#9400D3'],
					}
					jsConfetti.current.addConfetti({
						confettiColors: confettiColors[response.data.data.iconGrade as keyof typeof confettiColors],
						confettiRadius: 6,
						confettiNumber: 300,
					})
				}
				setIsShaking(false)
			} catch (error) {
				setIsShaking(false)
			}
		}, 800)
	}

	const applyAnimation = () => {
		setIsAnimating(true)
		setTimeout(() => {
			setIsAnimating(false)
			setIsDisabled(false)
		}, 2500)
	}

	const handleImageClick = () => {
		if (boxCount > 0) {
			if (!isDisabled) {
				setImageSrc(OpenImage)
				setSelectedIcon(null)
				fetchUserIcons()
				applyAnimation()
			}
		}
	}

	return (
		<>
			<div className="button" onClick={handleNotificationSettingsClick}>
				캐릭터 뽑기
			</div>
			{isModalOpen && (
				<div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
					<div className="w-80 h-56 bg-white rounded-lg border-solid border-2 border-black">
						<div className="flex flex-row justify-between rounded-t-lg bg-[#D78DDD]">
							<h2 className="pt-1 p-2 modal-text">랜덤 박스</h2>
							<button className="pr-3 text-lg p-0" onClick={closeModal}>
								<img src={Cancel} alt="Cancel" />
							</button>
						</div>
						<hr className="border-1 border-black" />
						<div className="flex align-middle justify-center">
							<img
								className={`w-24 h-24 ${isShaking ? 'shake' : ''} ${isAnimating ? 'image-animation' : ''}`}
								src={imageSrc}
								alt="Image"
								onClick={handleImageClick}
							/>
						</div>
						<div
							className="flex justify-center text-black text-sm"
							style={{
								minHeight: '20px',
								color: selectedIcon
									? selectedIcon.iconGrade === 'RARE'
										? 'green'
										: selectedIcon.iconGrade === 'ADVANCED'
											? 'blue'
											: 'black'
									: 'black',
							}}
						>
							{selectedIcon
								? `${selectedIcon.iconGrade === 'COMMON' ? '흔함' : selectedIcon.iconGrade === 'RARE' ? '고급' : '희귀'}`
								: ' '}
						</div>

						<p className="flex justify-center pt-2 text-sm">남은 기회 : {boxCount > 0 ? boxCount : 0}회</p>
						<div
							className="modalbutton"
							onClick={handleImageClick}
							style={{
								backgroundColor: boxCount > 0 && !isDisabled ? '#F6B5D7' : '#dadbd9',
								color: boxCount > 0 && !isDisabled ? 'black' : '#727372',
							}}
						>
							캐릭터 뽑기
						</div>
						<div className="drawn-images-container">
							{drawnImages.map((image, index) => (
								<div key={index}>
									<img src={image.iconUrl} alt={`Drawn Image ${index}`} />
									<p>Grade: {image.iconGrade}</p>
									<p>Is Duplicate: {image.isDuplicat ? 'Yes' : 'No'}</p>
								</div>
							))}
						</div>
					</div>
				</div>
			)}
		</>
	)
}

export default ChacollectionModal
