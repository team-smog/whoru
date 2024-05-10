import Header, { IHeaderInfo } from '@/components/@common/Header'
import Bell from '@/assets/@common/Bell.png'
import NavigationBar from '@/components/@common/NavigationBar'
import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useLocation } from 'react-router-dom'
import { format } from 'date-fns'
import { ko } from 'date-fns/locale'
import Backspace from '@/assets/@common/Backspace.png'

interface Announcement {
	id: number
	subject: string
	writerName: string
	content: string
	boardType: string
	createDate: string
	updateDate: string
	isCommented: boolean
}

const AnnouncementDetail = () => {

	const info: IHeaderInfo = {
		left_1: null,
		left_2: <img src={Backspace} alt=""/>,
		center: '공지사항',
		right: <img src={Bell} alt="Alarm" />,
	}
	const [announcement, setAnnouncement] = useState<Announcement | null>(null)
	const [loading, setLoading] = useState<boolean>(true)
	const { id } = useParams<{ id: string }>()
	const location = useLocation()

	const formatDate = (dateString: string) => {
		const date = new Date(dateString)
		return format(date, 'yyyy.MM.dd', { locale: ko })
	}
	useEffect(() => {
		setAnnouncement(location.state)
		setLoading(false)
	}, [id])

	return (
		<div>
			<div className="pb-10">
				<Header info={info} />
			</div>
			<div className="flex flex-col justify-center items-center pt-10">
				<div className="w-full max-w-4xl mx-auto">
					{loading ? (
						<p>Loading...</p>
					) : announcement ? (
						<div>
							<div className=" border-b-[0.5px] mx-8">
								<div className="flex flex-col items-center">
									<div className="flex flex-col justify-evenly w-full px-4 pb-2">
										<p className="text-[16px]">{announcement.subject}</p>
										<p className="text-[12px] text-[#858585]">{formatDate(announcement.updateDate)}</p>
									</div>
								</div>
							</div>
							<div className="flex px-12 pt-4">
								<p className="text-xs break-keep">{announcement.content}</p>
							</div>
						</div>
					) : (
						<p>공지사항이 없습니다.</p>
					)}
				</div>
			</div>
			<div>
				<NavigationBar />
			</div>
		</div>
	)
}

export default AnnouncementDetail
