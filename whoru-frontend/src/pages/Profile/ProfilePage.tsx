import { useNavigate } from 'react-router-dom'
import Header from '@/components/@common/Header'
import ProfileInfo from '../../components/Profile/ProfileInfo'
import ProfileActions from '../../components/Profile/ProfileActions'
import ProfileSettingsModal from '../../components/Profile/ProfileSettingsModal'
import NavigationBar from '@/components/@common/NavigationBar'

const ProfilePage = () => {
	const navigate = useNavigate()

	const handleInquiryClick = () => {
		navigate('/Inquiry')
	}

	const info = {
		left_1: 'Profile',
		left_2: null,
		center: null,
		right: null,
	}

	return (
		<>
			<Header info={info} />
			<div>
				<ProfileInfo />
			</div>
      <div className='pt-10'>
        <hr className="border-1 border-black mt-10 px-10" />
        <ProfileActions />
        <hr className="border-1 border-black pt-5 px-10" />
      </div>
			<p className="pl-12 pt-4">언어 설정</p>
			<ProfileSettingsModal />
			<p className="pl-12 pt-4" onClick={handleInquiryClick}>
				문의하기
			</p>
			<NavigationBar />
		</>
	)
}

export default ProfilePage
