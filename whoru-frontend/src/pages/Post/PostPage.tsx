import { useEffect, useState } from "react";
import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";
import styles from "./PostPage.module.css";
import SendTextComponent from "@/components/postPage/SendTextComponent";
import SendImageComponent from "@/components/postPage/SendImageComponent";
import SendVoiceComponent from "@/components/postPage/SendVoiceComponent";
import { useDispatch, useSelector } from "react-redux";
import { setReplyMessage } from "@/stores/store";

const PostPage = () => {
  const info: IHeaderInfo = {
    left_1: "Send",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  const [messageType, setMessageType] = useState("text");
  const dispatch = useDispatch();
  const messageId = useSelector((state: any) => state.reply.messageId);

  const isReply = () => {
    if (messageId) {
      console.log("답장하기")
      return <p className={styles.postPageTypeControllerReplyType}>답장하기</p>
    }
  }

  useEffect(() => {
    console.log("messageId", messageId)
    return () => {
      dispatch(setReplyMessage(null))
    }
  }, [messageId]);

  return (
    <div className={styles.postPage}>
      <Header info={info} />
      {isReply()}
      <div className={styles.postPageTypeController}>
        <button 
          className={messageType === "voice" ? styles.selectedButton : styles.postPageTypeControllerButton} 
          onClick={()=>{setMessageType("voice")}}
        >
          Voice
        </button>
        <button 
          className={messageType === "text" ? styles.selectedButton : styles.postPageTypeControllerButton} 
          onClick={()=>{setMessageType("text")}}
        >
          Text
        </button>
        <button 
          className={messageType === "image" ? styles.selectedButton : styles.postPageTypeControllerButton} 
          onClick={()=>{setMessageType("image")}}
        >
          Image
        </button>
      </div>
      <div className={styles.postPageComponentContainer}>
        {messageType === "text" && <SendTextComponent messageId = {messageId}/>}
        {messageType === "voice" && <SendVoiceComponent messageId = {messageId}/>}
        {messageType === "image" && <SendImageComponent messageId = {messageId}/>}
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