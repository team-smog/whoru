import { useState, FormEvent } from 'react'
import Swal from 'sweetalert2'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import Header, { IHeaderInfo } from '@/components/@common/Header'
import NavigationBar from '@/components/@common/NavigationBar'
import Backspace from '@/assets/@common/Backspace.png'
import './Inquriy.css'

const CreateInquiry = () => {
	const info: IHeaderInfo = {
		left_1: null,
		left_2: <img src={Backspace} alt="" />,
		center: '문의사항',
		right: null,
	}

	const [subject, setSubject] = useState<string>('')
	const [content, setContent] = useState<string>('')
	const [isSubmitting, setIsSubmitting] = useState<boolean>(false)
	const navigate = useNavigate()

	const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault()
		if (!subject.trim() || !content.trim()) {
			Swal.fire('필수 입력!', '제목과 내용을 모두 입력해주세요.', 'warning')
			return
		}

		Swal.fire({
			title: '문의사항을 등록하시겠습니까?',
			showDenyButton: true,
			confirmButtonText: '예',
			denyButtonText: '아니요',
			customClass: {
				popup: 'my-popup-style', // 팝업 전체 스타일
				title: 'my-title-style', // 제목 스타일
				confirmButton: 'my-confirm-button', // 확인 버튼 스타일
				denyButton: 'my-deny-button', // 거절 버튼 스타일
			},
		}).then(async (result) => {
			if (result.isConfirmed) {
				setIsSubmitting(true)
				try {
					const response = await axios.post(
						'https://k10d203.p.ssafy.io/api/board/inquiry',
						{ subject, content },
						{ headers: { Authorization: 'Bearer ' + localStorage.getItem('AccessToken') } }
					)
					if (response.status === 200) {
						Swal.fire('등록 성공!', '문의사항이 성공적으로 등록되었습니다.', 'success')
						navigate('/inquiry')
					} else {
						Swal.fire('등록 실패', response.data.msg || '문의사항 등록에 실패했습니다.', 'error')
					}
				} catch (error) {
					console.error('문의사항 등록 중 오류 발생:', error)
					Swal.fire('서버 오류', '문의사항 등록에 실패했습니다.', 'error')
				} finally {
					setIsSubmitting(false)
				}
			} else if (result.isDenied) {
				Swal.fire('취소됨', '문의사항이 등록되지 않았습니다.', 'info')
			}
		})
	}

	return (
		<div>
			<div className="pb-10">
				<Header info={info} />
			</div>
			<div className="pt-10 pr-5">
				<form onSubmit={handleSubmit}>
					<p className="text-sm pl-5">제목</p>
					<input
						type="text"
						value={subject}
						onChange={(e) => setSubject(e.target.value)}
						className="transparent-input text-xs pl-5"
						placeholder="제목을 입력해주세요.(최대 80자)"
						disabled={isSubmitting}
						maxLength={80}
					/>
					<hr className="ml-5" />
					<p className="text-sm pt-3 pl-5">내용</p>
					<div className="pl-5">
						<textarea
							className="text-xs mt-3 w-full textarea p-3 rounded-md border-solid border border-black"
							value={content}
							onChange={(e) => setContent(e.target.value)}
							placeholder="내용을 입력해주세요."
							disabled={isSubmitting}
						/>
					</div>
					<div className="fixed bottom-20 w-full max-w-[500px] m-auto px-4">
						<button className="p-2 text-sm w-full bg-[#C4AFF1] h-10 rounded-lg" type="submit" disabled={isSubmitting}>
							{isSubmitting ? '작성중...' : '문의사항 작성하기'}
						</button>
					</div>
				</form>
			</div>
			<div>
				<NavigationBar />
			</div>
		</div>
	)
}

export default CreateInquiry
