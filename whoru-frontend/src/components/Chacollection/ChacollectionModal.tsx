import React, { useState, useEffect, useRef } from 'react'
import axios from 'axios'
import { useSelector, useDispatch } from 'react-redux'
import { setBoxCount } from '@/stores/store'
import OpenImage from '@/assets/@common/Randomopenbox.png'
import Cancel from '@/assets/@common/Cancel.png'
import './Modal.css'
import JSConfetti from 'js-confetti'

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
	const jsConfetti = useRef(new JSConfetti())
	const [showGrade, setShowGrade] = useState(false)
	const gradePlaceholder = showGrade ? selectedIcon?.iconGrade : ' '

	useEffect(() => {
		console.log('상자 개수:', boxCount)
	}, [boxCount])

	useEffect(() => {
		console.log('AccessToken:', localStorage.getItem('AccessToken'))
	}, [])

	const handleNotificationSettingsClick = () => {
		setIsModalOpen(true)
	}

	const closeModal = () => {
		setIsModalOpen(false)
		setImageSrc(OpenImage)
		setSelectedIcon(null)
	}

	const fetchUserIcons = async () => {
		setIsShaking(true)
		setTimeout(async () => {
			try {
				const response = await axios.post(
					'https://k10d203.p.ssafy.io/api/collects/icons/redeem-random',
					{},
					{
						headers: {
							Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
						},
					}
				)
				console.log(response)
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
					displayGrade()
				}
				setIsShaking(false)
			} catch (error) {
				console.error('Error fetching icons:', error)
				setIsShaking(false)
			}
		}, 800)
	}

	const displayGrade = () => {
		setShowGrade(true)
		setTimeout(() => {
			setShowGrade(false)
		}, 2000)
	}

	const applyAnimation = () => {
		setIsAnimating(true)
		setTimeout(() => {
			setIsAnimating(false)
		}, 500)
	}

	const handleImageClick = () => {
		if (boxCount > 0) {
			dispatch(setBoxCount(boxCount - 1))
			setImageSrc(OpenImage)
			fetchUserIcons()
			applyAnimation()
		} else {
			alert('상자 개수가 부족합니다.')
		}
	}

	return (
		<>
			<div className="button" onClick={handleNotificationSettingsClick}>
				캐릭터 뽑기
			</div>
			{isModalOpen && (
				<div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
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
							style={{ minHeight: '20px', visibility: showGrade ? 'visible' : 'hidden' }}
						>
							{gradePlaceholder}
						</div>
						<p className="flex justify-center pt-2 text-sm">남은 기회 : {boxCount > 0 ? boxCount : 0}회</p>
						<div
							className="modalbutton"
							onClick={handleImageClick}
							style={{ cursor: boxCount > 0 ? 'pointer' : 'not-allowed' }}
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
