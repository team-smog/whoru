import Header, { IHeaderInfo } from "@/components/@common/Header";
import NavigationBar from "@/components/@common/NavigationBar";

const Inquiry = () => {
  const info: IHeaderInfo = {
    left_1: null,
    left_2: null,
    center: '문의사항',
    right: null
  }

  return (
    <div>
      <Header info={info} />
        
      <NavigationBar />
    </div>
  )
};

export default Inquiry;