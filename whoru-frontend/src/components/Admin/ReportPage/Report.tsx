import { ReportInfo } from '@/pages/Admin/AdminPage';
import { useState } from 'react';
import ReportModal from './ReportModal';

const categories = ['전체', '비속어', '음란', '스팸', '광고'];

const Report = ({ data }: { data: ReportInfo[] }) => {
  const [selectedCategory, setSelectedCategory] = useState<string>('전체');
  const [isReportModalOpen, setIsReportModalOpen] = useState<boolean>(false);
  const [selectedReport, setSelectedReport] = useState<ReportInfo | null>(null);

  // 신고내역 필터링
  const filteredData = data.filter(item => {
    if (selectedCategory === '전체') return true;
    return item.report_type === selectedCategory;
  });


  const handleReportClick = (report: ReportInfo) => {
      // 신고내역 클릭 시 데이터 넘기기 위해
    setSelectedReport(report);
    setIsReportModalOpen(true);
  }

  return (
    <>
      <div className="flex justify-around pb-2 text-text_color">
        {categories.map(category => (
          <button
            key={category}
            className={`${selectedCategory === category ? 'text-text_color border-b-[0.5px] border-text_color' : 'text-gray-400'}`}
            onClick={() => setSelectedCategory(category)}
          >
            {category}
          </button>
        ))}
      </div>
      <div>
        {filteredData.map((item) => (
          <div key={item.id} className="p-2 mx-4 border-b border-gray-200" onClick={() => handleReportClick(item)}>
            <p className='text-text_color text-[14px]'>[{item.report_type}] 신고사항 {item.message_id}</p>
            <p className='text-[12px] text-gray-400'>{item.report_date}</p>
          </div>
        ))}
      </div>
      {isReportModalOpen && selectedReport && <ReportModal report={selectedReport} onClose={() => setIsReportModalOpen(false)} />}
    </>
  );
};

export default Report;

