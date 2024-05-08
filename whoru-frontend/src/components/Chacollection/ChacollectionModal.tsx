import { useState } from 'react'
import OpenImage from '@/assets/@common/Randomopenbox.png'
import Cancel from '@/assets/@common/Cancel.png'
import './Modal.css'

const ProfileSettingsModal = () => {
	const [isModalOpen, setIsModalOpen] = useState(false)
	const [remainingChances, setRemainingChances] = useState(3)
	const [imageSrc, setImageSrc] = useState(OpenImage)

	const handleNotificationSettingsClick = () => {
		setIsModalOpen(true)
	}

	const closeModal = () => {
		setIsModalOpen(false)
	}

	const getRandomImage = () => {
    const images = [OpenImage, Cancel];
    const randomIndex = Math.floor(Math.random() * images.length);
    return images[randomIndex];
};


	const handleImageClick = () => {
		if (remainingChances > 0) {
			setRemainingChances((prevChances) => prevChances - 1)
			const randomImage = getRandomImage()
			setImageSrc(randomImage)
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
						<div className="flex justify-center">
							<img src={imageSrc} alt="Image" onClick={handleImageClick} />
						</div>
						<p className="flex justify-center pt-2 text-sm">
							남은 기회 : {remainingChances > 0 ? remainingChances : 0}회
						</p>
						<div
							className="modalbutton"
							onClick={handleImageClick}
							style={{ cursor: remainingChances > 0 ? 'pointer' : 'not-allowed' }}
						>
							캐릭터 뽑기
						</div>
					</div>
				</div>
			)}
		</>
	)
}

export default ProfileSettingsModal
