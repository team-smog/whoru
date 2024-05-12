import { IInquiryRes } from '@/types/Admin'
import { useState } from 'react'
import InquiryEditModal from './InquiryEditModal';

const InquiryItems = ({ data, onSelectItem }: { data: IInquiryRes, onSelectItem:(id:number) => void }) => {
	const [openId, setOpenId] = useState<number | null>(null);
	const [isInquiryEditModalOpen, setIsInquiryEditModalOpen] = useState<boolean>(false)

	const isOpenAccordion = (id: number) => {
		if (openId === id) {
			setOpenId(null)
		} else {
			setOpenId(id)
		}
		onSelectItem(id);
	}

	const handleOpenModal = () => {
		setIsInquiryEditModalOpen(true);
	}

	return (
		<>
			<div
				className={`{ mx-4 px-4 py-3 overflow-y-auto ${openId === data.id ? null : 'mx-4 px-4 py-3 border-gray-200'}}`}
				onClick={() => isOpenAccordion(data.id)}
			>
				<div className="flex items-center justify-between">
					<div className="flex">
						<div className="text-[14px] text-[#252525]">{data.subject}</div>
						<div className="flex items-end text-[10px] text-text_color pl-1">{data.writerName}</div>
					</div>
					<div className="text-[12px]">
						{data && data.comment === null ? (
							<div className="text-red-500">답변 대기중</div>
						) : (
							<div className="text-blue-500">답변 완료</div>
						)}
					</div>
				</div>
				<div className="text-[12px] text-text_color">{data.content}</div>
				<div className="text-[10px] text-gray-400 pt-2">{data.createDate}</div>
			</div>
			{data.comment && openId === data.id && (
				<div className="mx-8 py-3 text-text_color border rounded-[10px] min-h-[250px] relative">
					<div className="flex px-4">
						<div className="text-text_color text-[12px]">작성자 </div>
						<div className="flex items-end text-text_color text-[10px] pl-2"> {data.comment.commenterName}</div>
					</div>
          <div className='text-[10px] text-gray-400 pl-4'>{data.comment.updateDate}</div>
					<div className="border-b-[0.5px] my-2 mx-2"></div>
					<div className="text-text_color px-4 text-[12px]">{data.comment.content}</div>
					<div className="absolute bottom-3 w-full flex justify-center max-w-[500px] m-auto px-4" onClick={handleOpenModal}>
            <button className="justify-center w-full flex items-center h-8 bg-gray-200 text-[14px] rounded-[10px] text-text_color">
              수정하기
            </button>
          </div>
				</div>
			)}
			<div className="w-full max-w-[500px] border h-1 bg-gray-200 mt-4"></div>
			{isInquiryEditModalOpen && (<InquiryEditModal commentId={data.comment.id} onClose={() => setIsInquiryEditModalOpen(false)} />)}
		</>
	)
}

export default InquiryItems