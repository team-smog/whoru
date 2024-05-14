import Header, { IHeaderInfo } from '@/components/@common/Header'
import InquiryPage from '@/components/Admin/InquiryPage/Inquiry'
import NotificationPage from '@/components/Admin/NotificationPage/Notification'
import ReportPage from '@/components/Admin/ReportPage/Report'

import { useEffect, useState } from 'react'

// export type InquiryInfo = {
// 	id: number
// 	subject: string
// 	writerName: string
// 	createDate: string
// 	updateDate: string
// 	content: string
// 	boardType: string
// 	isCommented: boolean
// }

// export type NotificationInfo = {
// 	id: number
// 	subject: string
// 	writerName: string
// 	content: string
// 	boardType: string
// 	createDate: string
// 	updateDate: string
// 	isCommented: boolean
// }

// export type ReportInfo = {
// 	id: number
// 	report_type: string
// 	report_date: number
// 	member_id: number
// 	message_id: number
// 	content: string
// }

// export type AdminData = {
// 	report_info: ReportInfo[]
// }

const AdminPage = () => {
	const [selectedTab, setSelectedTab] = useState<string>('Notification');

	useEffect(() => {
		console.log(selectedTab);
	}, [selectedTab]);

	const info: IHeaderInfo = {
		left_1: 'Admin',
		left_2: null,
		center: null,
		right: null,
	}

	const handleUserTab = (type: string) => {
		if (selectedTab === type) {
			setSelectedTab('');
		} else {
			setSelectedTab(type);
		}
	}

	return (
		<div className="h-screen bg-white">
			<Header info={info} />
			<div className="pt-16">
				<div className="flex justify-between items-center">
					<div
						className={`w-1/3 h-8 text-center ${selectedTab === 'Notification' ? 'text-text_color border-text_color border-b-[0.5px]' : 'text-[#A5A5A5]'}`}
						onClick={() => handleUserTab('Notification')}
					>
						공지사항
					</div>
					<div
						className={`w-1/3 h-8 text-center ${selectedTab === 'Inquiry' ? 'text-text_color border-text_color border-b-[0.5px]' : 'text-[#A5A5A5]'}`}
						onClick={() => handleUserTab('Inquiry')}
					>
						문의사항
					</div>
					<div
						className={`w-1/3 h-8 text-center ${selectedTab === 'Report' ? 'text-text_color border-text_color border-b-[0.5px]' : 'text-[#A5A5A5]'}`}
						onClick={() => handleUserTab('Report')}
					>
						신고목록
					</div>
				</div>
				<div className="py-4">
					{selectedTab === 'Notification' && <NotificationPage />}
					{selectedTab === 'Inquiry' && <InquiryPage />}
					{selectedTab === 'Report' && <ReportPage />}
				</div>
			</div>
		</div>
	)
}

export default AdminPage