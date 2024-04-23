import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";

const PostPage = () => {
  const info: IHeaderInfo = {
    left_1: "Send Message",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  return (
    <div>
      <Header info={info} />
      <div>
        보내기요
      </div>
      <NavigationBar />
    </div>
  )
};

export default PostPage;