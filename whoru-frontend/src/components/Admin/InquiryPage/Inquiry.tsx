import { InquiryInfo } from "@/pages/Admin/AdminPage";
import { useState } from "react";
import InquiryModal from "./InquiryModal";

const Inquiry = ({ data }: { data: InquiryInfo[] }) => {
  // 데이터가 없어서 일단 넣어둠
  data = data || [];
  const [currentPage, setCurrentPage] = useState<number>(1);
  const itemsPerPage = 10;
  const totalPages = Math.ceil(data.length / itemsPerPage);
  const [openId, setOpenId] = useState<number | null>(null);
  const [isInquiryModalOpen, setIsInquiryModalOpen] = useState<boolean>(false);

  const currentData = data.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

  const handleClickPage = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  const handlePrevPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  const handleNextPage = () => {
    setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPages));
  };

  const isOpenAccordion = (id: number) => {
    if (openId === id) {
      setOpenId(null);
    } else {
      setOpenId(id);
    }
  };
  
  const handleOpenModal = () => {
    setIsInquiryModalOpen(true);
  };

  return (
    <>
      <div className="w-full max-w-[500px]">
        {currentData.map((inquiry) => (
          <div key={inquiry.id}>
            <div className="px-8 py-3" onClick={() => isOpenAccordion(inquiry.id)}>
              <div className="text-[14px] text-text_color">{inquiry.subject}</div>
              <div className="text-[12px] text-gray-400 pl-1">{inquiry.createDate}</div>
            </div>
            {openId === inquiry.id && (
              <div className="mx-8 py-3 text-text_color border rounded-[10px]">
                <div className="flex px-4">
                  <div className="text-text_color text-[12px]">작성자 </div>
                  <div className="flex items-end text-gray-400 text-[10px] pl-2"> {inquiry.writerName}</div>
                </div>
                <div className="border-b-[0.5px] my-2 mx-2"></div>
                <div className="text-text_color px-4 text-[12px]">{inquiry.content}</div>
                <div className="flex items-center justify-center h-8 mt-8 mx-4 bg-gray-200 text-[14px] text-text_color rounded-[10px]" onClick={handleOpenModal}>
                  <div>답글 작성하기</div>
                </div>
              </div>
            )}
          </div>
        ))}
      </div>
      <div className="flex justify-center mt-4">
        <button onClick={handlePrevPage} disabled={currentPage === 1}>이전</button>
        {Array.from({ length: totalPages }, (_, index) => (
          <button key={index + 1} onClick={() => handleClickPage(index + 1)}>{index + 1}</button>
        ))}
        <button onClick={handleNextPage} disabled={currentPage === totalPages}>다음</button>
      </div>
      {isInquiryModalOpen && <InquiryModal onClose={() => setIsInquiryModalOpen(false)} />}
    </>
  );
}

export default Inquiry;