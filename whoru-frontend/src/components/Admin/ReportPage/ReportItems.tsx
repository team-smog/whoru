import { IReportRes } from "@/types/Admin"
// import ReportModal from "./ReportModal"
// import { useState } from "react"

const ReportItems = ({ data } : { data: IReportRes }) => {
  // const [isReportModalOpen, setIsReportModalOpen] = useState<boolean>(false)

  // const handleOpenModal = () => {
	// 	//   // 신고내역 클릭 시 데이터 넘기기 위해
	// 	// setSelectedReport(report);
	// 	setIsReportModalOpen(true)
	// }

	return (
		<>
			<div className="px-4 mx-4 py-3 border-b-[0.5px] border-gray-200">
				<div className="text-text_color text-[14px]">[{data.reportType}] 신고사항</div>
				<div className="text-gray-400 text-[12px]">{data.reportDate}</div>
			</div>
      {/* {isReportModalOpen && (
					<ReportModal onClose={() => setIsReportModalOpen(false)} />
				)} */}
		</>
	)
}

export default ReportItems;
