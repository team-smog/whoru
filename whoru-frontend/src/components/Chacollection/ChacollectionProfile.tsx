import { useState, useEffect } from 'react'
import axios from 'axios'
import ChacollectionModal from './ChacollectionModal'

interface Icon {
	iconId: number
	iconUrl: string
	iconGrade: string
}

const ChacollectionProfile = () => {
	const [icons, setIcons] = useState<Icon[]>([])

	useEffect(() => {
		console.log(localStorage.getItem('AccessToken'))
		const fetchIcons = async () => {
			try {
				const response = await axios.get(`https://k10d203.p.ssafy.io/api/collects/icons`, {
					headers: {
						'Content-Type': 'application/json',
						Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
					},
				})
				console.log(response)
				if (response.data && response.data.data) {
					setIcons([response.data.data])
				} else {
					console.error('예상된 데이터 형식이 아닙니다:', response.data)
					setIcons([])
				}
			} catch (error) {
				console.error('아이콘을 가져오는 데 에러가 발생했습니다:', error)
				setIcons([])
			}
		}

		fetchIcons()
	}, [])

	const changeIcon = async (iconId: number) => {
    try {
      const response = await axios.patch('https://k10d203.p.ssafy.io/api/member/icon', null, {
        params: {iconId},
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('AccessToken'),
        }
      });
  
      console.log('Profile icon changed successfully:', response.data);

      alert('프로필 아이콘이 성공적으로 변경되었습니다.');
    } catch (error) {
      console.error('프로필 아이콘 변경 중 오류 발생:', error);
      alert('프로필 아이콘 변경에 실패하였습니다.');
    }
  }
  

	return (
		<div className="relative chacollection-profile-container">
			<p className="text-xl pt-32 pl-4">캐릭터 도감</p>
			<p className="text-xs pt-2 pl-4 text-[#797979]">원하는 캐릭터로 자신의 프로필을 바꿀 수 있어요.</p>
			<div className="flex flex-wrap pt-4 scrollable-container">
				{icons.map((icon, index) => (
					<div className="profile-container" key={index} onClick={() => changeIcon(icon.iconId)}>
						<img src={icon.iconUrl} alt={`Profile Icon ${index}`} />
					</div>
				))}
			</div>

			<div className="fixed flex justify-center w-full max-w-[500px] bottom-20 pt-4 m-auto px-3">
				<ChacollectionModal />
			</div>
		</div>
	)
}

export default ChacollectionProfile
