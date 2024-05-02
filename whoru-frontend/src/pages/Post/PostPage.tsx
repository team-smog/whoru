import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";
import styles from "./PostPage.module.css";
import SendTextComponent from "@/components/postPage/SendTextComponent";
import SendImageComponent from "@/components/postPage/SendImageComponent";
import SendVoiceComponent from "@/components/postPage/SendVoiceComponent";


const PostPage = () => {
  const info: IHeaderInfo = {
    left_1: "Send",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  let settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
  };

  return (
    <div className={styles.postPage}>
      <Header info={info} />
        {/* <div className={styles.postPageBody}> */}
      {/* <Slider {...settings} > */}
        <div className={styles.postPageSliderContainer}>
          <SendTextComponent />
        </div>
        <div className={styles.postPageSliderContainer}>
          <SendVoiceComponent />
        </div>
        <div className={styles.postPageSliderContainer}>
          <SendImageComponent />
        </div>
      {/* </Slider> */}
        {/* </div> */}
      <NavigationBar />
    </div>
  )
};

export default PostPage;