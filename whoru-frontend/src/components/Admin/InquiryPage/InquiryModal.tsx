import Modal from "@/components/@common/Modal";

const InquiryModal = ({ onClose }: { onClose: () => void }) => {
  return (
    <Modal width="300px" height="auto" title="답글" onClose={onClose}>
      <div className="flex flex-col px-4 pt-4 pb-6">
        <textarea
          placeholder="답글을 입력해주세요."
          className="border rounded-[10px] w-full px-2 py-2 min-h-[160px] text-text_color placeholder-[10px] text-[12px]"
        ></textarea>
        <div className="flex justify-center pt-4">
          <button className="w-full h-8 bg-gray-500 text-white py-1 rounded-lg text-[12px]" onClick={onClose}>
            답글 작성하기
          </button>
        </div>
      </div>
    </Modal>
  )
}

export default InquiryModal;