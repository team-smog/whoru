import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";

const ProfilePage = () => {
  const info: IHeaderInfo = {
    left_1: "Profile",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  return (
    <>
      <Header info={info} />
      
      <NavigationBar />
    </>
  )
};

export default ProfilePage;