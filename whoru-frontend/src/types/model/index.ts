import { InquiryInfo, NotificationInfo } from "@/pages/Admin/AdminPage";

export interface APIResponse<T> {
  notification_info: NotificationInfo[];
  inquiry_info: InquiryInfo[];
  status: number;
  message: string;
  data: T;
}