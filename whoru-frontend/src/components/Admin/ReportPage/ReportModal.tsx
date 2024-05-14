import Modal from "@/components/@common/Modal";
import { useReportDetailItem, useReportUser } from "@/hooks/Admin/useAdmin";
import AudioPlayer from 'react-h5-audio-player';

const ReportModal = ({ messageId, reportId, onClose } : { messageId:number|null, reportId:number|null, onClose: () => void }) => {
  const { data: reportData } = useReportDetailItem(messageId);
  const { mutate } = useReportUser();

  const handleUserReport = () => {
    if (reportData?.senderId && reportId) {
      mutate({
        senderId: reportData.senderId,
        reportId: reportId
      });
    }
    onClose();
  }

  const renderform = () => {
    switch (reportData?.contentType) {
      case 'text':
        return (
          <div>
            <div>{reportData?.content}</div>
          </div>
        );
      case 'image':
        return (
          <div className="flex items-center justify-center">
            <img src={reportData?.content} alt="" />
          </div>
        );
      case 'voice':
        return (
          <div>
            <AudioPlayer
            src={reportData?.content}
            onPlay={() => {console.log("onPlay")}}
            layout="stacked-reverse"
            style={{ width: "100%", 
              height: "100%",
              background: "linear-gradient(90deg, #E08EDC 0%, #AFA4F4 100%)", 
              border: "3px solid #423752", 
              borderRadius: "10px"}}
          />
          </div>
        )
    }
  }

  return (
    <Modal width="300px" height="auto" title="신고 내역" onClose={onClose}>
      <div className="flex flex-col px-4 pt-4 pb-4 text-text_color">
        {renderform()}
        
        {/* <div className="text-[14px] pt-2">신고 사유</div>
        <div className="w-full max-w-[500px] px-2 py-2 mt-2 border-[0.5px] rounded-[10px]">{reportData?.content}</div> */}
        <button className="w-full max-w-[500px] mt-5 py-1 bg-gray-200 rounded-[10px] border" onClick={() => handleUserReport()}>무고벤</button>
      </div>
    </Modal>
  )
};

export default ReportModal;