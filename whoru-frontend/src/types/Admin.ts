export interface IInquiryRes {
  content: IInquiry[];
  currentPage: number;
  size: number;
  first:boolean;
  last:boolean;
  status:string;
  msg:string;
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