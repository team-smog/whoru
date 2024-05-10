import { INotificationRes } from '@/types/Admin'
import { useState } from 'react'
import NotificationModal from './NotificationModal'

const NotificationItem = ({ data }: { data: INotificationRes }) => {
	const [openId, setOpenId] = useState<number | null>(null)
	const [boardId, setBoardId] = useState<number | null>(null)
	const [isNotificationModalOpen, setIsNotificationModalOpen] = useState<boolean>(false)

	const isOpenAccordion = (id: number) => {
		setOpenId((prev) => (prev === id ? null : id))
	}

	const handleModalOpen = (id: number) => {
		setBoardId(id)
		console.log(id)
		setIsNotificationModalOpen(true)
	}

	return (
		<>
			<div className="px-8 py-3" onClick={() => isOpenAccordion(data.id)}>
				<div className="text-[14px] text-text_color">{data.subject}</div>
				<div className="text-[12px] text-gray-400 pl-1">{data.createDate}</div>
			</div>
			{openId === data.id && (
				<div className="mx-8 py-3 text-text_color border rounded-[10px] min-h-[250px] relative">
					<div className="flex flex-col gap-1 px-4">
						<div className="text-[14px] text-center">공지</div>
						<div>
							<div className="text-[14px]">{data.subject}</div>
							<div className="text-[12px] text-gray-400">{data.createDate}</div>
						</div>
					</div>
					<div className="border-b-[0.5px] mt-1 mx-2" />
					<div className="px-4 pt-3 text-[12px]">{data.content}</div>
					<div className="absolute bottom-3 w-full flex justify-center max-w-[500px] m-auto px-4">
						<button
							className="justify-center w-[300px] flex items-center h-8 bg-gray-200 text-[14px] rounded-[10px] text-text_color"
							onClick={() => handleModalOpen(data.id)}
						>
							수정하기
						</button>
					</div>
				</div>
			)}

			{isNotificationModalOpen && (
				<NotificationModal
					boardId={boardId}
					onClose={() => {
						setIsNotificationModalOpen(false)
					}}
				/>
			)}
		</>
	)
}

export default NotificationItem
