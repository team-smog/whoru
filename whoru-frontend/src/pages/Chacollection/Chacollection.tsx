import Header, { IHeaderInfo } from '@/components/@common/Header'
import Bell from '@/assets/@common/Bell.png'
import NavigationBar from '@/components/@common/NavigationBar'
import ChacollectionInfo from '../../components/Chacollection/ChacollectionInfo'
import ChacollectionProfile from '../../components/Chacollection/ChacollectionProfile'

const Chacollection = () => {
	const info: IHeaderInfo = {
		left_1: null,
		left_2: null,
		center: '캐릭터 도감',
		right: <img src={Bell} alt="Alarm" />,
	}
	return (
		<>
			<div>
				<Header info={info} />
			</div>
			<div>
				<ChacollectionInfo />
			</div>
			<div>
				<ChacollectionProfile />
			</div>
			<div>
				<NavigationBar />
			</div>
		</>
	)
}

export default Chacollection
