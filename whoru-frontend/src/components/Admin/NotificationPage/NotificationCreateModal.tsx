import Modal from "@/components/@common/Modal";
import { useNotificationCreate } from "@/hooks/Admin/useAdmin";
import { useState } from "react";

const NotificationCreateModal = ({ onClose }: { onClose: () => void }) => {
  const [subject, setSubject] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const { mutate } = useNotificationCreate();

  console.log(subject)
  console.log(content)

  const handleNotificationCreate = () => {
    const formData = {
      subject,
      content
    };
    mutate(formData);
    onClose();
  };

  return (
    <Modal width="300px" height="auto" title="공지사항 작성" onClose={onClose}>
      <div className="flex flex-col px-4 pt-4 pb-4">
        <label className="py-1 text-[14px]">제목</label>
        <input
          type="text"
          value={subject}
          onChange={e => setSubject(e.target.value)}
          placeholder="제목을 입력해주세요"
          className="border rounded-[10px] p-2 text-[12px]"
        />
        <label className="py-1 text-[14px]">내용</label>
        <textarea
          value={content}
          onChange={e => setContent(e.target.value)}
          placeholder="답글을 입력해주세요."
          className="border rounded-[10px] w-full px-2 py-2 min-h-[160px] text-text_color placeholder-[10px] text-[12px]"
        />
        <div className="flex justify-center pt-4">
          <button className="w-full h-8 bg-gray-300 text-text_color py-1 rounded-lg text-[14px]" onClick={handleNotificationCreate}>
            작성하기
          </button>
        </div>
      </div>
    </Modal>
  )
};

export default NotificationCreateModal;