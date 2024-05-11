import { InquiryInfo } from "@/pages/Admin/AdminPage";
import InquiryItems from "./InquiryItems";

const Inquiry = ({ data }: { data: InquiryInfo[] }) => {
  return (
    <>
      <div className="w-full max-w-[500px]">
        {data && data.map((item, index) => {
          return <InquiryItems key={index} data={item} />
        })}
      </div>
    </>
  );
}

export default Inquiry;