import Header, { IHeaderInfo } from "@/components/@common/Header";
import InquiryPage from "@/components/Admin/InquiryPage";
import NotificationPage from "@/components/Admin/NotificationPage";
import ReportPage from "@/components/Admin/ReportPage";
import { useState } from "react";

const AdminPage = () => {
  const [selectedTab, setSelectedTab] = useState<string>('');

  const info: IHeaderInfo = {
    left_1: "Admin",
    left_2: null,
    center: null,
    right: null
  }

  const renderForm = () => {
    switch (selectedTab) {
      case 'Notification':
        return <NotificationPage />;
      case 'Inquiry':
        return <InquiryPage />;
      case 'Report':
        return <ReportPage />;
      default:
        return null;
    }

  }

  const handleUserTab = (type:string) => {
    if (selectedTab === type) {
      setSelectedTab('');
    } else {
      setSelectedTab(type);
    }
    console.log(selectedTab)
  }

  return (
    <div className="h-screen bg-white">
      <Header info={info} />
      <div className="pt-16">
        <div className="flex justify-between items-center">
          <div className={`w-1/3 text-center ${selectedTab === 'Notification' ? 'text-text_color border-black border-b-[0.5px]' : 'text-[#A5A5A5]'}`} onClick={() => handleUserTab('Notification')}>
            공지사항
          </div>
          <div className={`w-1/3 text-center ${selectedTab === 'Inquiry' ? 'text-text_color' : 'text-[#A5A5A5]'}`} onClick={() => handleUserTab('Inquiry')}>
            문의사항
          </div>
          <div className={`w-1/3 text-center ${selectedTab === 'Report' ? 'text-text_color' : 'text-[#A5A5A5]'}`} onClick={() => handleUserTab('Report')}>
            신고목록
          </div>
        </div>
        <div>
          {renderForm()}
        </div>
      </div>
    </div>
  )
}

export default AdminPage;
