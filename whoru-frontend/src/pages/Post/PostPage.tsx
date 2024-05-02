import { useState } from "react";
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

  const [messageType, setMessageType] = useState("text");

  return (
    <div className={styles.postPage}>
      <Header info={info} />
      <div className={styles.postPageTypeController}>
        <button className={styles.postPageTypeControllerButton} onClick={()=>{setMessageType("voice")}}>Voice</button>
        <button className={styles.postPageTypeControllerButton} onClick={()=>{setMessageType("text")}}>Text</button>
        <button className={styles.postPageTypeControllerButton} onClick={()=>{setMessageType("image")}}>Image</button>
      </div>
      <div className={styles.postPageComponentContainer}>
        {messageType === "text" && <SendTextComponent />}
        {messageType === "voice" && <SendVoiceComponent />}
        {messageType === "image" && <SendImageComponent />}
      </div>

      {/* <div className={styles.postPageSliderContainer}>
        <SendTextComponent />
      </div>
      <div className={styles.postPageSliderContainer}>
        <SendVoiceComponent />
      </div>
      <div className={styles.postPageSliderContainer}>
        <SendImageComponent />
      </div> */}
      <NavigationBar />
    </div>
  )
};

export default PostPage;