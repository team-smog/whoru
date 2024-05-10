import { useState, useEffect } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { format } from 'date-fns'
import { ko } from 'date-fns/locale'

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

const AnnouncementInfo = () => {
	const [announcements, setAnnouncements] = useState<Announcement[]>([])
	const [currentPage, setCurrentPage] = useState<number>(0)
	const [size] = useState<number>(10)
	const [loading, setLoading] = useState<boolean>(true)
	const [isLastPage, setIsLastPage] = useState<boolean>(false)
	const navigate = useNavigate()

	const loadAnnouncements = async (page: number) => {
		setLoading(true)
		try {
			const response = await axios.get('https://k10d203.p.ssafy.io/api/board/noti', {
				params: {
					page: page,
					size: size,
				},
				headers: {
					Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
				},
			})
			console.log(response)
			if (response.status === 200 && response.data && response.data.data) {
				const { content, last } = response.data.data
				setAnnouncements(content)
				setIsLastPage(last)
			} else {
				console.error('유효한 데이터가 없습니다. 서버 응답:', response.data)
			}
		} catch (error) {
			if (axios.isAxiosError(error)) {
				console.error('API 응답 오류:', error.response?.data || error.message)
			} else {
				console.error('예상치 못한 오류:', error)
			}
		} finally {
			setLoading(false)
		}
	}

	useEffect(() => {
		loadAnnouncements(currentPage)
	}, [currentPage])

	const handleNextPage = () => {
		setCurrentPage((prev) => prev + 1)
	}

	const handlePrevPage = () => {
		setCurrentPage((prev) => prev - 1)
	}

	const goToDetailPage = (id: number, announcement: Announcement) => {
		navigate(`/announcement/${id}`, { state: announcement })
	}

	const formatDate = (dateString: string) => {
		const date = new Date(dateString)
		return format(date, 'yyyy.MM.dd', { locale: ko })
	}

	return (
		<div>
			{loading}
			{announcements.map((announcement) => (
				<div className="pl-5 pr-5" key={announcement.id} onClick={() => goToDetailPage(announcement.id, announcement)}>
					<p className="text-sm">{announcement.subject}</p>
					<p className="pb-2 text-xs text-[#858585]">{formatDate(announcement.updateDate)}</p>
					<hr className="pb-2" />
				</div>
			))}
			<div className="fixed bottom-20 w-full">
				<div className="flex justify-evenly items-center">
					<button onClick={handlePrevPage} disabled={currentPage === 0}>
						이전
					</button>
					<span>{currentPage + 1}</span>
					<button onClick={handleNextPage} disabled={isLastPage}>
						다음
					</button>
				</div>
			</div>
		</div>
	)
}

export default AnnouncementInfo
