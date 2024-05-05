import { InquiryInfo } from "@/pages/Admin/AdminPage";

export interface APIResponse<T> {
  inquiry_info: InquiryInfo[];
  status: number;
  message: string;
  data: T;
}