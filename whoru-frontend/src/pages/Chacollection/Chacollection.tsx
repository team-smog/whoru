import Header, { IHeaderInfo } from '@/components/@common/Header'
import NavigationBar from '@/components/@common/NavigationBar'
import ChacollectionProfile from '../../components/Chacollection/ChacollectionProfile'

const Chacollection = () => {
	const info: IHeaderInfo = {
		left_1: null,
		left_2: null,
		center: '캐릭터 도감',
		right: null,
	}
	return (
		<>
			<div>
				<Header info={info} />
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
