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

export type ReportInfo = {
  id: number;
  report_type: string;
  report_date: number;
  member_id: number;
  message_id: number;
  content: string;
}


export type AdminData = {
  inquiry_info: InquiryInfo[];
  notification_info: NotificationInfo[];
  report_info: ReportInfo[];
}

const AdminPage = () => {
  const [selectedTab, setSelectedTab] = useState<string>('Notification');

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
        content: '궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁'
      },
      {
        id:2,
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
    report_info:[
      {
        id: 1,
        report_type: '비속어',
        report_date: 20240501,
        member_id: 10211423,
        message_id: 1000001,
        content: '궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁'
      },
      {
        id: 2,
        report_type: '음란',
        report_date: 20240502,
        member_id: 12222222,
        message_id: 1000002,
        content: '궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁'
      },
      {
        id: 3,
        report_type: '광고',
        report_date: 20240503,
        member_id: 13333333,
        message_id: 1000003,
        content: '궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁궁시렁'
      },
    ]
  }

  const renderForm = () => {
    switch (selectedTab) {
      case 'Notification':
        return <NotificationPage data={dummies.notification_info} />;
      case 'Inquiry':
        return <InquiryPage data={dummies.inquiry_info} />;
      case 'Report':
        return <ReportPage data={dummies.report_info}/>;
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
          <div className={`w-1/3 h-8 text-center ${selectedTab === 'Notification' ? 'text-text_color border-text_color border-b-[0.5px]' : 'text-[#A5A5A5]'}`} onClick={() => handleUserTab('Notification')}>
            공지사항
          </div>
          <div className={`w-1/3 h-8 text-center ${selectedTab === 'Inquiry' ? 'text-text_color border-text_color border-b-[0.5px]' : 'text-[#A5A5A5]'}`} onClick={() => handleUserTab('Inquiry')}>
            문의사항
          </div>
          <div className={`w-1/3 h-8 text-center ${selectedTab === 'Report' ? 'text-text_color border-text_color border-b-[0.5px]' : 'text-[#A5A5A5]'}`} onClick={() => handleUserTab('Report')}>
            신고목록
          </div>
        </div>
        <div className="py-4">
          {renderForm()}
        </div>
      </div>
    </div>
  )
}

export default AdminPage;
