import { InquiryInfo } from '@/pages/Admin/AdminPage';
import InquiryItems from './InquiryItems';
import { useState } from 'react';
import InquiryModal from './InquiryModal';

const Inquiry = ({ data }: { data: InquiryInfo[] }) => {
    const [boardId, setBoardId] = useState<number | null>(null);
    const [isInquiryModalOpen, setIsInquiryModalOpen] = useState<boolean>(false);
    const [activeInquiryId, setActiveInquiryId] = useState<number | null>(null);

    const handleOpenModal = () => {
        if (activeInquiryId !== null) {
            setBoardId(activeInquiryId);
            setIsInquiryModalOpen(true);
        }
    };

    const handleInquiryClick = (id: number) => {
        setActiveInquiryId(id);
    };

    return (
        <>
            <div className="w-full max-w-[500px]">
                <div className='h-[calc(100vh-180px)] overflow-y-auto'>
                    {data.map((item, index) => (
                        <div key={index} className="mb-4 cursor-pointer" onClick={() => handleInquiryClick(item.id)}>
                            <InquiryItems data={item} />
                        </div>
                    ))}
                </div>
                {activeInquiryId && (
                    <button
                        className="fixed bottom-5 left-1/2 transform -translate-x-1/2 flex items-center justify-center w-[300px] h-8 bg-gray-200 text-[14px] rounded-[10px] text-text_color"
                        onClick={handleOpenModal}
                    >
                        답글 작성하기
                    </button>
                )}
                {isInquiryModalOpen && (
                    <InquiryModal
                        boardId={boardId}
                        onClose={() => {
                            setIsInquiryModalOpen(false);
                            setActiveInquiryId(null);
                        }}
                    />
                )}
            </div>
        </>
    );
};

export default Inquiry;