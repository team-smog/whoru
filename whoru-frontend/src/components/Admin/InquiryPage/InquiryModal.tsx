import Modal from "@/components/@common/Modal";
import { useInquiryCreate } from "@/hooks/Admin/useAdmin";
import { useState } from "react";

const InquiryModal = ({ boardId, onClose }: { boardId: number | null; onClose: () => void }) => {
  const [content, setContent] = useState<string>('');
  const { mutate } = useInquiryCreate();
  // useEffect(() => {
  //   console.log(content)
  // }, [content])

  const handleInquiryCreate = () => {
    if (boardId == null) {
      console.log('공지 음슴')
      return
    }
    const commenterId = 1
    mutate({ boardId, content, commenterId })
  }

  return (
    <Modal width="300px" height="auto" title="답글 작성하기" onClose={onClose}>
      <div className="flex flex-col px-4 pt-4 pb-6">
        <textarea
          placeholder="답글을 입력해주세요."
          value={content}
          onChange={e => setContent(e.target.value)}
          className="border rounded-[10px] w-full px-2 py-2 min-h-[160px] text-text_color placeholder-[10px] text-[12px]"
        />
        <div className="flex justify-center pt-4" onClick={handleInquiryCreate}>
          <button className="w-full h-8 bg-gray-300 text-text_color py-1 rounded-lg text-[14px]" onClick={onClose}>
            답글 작성하기
          </button>
        </div>
      </div>
    </Modal>
  )
};

export default InquiryModal;