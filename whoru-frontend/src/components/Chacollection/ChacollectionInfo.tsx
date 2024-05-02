import Profile from '@/assets/@common/Profile.png'

const ChacollectionInfo = () => {
	return (
		<div className="flex justify-center">
			<div className="pt-20 w-[120PX] h-[120px]">
				<img src={Profile} alt="Profile" />
				<p className="flex justify-center text-xs pt-2">현재 프로필</p>
			</div>
		</div>
	)
}

export default ChacollectionInfo
