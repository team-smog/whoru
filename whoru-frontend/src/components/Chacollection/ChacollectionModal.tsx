import React, { useState, useEffect } from 'react'
import axios from 'axios'
import Confetti from 'react-confetti'
import { useSelector, useDispatch } from 'react-redux'
import { setBoxCount } from '@/stores/store'
import OpenImage from '@/assets/@common/Randomopenbox.png'
import Cancel from '@/assets/@common/Cancel.png'
import './Modal.css'

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
	const [showConfetti, setShowConfetti] = useState(false)
	const [isShaking, setIsShaking] = useState(false)
  
  useEffect(() => {
    console.log("123",boxCount)
  },[])

	const handleNotificationSettingsClick = () => {
		setIsModalOpen(true)
	}

	const closeModal = () => {
		setIsModalOpen(false)
		setShowConfetti(false)
		setImageSrc(OpenImage)
	}

	useEffect(() => {
		console.log(localStorage.getItem('AccessToken'))
	}, [])

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
				onAction()
				setImageSrc(response.data.data.iconUrl)
				dispatch(setBoxCount(response.data.data.boxCount))
				setShowConfetti(true)
				setIsShaking(false)
				setTimeout(() => setShowConfetti(false), 5000)
			} catch (error) {
				console.error('Error fetching icons:', error)
				setIsShaking(false)
			}
		}, 800)
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
			alert('박스 개수가 부족합니다.')
		}
	}

	return (
		<>
			<div className="button" onClick={handleNotificationSettingsClick}>
				캐릭터 뽑기
			</div>
			{isModalOpen && (
				<div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
					<div className="w-80 h-52 bg-white rounded-lg border-solid border-2 border-black">
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
						{showConfetti && (
							<Confetti
								width={window.innerWidth}
								height={window.innerHeight}
								numberOfPieces={200}
								recycle={false}
								colors={['#ff5f6d', '#ffc371', '#48c6ef', '#6f86d6']}
							/>
						)}
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
