import React, { useState, useEffect } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { format } from 'date-fns'
import { ko } from 'date-fns/locale'

interface Inquiry {
	boardType: string
	createDate: string
	id: number
	subject: string
	content: string
	isCommented: boolean
	writerName: string
	updateDate: string
}

interface InquiryListProps {
	inquiries: Inquiry[]
	handleDetailPage: (id: number) => void
}

const InquiryManager = () => {
	const [inquiries, setInquiries] = useState<Inquiry[]>([])
	const [currentPage, setCurrentPage] = useState(0)
	const [pageSize] = useState(9)
	const [loading, setLoading] = useState(true)
	const [isLastPage, setIsLastPage] = useState(false)
	const navigate = useNavigate()

	useEffect(() => {
		fetchInquiries()
	}, [currentPage, pageSize])

	const InquiryList: React.FC<InquiryListProps> = ({ inquiries }) => {
		return (
			<div>
				{inquiries.map((inquiry) => (
					<div key={inquiry.id}>
						<div className="pl-5 pr-5" onClick={() => goToDetailPage(inquiry)}>
							<p className="text-sm">{inquiry.subject}</p>
							<p className="pb-2 text-xs text-[#858585]">{formatDate(inquiry.updateDate)}</p>
							<hr className="pb-2" />
						</div>
					</div>
				))}
			</div>
		)
	}

	const fetchInquiries = async () => {
		setLoading(true)
		try {
			const response = await axios.get('https://k10d203.p.ssafy.io/api/board', {
				params: {
					page: currentPage,
					size: pageSize,
				},
				headers: {
					Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
				},
			})
			if (response.status === 200) {
				setInquiries(response.data.data.content)
				setIsLastPage(response.data.data.last)
				console.log('성공')
				console.log(response)
				console.log(response.data.data.content)
				setTimeout(() => {
					console.log(inquiries)
				}, 1000)
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

	const handleNextPage = () => {
		if (!isLastPage) {
			setCurrentPage((prev) => prev + 1)
		}
	}

	const handlePrevPage = () => {
		if (currentPage > 0) {
			setCurrentPage((prev) => prev - 1)
		}
	}

	const formatDate = (dateString: string) => {
		const date = new Date(dateString)
		return format(date, 'yyyy.MM.dd', { locale: ko })
	}

	const goToCreatePage = () => navigate(`/inquiry/create`)
	const goToDetailPage = (inquiry: Inquiry) => navigate(`/inquiry/detail`, { state: inquiry })

	return (
		<div className="pt-10">
			{loading}
			<div className="fixed bottom-32 w-full max-w-[500px] m-auto px-4">
				<button className="p-2 text-sm w-full bg-[#C4AFF1] h-10 rounded-lg" onClick={goToCreatePage}>
					문의사항 작성하기
				</button>
			</div>
			<InquiryList inquiries={inquiries} handleDetailPage={goToCreatePage} />
			<div className="flex justify-center">
				<div className="fixed bottom-20 w-full max-w-[500px] m-auto px-4">
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
		</div>
	)
}

export default InquiryManager
