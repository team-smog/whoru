import { useState, useEffect } from "react";
import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";
import InboxTextComponent from "@/components/mainPage/InboxTextComponent";
import InboxImageComponent from "@/components/mainPage/InboxImageComponent";
import InboxVoiceComponent from "@/components/mainPage/InboxVoiceComponent";
import styles from "./MainPage.module.css";
import { MessageInfoDetail } from "@/types/mainTypes";
import PullToRefresh from 'react-pull-to-refresh';
import { useInfiniteQuery } from "@tanstack/react-query";
import { useInView } from 'react-intersection-observer';
// import { next } from "million/compiler";
import { useDispatch, useSelector } from "react-redux";
import { setFirstId, setLastId } from "@/stores/storeMessageId";
import { requestPermission } from "@/FirebaseUtil";

//todo: 
const MainPage = () => {
  const info: IHeaderInfo = {
    left_1: "Main",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }


  interface ResponseData {
    data: {
      content: MessageInfoDetail[];
      hasNext: boolean;
    }
  }


  const accessToken = localStorage.getItem('AccessToken');

  const [firstId, setFirstId] = useState<number>(0);
  const [lastId, setLastId] = useState<number>(0);
  const [hasNext, setHasNext] = useState<boolean>(true);
  const [serverState, setServerState] = useState<any[]>([]);

  const fetchOld = async ():Promise<ResponseData> => {
    const response = await fetch(`https://k10d203.p.ssafy.io/api/message?size=20`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${accessToken}`
    }});
     
    return response.json();
  }

  const fetchRecent = async () => {
    const response = await fetch(`https://k10d203.p.ssafy.io/api/message?size=20&firstId=${firstId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${accessToken}`
    }});
    const data = await response.json();
    return data;
  }

  useEffect(() => {
    const fetchData = async () => {
      const { data } = await fetchOld();
      const { content, hasNext } = data;
      setHasNext(hasNext);
      if (content.length > 0) {
        setFirstId(content[0].id);
        setLastId(content[content.length - 1].id);
        setServerState(content);
      }
    }
    fetchData();
  },[]);
  
  return (
      <div className={styles.mainPage}>
        <Header info={info} />
        <div>
          {serverState.map((message: MessageInfoDetail) => {
            if (message.contentType === 'text') {
              return <InboxTextComponent message={message} key={message.id} />
            } else if (message.contentType === 'image') {
              return <InboxImageComponent message={message} key={message.id} />
            } else if (message.contentType === 'voice') {
              return <InboxVoiceComponent message={message} key={message.id} />
            }
          })}
        </div>
        
        <NavigationBar />
    </div>
  );
};

export default MainPage;