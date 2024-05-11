import { IInquiryRes } from '@/types/Admin'
import { useState } from 'react'
import InquiryModal from './InquiryModal'

const InquiryItems = ({ data }: { data: IInquiryRes }) => {
	const [openId, setOpenId] = useState<number | null>(null)
	const [boardId, setBoardId] = useState<number | null>(null)
	const [isInquiryModalOpen, setIsInquiryModalOpen] = useState<boolean>(false)

	const isOpenAccordion = (id: number) => {
		if (openId === id) {
			setOpenId(null)
		} else {
			setOpenId(id)
		}
	}

	const handleOpenModal = (id: number) => {
		setBoardId(id)
		setIsInquiryModalOpen(true)
	}

	return (
		<>
			<div
				className={`{ mx-4 px-4 py-3 ${openId === data.id ? null : 'mx-4 px-4 py-3 border-b-[0.5px] border-gray-200'}}`}
				onClick={() => isOpenAccordion(data.id)}
			>
				<div className="text-[14px] text-text_color">{data.subject}</div>
				<div className="text-[12px] text-gray-400 pl-1">{data.createDate}</div>
			</div>
			{openId === data.id && (
				<div className="mx-8 py-3 text-text_color border rounded-[10px] min-h-[250px] relative">
					<div className="flex px-4">
						<div className="text-text_color text-[12px]">작성자 </div>
						<div className="flex items-end text-gray-400 text-[10px] pl-2"> {data.writerName}</div>
					</div>
					<div className="border-b-[0.5px] my-2 mx-2"></div>
					<div className="text-text_color px-4 text-[12px]">{data.content}</div>
					<div className="absolute bottom-3 w-full flex justify-center max-w-[500px] m-auto px-4">
						<button
							className="justify-center w-[300px] flex items-center h-8 bg-gray-200 text-[14px] rounded-[10px] text-text_color"
							onClick={() => handleOpenModal(data.id)}
						>
							답글 작성하기
						</button>
					</div>
				</div>
			)}
			{isInquiryModalOpen && (
				<InquiryModal
					boardId={boardId}
					onClose={() => {
						setIsInquiryModalOpen(false)
					}}
				/>
			)}
		</>
	)
}

export default InquiryItems
