import { useState, useEffect } from 'react'
import axios from 'axios'
import Header, { IHeaderInfo } from '@/components/@common/Header'
import NavigationBar from '@/components/@common/NavigationBar'
import { useParams, useLocation, useNavigate } from 'react-router-dom'
import { format } from 'date-fns'
import { ko } from 'date-fns/locale'
import Backspace from '@/assets/@common/Backspace.png'
import Swal from 'sweetalert2'
import Q from '@/assets/@common/Q.png'
import A from '@/assets/@common/A.png'

interface Inquiry {
	id: number
	subject: string
	content: string
	writerName: string
	updateDate: string
	comment: {
		id: number
		createDate: string
		updateDate: string
		content: string
		commenterName: string
	} | null
}

function InquiryDetail() {
	const info: IHeaderInfo = {
		left_1: null,
		left_2: <img src={Backspace} alt="" />,
		center: '문의사항',
		right: null,
	}

	let { id } = useParams()
	const [inquiry, setInquiry] = useState<Inquiry | null>(null)
	const [loading, setLoading] = useState<boolean>(true)
	const location = useLocation()
	const navigate = useNavigate()

	useEffect(() => {
		setInquiry(location.state as Inquiry)
		setLoading(false)
	}, [id, location.state])

	const handleDelete = async (id: number) => {
		Swal.fire({
			title: '정말 삭제하시겠습니까?',
			text: '이 작업은 되돌릴 수 없습니다!',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#5959E7',
			cancelButtonColor: '#d33',
			confirmButtonText: '예, 삭제합니다',
			cancelButtonText: '아니오, 취소합니다',
		}).then((result) => {
			if (result.isConfirmed) {
				axios
					.delete(`https://k10d203.p.ssafy.io/api/board/${id}`, {
						headers: {
							Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
						},
					})
					.then((response) => {
						navigate('/inquiry')
						console.log(response)
					})
			}
		})
	}

	const formatDate = (dateString: string) => {
		const date = new Date(dateString)
		return format(date, 'yyyy.MM.dd', { locale: ko })
	}

	return (
		<div>
			<div className="pb-10">
				<Header info={info} />
			</div>
			<div className="flex flex-col justify-center items-center pt-10">
				{loading ? (
					<p>Loading...</p>
				) : inquiry ? (
					<div>
						<div className="border-b-[0.5px] mx-8">
							<div className="w-full">
								<div className="flex flex-col items-start">
									<div className="flex flex-row">
										<img className="w-8 h-8" src={Q} alt="" />
										<div className="flex flex-col justify-evenly w-full px-4 pb-2">
											<p className="text-[16px]">{inquiry.subject}</p>
											<p className="text-[14px] text-[#858585]">{formatDate(inquiry.updateDate)}</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div className="flex flex-row px-12 pt-4">
							<p className="text-xs word-wrap:break-word scrollable-content">{inquiry.content}</p>
						</div>
						{inquiry.comment && (
							<div className="w-full max-w-[500px] mx-auto fixed bottom-52 overflow-y-auto">
								<div className="border-b-[0.5px] mx-8">
									<div className="flex justify-center items-center">
										<div className="flex flex-row justify-start w-full pb-2">
											<div>
												<img className="w-8 h-8" src={A} alt="" />
											</div>
											<div className="flex flex-col justify-start px-4">
												<p className="text-[16px]">{inquiry.comment.commenterName}</p>
												<p className="text-[12px] text-[#858585]">{formatDate(inquiry.comment.createDate)}</p>
											</div>
										</div>
									</div>
								</div>
								<div
									className="w-full max-w-[500px] max-h-[300px] px-12 pt-4 min-h-20"
									style={{
										overflowWrap: 'break-word',
										overflow: 'auto',
										whiteSpace: 'normal',
									}}
								>
									<p className="text-xs">{inquiry.comment.content}</p>
								</div>
							</div>
						)}
						<div className="fixed bottom-20 w-full max-w-[500px] m-auto px-4">
							<button
								className="p-2 text-sm w-full bg-[#C4AFF1] h-10 rounded-lg"
								onClick={() => handleDelete(inquiry.id)}
							>
								삭제하기
							</button>
						</div>
					</div>
				) : (
					<p>No data found.</p>
				)}
			</div>
			<NavigationBar />
		</div>
	)
}

export default InquiryDetail
