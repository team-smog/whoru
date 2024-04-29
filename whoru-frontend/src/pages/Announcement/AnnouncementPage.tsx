import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";

const Announcement = () => {
  const info: IHeaderInfo = {
    left_1: null,
    left_2: null,
    center: '공지사항',
    right: <img src={Bell} alt="Alarm"/>
  }

  return (
    <div>
      <Header info={info} />
      
      <NavigationBar />
    </div>
  )
};

export default Announcement;