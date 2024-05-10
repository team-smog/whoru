import Header, { IHeaderInfo } from "@/components/@common/Header";
import NavigationBar from "@/components/@common/NavigationBar";
import AnnouncementInfo from '../../components/Announcement/AnnouncementInfo';
import Backspace from '@/assets/@common/Backspace.png';


const Announcement = () => {

	const info: IHeaderInfo = {
    left_1: null,
		left_2: <img src={Backspace} alt="" />,
		center: '공지사항',
		right: null,
	}

	return (
		<div>
			<div className='pb-10'>
				<Header info={info} />
			</div>
			<div className='pt-10'>
				<AnnouncementInfo />
			</div>
			<div>
				<NavigationBar />
			</div>
		</div>
	)
}

export default Announcement
