// import { useState } from "react";
// import axios from "axios";
import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";
import InboxTextComponent from "@/components/mainPage/InboxTextComponent";
import InboxImageComponent from "@/components/mainPage/InboxImageComponent";
import InboxVoiceComponent from "@/components/mainPage/InboxVoiceComponent";
import styles from "./MainPage.module.css";


const MainPage = () => {
  const info: IHeaderInfo = {
    left_1: "Main",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  // type MessageInfoDetail = {
  //   messageId: string;
  //   contentType: string;
  //   content: string;
  //   senderId: string;
  //   isResponse: boolean;
  //   isRead: boolean;
  //   time: string;
  //   type: string;
  // }

  // const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  // const messageInfoSize: number = 10;
  // const [lastId, setLastId] = useState<string> ("");

  // useEffect(() => {
  //   axios.get<MessageInfoDetail[]>(`https://k10d203.p.ssafy.io/message?lastid=${lastId}&size=${messageInfoSize}`, {
  //     headers: {
  //       Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
  //     },
  //   })
  //   .then((res) => {
  //     setMessageInfo(res.data);
  //   })
  //   .catch((err) => {
  //     console.log(err);
  //   });
  // }, []);
  
  return (
    <div className={styles.mainPage}>
      <Header info={info} />
      <div className={styles.mainPageBody}>
        <InboxTextComponent />
        <InboxImageComponent />
        <InboxVoiceComponent />
      </div>
      <NavigationBar />
    </div>
  )
};

export default MainPage;