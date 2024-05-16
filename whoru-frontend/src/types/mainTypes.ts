export type MessageInfoDetail = {
  content: string;
  contentType: string;
  createDate: string;
  id: number;
  isReported: boolean;
  isResponse: boolean;
  parent: Parent;
  readDate: string;
  readStatus: boolean;
  receiverId: number;
  responseStatus: boolean;
  senderId: number;
}

export type Parent = {
  content: string;
  contentType: string;
  createDate: string;
  id: number;
  isReported: boolean;
  isResponse: boolean;
  readDate: string;
  readStatus: boolean;
  receiverId: number;
  responseStatus: boolean;
  senderId: number;
}