import { InquiryInfo } from "@/pages/Admin/AdminPage";
import { useState } from "react";
import InquiryModal from "./InquiryModal";

const Inquiry = ({ data }: { data: InquiryInfo[] }) => {
  const [openId, setOpenId] = useState<number | null>(null);
  const [isInquiryModalOpen, setIsInquiryModalOpen] = useState<boolean>(false);

  const isOpenAccordion = (id: number) => {
    if (openId === id) {
      setOpenId(null);
    } else {
      setOpenId(id);
    }
  };
  
  const handleOpenModal = () => {
    setIsInquiryModalOpen(true)
  };

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
              <div className="mx-8 py-3 text-text_color border rounded-[10px]">
                <div className="flex px-4">
                  <div className="text-text_color text-[12px]">작성자 </div>
                  <div className="flex items-end text-gray-400 text-[10px] pl-2"> {item.name}</div>
                </div>
                <div className="border-b-[0.5px] my-2 mx-2"></div>
                <div className="text-text_color px-4 text-[12px]">{item.content}</div>
                <div className="flex items-center justify-center h-8 mt-8 mx-4 bg-gray-200 text-[14px] text-text_color rounded-[10px]" onClick={handleOpenModal}>
                  <div>답글 작성하기</div>
                </div>
              </div>
            )}
          </div>
        ))}
      </div>
      {isInquiryModalOpen && <InquiryModal onClose={() => setIsInquiryModalOpen(false)} />}
    </>
  );
}

export default Inquiry;
