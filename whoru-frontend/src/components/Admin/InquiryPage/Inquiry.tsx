import InquiryItems from './InquiryItems'
import { useState } from 'react'
import InquiryModal from './InquiryModal'
import { useInquiryDetail } from '@/hooks/Admin/useAdmin'
import { useIntersectionObserver } from '@/hooks/useIntersectionObserver'

const Inquiry = () => {
	const [boardId, setBoardId] = useState<number | null>(null)
	const [isInquiryModalOpen, setIsInquiryModalOpen] = useState<boolean>(false)
	const [inquiryId, setInquiryId] = useState<number | null>(null)
	const { data: inquiryData, fetchNextPage, hasNextPage } = useInquiryDetail()
  const { setTarget } = useIntersectionObserver({ fetchNextPage, hasNextPage });

	const handleOpenModal = () => {
		if (inquiryId !== null) {
			setBoardId(inquiryId)
			setIsInquiryModalOpen(true)
		}
	}

  const handleSelectItem = (id:number) => {
    setInquiryId(id);
  }

	return (
		<>
			<div className="w-full max-w-[500px]">
				<div className="h-[calc(100vh-180px)] overflow-y-auto">
					{inquiryData &&
						inquiryData.pages.map((page) =>
							page.content.map((item, index) => {
								return <InquiryItems key={index} data={item} onSelectItem={handleSelectItem}/>
							})
						)}
          <div ref={setTarget} className='h-[2rem]'></div>
				</div>
				{inquiryId && (
					<button
						className="fixed bottom-5 left-1/2 transform -translate-x-1/2 flex items-center justify-center w-[300px] h-8 bg-gray-200 text-[14px] rounded-[10px] text-text_color"
						onClick={handleOpenModal}
					>
						답글 작성하기
					</button>
				)}
				{isInquiryModalOpen && (
					<InquiryModal
						boardId={boardId}
						onClose={() => {
							setIsInquiryModalOpen(false)
							setInquiryId(null)
						}}
					/>
				)}
			</div>
		</>
	)
}

export default Inquiry