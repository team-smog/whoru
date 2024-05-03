import Modal from "@/components/@common/Modal";
import { ReportInfo } from "@/pages/Admin/AdminPage";

const ReportModal = ({ report, onClose }: {report: ReportInfo, onClose: () => void }) => {
  return (
    <Modal width="300px" height="auto" title="신고 내역" onClose={onClose}>
      <div className="flex flex-col px-4 pt-4 pb-4 text-text_color">
        <div className="flex items-center gap-1">
          <div className="text-[14px]">[{report.member_id}]</div>
          <div className="flex text-red-500 text-[12px] items-end"> 2회</div>
        </div>
        <div className="flex border-b items-center gap-1">
          <div className="text-[12px]">신고 일시</div>
          <div className="text-gray-400 py-1 text-[12px] ">{report.report_date}</div>
        </div>
        <div className="text-[14px] pt-2">신고 사유</div>
        <div className="w-full max-w-[500px] px-2 py-2 mt-2 border-[0.5px] rounded-[10px]">{report.content}</div>
        <button className="w-full max-w-[500px] mt-5 py-1 bg-gray-200 rounded-[10px] border">무고벤</button>
      </div>
    </Modal>
  )
};

export default ReportModal;