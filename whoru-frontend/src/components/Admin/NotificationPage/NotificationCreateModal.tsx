import Modal from "@/components/@common/Modal";

const InquiryModal = ({ onClose }: { onClose: () => void }) => {
  return (
    <Modal width="300px" height="auto" title="공지사항 작성" onClose={onClose}>
      <div className="flex flex-col px-4 pt-4 pb-4">
        <label className="py-1 text-[14px]">제목</label>
        <input 
          placeholder="제목을 입력해주세요"
          className="border rounded-[10px] p-2 text-[12px]"
        />
        <label className="py-1 text-[14px]">내용</label>
        <textarea
          placeholder="답글을 입력해주세요."
          className="border rounded-[10px] w-full px-2 py-2 min-h-[160px] text-text_color placeholder-[10px] text-[12px]"
        />
        <div className="flex justify-center pt-4">
          <button className="w-full h-8 bg-gray-300 text-text_color py-1 rounded-lg text-[14px]" onClick={onClose}>
            수정하기
          </button>
        </div>
      </div>
    </Modal>
  )
};

export default InquiryModal;