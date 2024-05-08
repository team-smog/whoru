import { useState, useEffect } from 'react'
import axios from 'axios'
import OpenImage from '@/assets/@common/Randomopenbox.png'
import Cancel from '@/assets/@common/Cancel.png'
import './Modal.css'


interface Icon {
	id: string
	iconUrl: string
	iconGrade: 'COMMON' | 'RARE' | 'ADVANCED'
	isDuplicat: boolean
}

const ProfileSettingsModal = () => {
	const [isModalOpen, setIsModalOpen] = useState(false)
	const [imageSrc, setImageSrc] = useState(OpenImage)
	const [drawnImages, setDrawnImages] = useState<Icon[]>([])
	const [remainingChances, setRemainingChances] = useState(3)

	const handleNotificationSettingsClick = () => {
		setIsModalOpen(true)
	}

	const closeModal = () => {
		setIsModalOpen(false)
	}
  useEffect(()=>{
    console.log(localStorage.getItem('AccessToken'))
  },[])

	const fetchUserIcons = async () => {
    try {
      console.log("Requesting random icon...");
        const randomIconResponse = await axios.post(
            'https://k10d203.p.ssafy.io/api/collects/icons/redeem-random',
            {},
            {
                headers: {
                    Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoxLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTcxNDcxMDA5MSwiZXhwIjoxNzUwNzEwMDkxfQ.YrzEzS-Bc1lYHTuUygb6nom3ZaMN_DWZcR2M46LSGDY'
                }
            }
        );
        console.log(randomIconResponse.data);
  
      console.log('Random Icon Status:', randomIconResponse.data.status);
      console.log('Random Icon Message:', randomIconResponse.data.msg);
  
      if (randomIconResponse.data) {
        const allIconsResponse = await axios.get('https://k10d203.p.ssafy.io/api/collects/icons', {
          headers:{
            'Content-Type':'application/json',
            'Authorization':'Bearer ' + localStorage.getItem('AccessToken'),
          },
        });
  
        console.log('All Icons Status:', allIconsResponse.data.status);
        console.log('All Icons Message:', allIconsResponse.data.msg);
  
        if (allIconsResponse.data && allIconsResponse.data.data) {
          setDrawnImages([...allIconsResponse.data.data, randomIconResponse.data])
        } else {
          console.error('No icons data received:', allIconsResponse.data)
        }
      }
    } catch (error) {
      console.error('Error fetching icons:', error)
    }
  }
  

	const getRandomImage = () => {
		if (drawnImages.length > 0) {
			const randomIndex = Math.floor(Math.random() * drawnImages.length)
			return drawnImages[randomIndex].iconUrl
		}
		return OpenImage
	}

	const handleImageClick = () => {
		if (remainingChances > 0) {
			setRemainingChances((prevChances) => prevChances - 1)
			fetchUserIcons()
      const randomImage = getRandomImage()
			setImageSrc(randomImage)
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

export default ProfileSettingsModal
