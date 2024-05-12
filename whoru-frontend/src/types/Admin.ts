export interface IInquiry {
  content: IInquiryRes[];
  currentPage: number;
  size: number;
  first:boolean;
  last:boolean;
}

export interface IInquiryRes {
  id: number;
  subject: string;
  writerName: string;
  content: string;
  boardType: string;
  createDate: string;
  updateDate: string;
  comment: {
    id: number;
    commenterName:string;
    content: string;
    createDate: string;
    updateDate: string;
  };
}

export interface INotification {
  content: INotificationRes[];
  currentPage: number;
  size: number;
  first: boolean;
  last: boolean;
}

export interface INotificationRes {
  id: number;
  subject: string;
  writerName: string;
  content: string;
  boardType: string;
  createDate: string;
  updateDate: string;
  isCommented: boolean;
}

export interface IReport {
  content: IReportRes[];
  currentPage: number;
  size: number;
  first: boolean;
  last: boolean;
}

export interface IReportRes {
  id: number;
  messageId: number;
  senderId: number;
  memberId: number;
  isReviewed: boolean;
  reportDate : string;
  reportId: number;
  reportType: string;
}

export interface IReportDetail {
  id: number;
  senderId: number;
  receiverId: number;
  content: string;
  contentType: string;
  readStatus: boolean;
  isResponse: boolean;
  parentId: number;
  isReported: boolean;
  responseStatus: boolean;
  createDate: string;
  readDate: string;
}

export interface IReportUser {
  senderId: number|null;
}