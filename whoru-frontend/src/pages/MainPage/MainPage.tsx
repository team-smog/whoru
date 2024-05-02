import { useState, useEffect } from "react";
import axios from "axios";
import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";
import InboxTextComponent from "@/components/mainPage/InboxTextComponent";
import InboxImageComponent from "@/components/mainPage/InboxImageComponent";
import InboxVoiceComponent from "@/components/mainPage/InboxVoiceComponent";
import styles from "./MainPage.module.css";
import { MessageInfoDetail } from "@/types/mainTypes";
import PullToRefresh from 'react-pull-to-refresh';
import InfiniteScroll from 'react-infinite-scroll-component';


const MainPage = () => {
  const info: IHeaderInfo = {
    left_1: "Main",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  interface ResponseData {
    content: MessageInfoDetail[];
  }
  
  
  const [refreshing, setRefreshing] = useState(false);

  const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  // const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  const messageInfoSize: number = 10;
  const [lastId, setLastId] = useState<string | null>(null);
  // const [lastId, setLastId] = useState<string | null> (null);
  const [hasMore, setHasMore] = useState(true);

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

  const handleRefresh = (): Promise<void> => {
    setRefreshing(true);
    return axios.get<ResponseData>(`https://k10d203.p.ssafy.io/message?lastid=${lastId}&size=${messageInfoSize}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    })
    .then((res) => {
      setMessageInfo(res.data.content);
    })
    .catch((err) => {
      console.log(err);
    })
    .finally(() => {
      setRefreshing(false);
    });
  };

  useEffect(() => {
    fetchMoreData();
  }, []);

  const fetchMoreData = () => {
    axios.get<ResponseData>(`https://k10d203.p.ssafy.io/message?lastid=${lastId}&size=${messageInfoSize}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
      },
    })
    .then((res) => {
      if (res.data.content.length > 0) {
        setMessageInfo(prevMessageInfo => [...(prevMessageInfo || []), ...res.data.content]);
        setLastId(res.data.content[res.data.content.length - 1].id);
      } else {
        setHasMore(false);
      }
    })
    .catch((err) => {
      console.log(err);
    });
  };
  
  return (
    <PullToRefresh onRefresh={handleRefresh}>
      <div className={styles.mainPage}>
        <Header info={info} />
        <InfiniteScroll
          dataLength={messageInfo?.length || 0}
          next={fetchMoreData}
          hasMore={hasMore}
          loader={<h4>Loading...</h4>}
          endMessage={
            <p style={{ textAlign: 'center' }}>
              <b>Yay! You have seen it all</b>
            </p>
        }
      >
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
      </InfiniteScroll>
        <NavigationBar />
      </div>
    </PullToRefresh>
  );
};

export default MainPage;