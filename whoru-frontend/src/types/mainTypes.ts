export type MessageInfoDetail = {
  id: string;
  senderId: number;
  receiverId: number;
  content: string;
  contentType: string;
  readStatus: boolean;
  isResponse: boolean;
  parentId: number;
  isReported: boolean;
  responseStatus: boolean;
}