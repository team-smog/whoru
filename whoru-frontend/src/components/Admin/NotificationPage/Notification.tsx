import { useState } from 'react'
import NotificationCreateModal from './NotificationCreateModal'
import { useIntersectionObserver } from '@/hooks/useIntersectionObserver'
import { useNotificationDetail } from '@/hooks/Admin/useAdmin'
import NotificationItem from './NotificationItems'

const Notification = () => {
	const [isNotificationCreateModalOpen, setIsNotificationCreateModalOpen] = useState<boolean>(false)

	const { data: notificationData, fetchNextPage, hasNextPage } = useNotificationDetail();
	const { setTarget } = useIntersectionObserver({ fetchNextPage, hasNextPage });

	const handleCreateModalOpen = () => {
		setIsNotificationCreateModalOpen(true);
	}

	return (
		<>
			<div className="w-full max-w-[500px]">
				<div className="h-[calc(100dvh-180px)] overflow-y-auto">
					{notificationData &&
						notificationData.pages.map((page) =>
							page.content.map((item, index) => {
								return <NotificationItem key={index} data={item} />
							})
						)}
					<div ref={setTarget} className="h-[2rem]"></div>
				</div>
				<div className="fixed bottom-5 w-full max-w-[500px] m-auto px-4" onClick={handleCreateModalOpen}>
					<button className="bg-gray-200 w-full rounded-[10px] text-text_color p-2 text-[14px]">공지사항 작성</button>
				</div>
			</div>
			{isNotificationCreateModalOpen && (
				<NotificationCreateModal onClose={() => setIsNotificationCreateModalOpen(false)} />
			)}
		</>
	)
}

export default Notification;
