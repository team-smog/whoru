import { useState, useEffect } from "react";
import axios from "axios";
import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";
import InboxTextComponent from "@/components/mainPage/InboxTextComponent";
import InboxImageComponent from "@/components/mainPage/InboxImageComponent";
import InboxVoiceComponent from "@/components/mainPage/InboxVoiceComponent";
import styles from "./MainPage.module.css";
// import { requestPermission } from '../../components/@common/FirebaseUtil'
// import { FCMComponent } from '../../FCM';


const MainPage = () => {
  const info: IHeaderInfo = {
    left_1: "Main",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  type MessageInfoDetail = {
    id: string;
    senderId: number;
    receiverId: number;
    content: string;
    contentType: string;
    readStatus: boolean;
    isResponse: boolean;
    parentId: number;
    isReported: boolean;
    responseStatus: boolean;
    // time: string;
    // type: string;
  }

  interface ResponseData {
    content: MessageInfoDetail[];
  }
  

  // const [token, setToken] = useState<string>("");
  // useEffect(() => {
  //   const resultToken = requestPermission();
  //   resultToken.then((token) => {
  //     setToken(token);
  //   });
  // }, []);

  const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  // const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  const messageInfoSize: number = 10;
  const [lastId] = useState<string | null> (null);
  // const [lastId, setLastId] = useState<string | null> (null);

  useEffect(() => {
    axios.get<ResponseData>(`https://k10d203.p.ssafy.io/message?lastid=${lastId}&size=${messageInfoSize}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    })
    .then((res) => {
      setMessageInfo(res.data.content);
    })
    .catch((err) => {
      console.log(err);
    });
  }, []);
  
  return (
    <div className={styles.mainPage}>
      <Header info={info} />
      <div className={styles.mainPageBody}>
        {
          messageInfo?.map((message, index) => {
            switch (message.contentType) {
              case 'text':
                return <InboxTextComponent key={index} message={message} />;
              case 'image':
                return <InboxImageComponent key={index} message={message} />;
              case 'voice':
                return <InboxVoiceComponent key={index} message={message} />;
              default:
                return null;
            }
          })
        }
      </div>
      <NavigationBar />
    </div>
  );
};

export default MainPage;