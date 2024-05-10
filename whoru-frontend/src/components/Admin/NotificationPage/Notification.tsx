import { NotificationInfo } from "@/pages/Admin/AdminPage";
import { useEffect, useState } from "react";
import NotificationModal from "@/components/Admin/NotificationPage/NotificationModal";
import NotificationCreateModal from "./NotificationCreateModal";
import { useQueryClient } from "@tanstack/react-query";

const Notification = ({data} : {data : NotificationInfo[]}) => {
  const [currentPage, setCurrentPage] = useState<number>(1);
  const itemsPerPage = 10;
  const totalPages = Math.ceil(data.length / itemsPerPage);
  const [openId, setOpenId] = useState<number | null>(null);
  const [isNotificationModalOpen, setIsNotificationModalOpen] = useState<boolean>(false);
  const [isNotificationCreateModalOpen, setIsNotificationCreateModalOpen] = useState<boolean>(false);
  const [boardId, setBoardId] = useState<number | null>(null);
  const queryClient = useQueryClient();


  const currentData = data.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);
  console.log(data)

  useEffect(() => {
    refetchNotifications();
  }, [currentPage]);

  const handleClickPage = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  }

  const handlePrevPage = () => {
    setCurrentPage(prev => Math.max(1, prev - 1));
  }

  const handleNextPage = () => {
    setCurrentPage(prev => Math.min(totalPages, prev + 1));
  }

  const isOpenAccordion = (id: number) => {
    setOpenId(prev => prev === id ? null : id);
  }

  const refetchNotifications = () => {
    queryClient.invalidateQueries({queryKey: ['INotificationRes']})
  }

  const handleModalOpen = (id:number) => {
    setBoardId(id);
    console.log(id)
    setIsNotificationModalOpen(true);
  }

  const handleCreateModalOpen = () => {
    setIsNotificationCreateModalOpen(true);
  }

  return (
    <>
      <div className="w-full max-w-[500px]">
        <div className="pb-20" style={{ height: 'calc(100vh - 180px)', overflowY: 'auto'}}>
        {currentData.map((notification) => (
          <div key={notification.id}>
            <div className="px-8 py-3" onClick={() => isOpenAccordion(notification.id)}>
              <div className="text-[14px] text-text_color">{notification.subject}</div>
              <div className="text-[12px] text-gray-400 pl-1">{notification.createDate}</div>
            </div>
            {openId === notification.id && (
              <div className="mx-8 py-3 text-text_color border rounded-[10px] min-h-[250px] relative">
                <div className="flex flex-col gap-1 px-4">
                  <div className="text-[14px] text-center">공지</div>
                  <div>
                    <div className="text-[14px]">{notification.subject}</div>
                    <div className="text-[12px] text-gray-400">{notification.createDate}</div>
                  </div>
                </div>
                <div className="border-b-[0.5px] mt-1 mx-2"/>
                <div className="px-4 pt-3 text-[12px]">{notification.content}</div>
                <div className="absolute bottom-3 w-full flex justify-center max-w-[500px] m-auto px-4">
                  <button className="justify-center w-[300px] flex items-center h-8 bg-gray-200 text-[14px] rounded-[10px] text-text_color" onClick={() => handleModalOpen(notification.id)}>수정하기</button>
                </div>
              </div>
            )}
          </div>
        ))}
        </div>
      <div className="fixed bottom-14 w-full max-w-[500px] m-auto px-4" onClick={handleCreateModalOpen}>
        <button className="bg-gray-200 w-full rounded-[10px] text-text_color p-2 text-[14px]">공지사항 작성</button>
      </div>
      </div>
      <div className="fixed bottom-4 left-0 right-0 mx-auto flex justify-between px-32">
        <button onClick={handlePrevPage} disabled={currentPage === 1}>이전</button>
        {Array.from({ length: totalPages }, (_, index) => (
            <button key={index + 1} onClick={() => handleClickPage(index + 1)}>{index + 1}</button>
        ))}
        <button onClick={handleNextPage} disabled={currentPage === totalPages}>다음</button>
      </div>
      {isNotificationModalOpen && <NotificationModal boardId={boardId} onClose={() => {
          setIsNotificationModalOpen(false);
          refetchNotifications();
        }} />}
      {isNotificationCreateModalOpen && <NotificationCreateModal onClose={() => setIsNotificationCreateModalOpen(false)}/>}
    </>
  )
}

export default Notification;
