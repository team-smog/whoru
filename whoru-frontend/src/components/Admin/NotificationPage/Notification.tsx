import { NotificationInfo } from "@/pages/Admin/AdminPage";
import { useState } from "react";
import NotificationModal from "@/components/Admin/NotificationPage/NotificationModal";
import NotificationCreateModal from "./NotificationCreateModal";

const Notification = ({data} : {data : NotificationInfo[]}) => {
  const [openId, setOpenId] = useState<number | null>(null);
  const [isNotificationModalOpen, setIsNotificationModalOpen] = useState<boolean>(false);
  const [isNotificationCreateModalOpen, setIsNotificationCreateModalOpen] = useState<boolean>(false);

  const isOpenAccordion = (id: number) => {
    if (openId === id) {
      setOpenId(null)
    } else {
      setOpenId(id);
    }
  }

  const handleModalOpen = () => {
    setIsNotificationModalOpen(true);
  }

  const handleCreateModalOpen = () => {
    setIsNotificationCreateModalOpen(true);
  }

  return (
    <>
      <div className="w-full max-w-[500px]">
        {data.map((item) => (
          <div key={item.id}>
            <div className="px-8 py-3" onClick={() => isOpenAccordion(item.id)}>
              <div className="text-[14px] text-text_color">{item.title}</div>
              <div className="text-[12px] text-gray-400 pl-1">{item.date}</div>
            </div>
            {openId === item.id && (
              <div className="mx-8 py-3 text-text_color border rounded-[10px] min-h-[250px] relative">
                <div className="flex items-center gap-3 px-4">
                  <div className="text-[14px]">공지</div>
                  <div>
                    <div className="text-[12px]">{item.title}</div>
                    <div className="text-[10px] text-gray-400">{item.date}</div>
                  </div>
                </div>
                <div className="border-b-[0.5px] mt-1 mx-2"/>
                <div className="px-4 pt-3 text-[12px]">{item.content}</div>
                <div className="absolute bottom-3 w-full flex justify-center max-w-[500px] m-auto px-4">
                  <button className="justify-center w-[300px] flex items-center h-8 bg-gray-200 text-[14px] rounded-[10px] text-text_color" onClick={handleModalOpen}>수정하기</button>
                </div>
              </div>
            )}
            <div className="fixed bottom-10 w-full max-w-[500px] m-auto px-4" onClick={handleCreateModalOpen}>
              <button className="bg-gray-200 w-full rounded-[10px] text-text_color p-2 text-[14px]">공지사항 작성</button>
            </div>
          </div>
        ))}
      </div>
      {isNotificationModalOpen && <NotificationModal onClose={() => setIsNotificationModalOpen(false)} />}
      {isNotificationCreateModalOpen && <NotificationCreateModal onClose={() => setIsNotificationCreateModalOpen(false)}/>}
    </>
  )
}

export default Notification;
