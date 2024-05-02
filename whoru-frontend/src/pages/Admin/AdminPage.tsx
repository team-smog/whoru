import Header, { IHeaderInfo } from "@/components/@common/Header";
import InquiryPage from "@/components/Admin/InquiryPage/Inquiry";
import NotificationPage from "@/components/Admin/NotificationPage/Notification";
import ReportPage from "@/components/Admin/ReportPage/Report";
import { useState } from "react";

export type InquiryInfo = {
  id: number;
  name: string;
  title: string;
  date: string;
  content: string;
}

export type NotificationInfo = {
  id: number;
  title:string;
  date: string;
  content: string;
}


export type AdminData = {
  inquiry_info: InquiryInfo[];
  notification_info: NotificationInfo[];
}

const AdminPage = () => {
  const [selectedTab, setSelectedTab] = useState<string>('');

  const info: IHeaderInfo = {
    left_1: "Admin",
    left_2: null,
    center: null,
    right: null
  }

  const dummies : AdminData = {
    notification_info:[
      {
        id:1,
        title:'[공지] 점검사항',
        date: '2024.04.24',
        content: '궁시렁궁시렁'
      },
      {
        id:1,
        title:'[공지] 업데이트',
        date: '2024.04.25',
        content: '궁시렁궁시렁궁시렁궁시렁'
      },
    ],
    inquiry_info:[
      {
        id: 1,
        name: '정민호1',
        title:'[#1] 회원탈퇴가 안됩니다.',
        date: '2024.04.24',
        content: '뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여뭐시여'
      },
      {
        id: 2,
        name: '정민호2',
        title: '[#2] 민호가 고장났어요',
        date: '2024.04.25',
        content: '민호는 오늘 코를 고면서 잠을 자네?'
      }
    ],

  }

  const renderForm = () => {
    switch (selectedTab) {
      case 'Notification':
        return <NotificationPage />;
      case 'Inquiry':
        return <InquiryPage data={dummies.inquiry_info} />;
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
