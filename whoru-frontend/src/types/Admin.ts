export interface IInquiryRes {
  content: IInquiry[];
  currentPage: number;
  size: number;
  first:boolean;
  last:boolean;
}

export interface IInquiry {
  id: number;
  subject: string;
  writerName: string;
  content: string;
  boardType: string;
  createDate: string;
  updateDate: string;
  isCommented: boolean;
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