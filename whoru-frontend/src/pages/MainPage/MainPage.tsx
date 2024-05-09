import { useState, useEffect, useRef } from "react";
import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";
import InboxTextComponent from "@/components/mainPage/InboxTextComponent";
import InboxImageComponent from "@/components/mainPage/InboxImageComponent";
import InboxVoiceComponent from "@/components/mainPage/InboxVoiceComponent";
import styles from "./MainPage.module.css";
import { MessageInfoDetail } from "@/types/mainTypes";
import { useInfiniteQuery } from "@tanstack/react-query";
import { useInView } from 'react-intersection-observer';
import { useDispatch, useSelector } from "react-redux";
import { setFirstId, setLastId } from "@/stores/storeMessageId";
import axios from "axios";
import { requestPermission } from "@/FirebaseUtil.js";


//todo: 

const MainPage = () => {
  const info: IHeaderInfo = {
    left_1: "Main",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  const dispatch = useDispatch();
  const firstId = useSelector((state: any) => state.message.firstId);
  const lastId = useSelector((state: any) => state.message.lastId);
  const [hasNext,setHasNext] = useState<boolean | null>(null);
  const [serverData, setServerData] = useState<any[]>([]);

  const touchStartY = useRef(0);
  const loadingHeight = useRef(0);
  const loading = useRef<HTMLDivElement | null>(null);
  const div = useRef<HTMLDivElement | null>(null);
  const MAX_HEIGHT = 50;

  const handleTouchStart = (e: React.TouchEvent) => {
    if (!div.current || !(div.current instanceof HTMLElement) || div.current.scrollTop !== 0) return;
    touchStartY.current = e.changedTouches[0].screenY;
    const el = document.createElement('div');
    el.classList.add('loading-element'); 
    div.current.prepend(el);
    loading.current = el;
  }

  const handleTouchMove = (e: React.TouchEvent) => {
    if (loading.current) {
      const screenY = e.changedTouches[0].screenY;
      const height = Math.floor((screenY - touchStartY.current) * 0.3);
      if (height >= 0) {
        loading.current.style.height = `${height}px`;
        loadingHeight.current = height;
      }
    }
  }

  const handleTouchEnd = () => {
    if (loading.current && loadingHeight.current >= MAX_HEIGHT) {
      addF();
      if (div.current) {
        div.current.removeChild(loading.current);
      }
      loading.current = null;
      loadingHeight.current = 0;
      touchStartY.current = 0;
    } else {
      if (loading.current) {
        if (div.current) {
          div.current.removeChild(loading.current);
        }
        loading.current = null;
        loadingHeight.current = 0;
        touchStartY.current = 0;
      }
    }
  }

  const [ tokenFCM, setTokenFCM] = useState<string|null>(null);
  const [ myTokenFCM, setMyTokenFCM] = useState<string|null>(null);

  const FCMSetToken = async () => {
    const token = await requestPermission();
    setMyTokenFCM(token);
  }

  const getTokenFCM = async () => {
    try {
      const FCMResponse = await fetch('https://k10d203.p.ssafy.io/api/member/gettoken', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
        },
      });
      const FCMData = await FCMResponse.json();
      // console.log("FCMData", FCMData);
      setTokenFCM(FCMData.data.token);
      // console.log("tokenFCM :", tokenFCM);
      console.log("getTokenFCM 확인");
    } catch (error: any) {
      console.error(error);
    }
  }

  const fetchDataFCM = async () => {
    try {
      await fetch(`https://k10d203.p.ssafy.io/api/member/updatefcm?fcmToken=${myTokenFCM}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
        },
      });
      console.log("fcm 토큰 저장 api 요청 완료")
    } catch (error: any) {
      console.error(error);
      console.log("fcm 토큰 저장 api 요청 실패")
    }
  };

  useEffect(() => {
    const confirmFCM = async () => {
      FCMSetToken();
      getTokenFCM();
      console.log("myTokenFCM ?", myTokenFCM);
      console.log("tokenFCM ?", tokenFCM);
      if ((myTokenFCM !== null) && (tokenFCM !== null) && (myTokenFCM !== tokenFCM)) {
        if (myTokenFCM !== tokenFCM) {
          fetchDataFCM();
        }
      }
    }

    confirmFCM();
  }, [tokenFCM, myTokenFCM]);

  useEffect(() => {
    console.log("firstId", firstId);
    console.log("lastId", lastId);
  }, [firstId, lastId]);

  const { ref, inView } = useInView();

  const handleRefresh = async (fId:number): Promise<void> => {
    console.log("fId", fId);
    const res = await fetch(`https://k10d203.p.ssafy.io/api/message/recent?firstid=${fId}`, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
      },
    })
    const data = await res.json();
    console.log(data);
    return data.data;
  }

  const fetchData = () => {
    if (lastId === null) {
      return axios.get(`https://k10d203.p.ssafy.io/api/message?size=${20}`, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
        },
      }) 
      .then((res) => {
        if (res.data && res.data.data && res.data.data.content) {
          dispatch(setFirstId(res.data.data.content[0].id));
          dispatch(setLastId(res.data.data.content[res.data.data.content.length - 1].id));
          setHasNext(res.data.data.hasNext);
          console.log(res.data);
          return res.data.data.content;
        }
      })
      .catch((err) => {
        console.log(err);
      })
    } else if (lastId !== null) {
      return axios.get(`https://k10d203.p.ssafy.io/api/message?size=${20}&lastid=${lastId}`, {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('AccessToken'),
        },
      }) 
      .then((res) => {
        if (res.data && res.data.data && res.data.data.content) {
          dispatch(setLastId(res.data.data.content[res.data.data.content.length - 1].id));
          setHasNext(res.data.data.hasNext);
          console.log(res.data);
          return res.data.data.content;
        }
      })
      .catch((err) => {
        console.log(err);
      })
    }
  }

  const { data, status, fetchNextPage, isFetchingNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: ['message'],
    queryFn: fetchData,
    initialPageParam: 0,
    getNextPageParam: (lastPage, allPages) => {
      return lastPage && lastPage.length ? allPages && allPages.length : undefined;
    },
    refetchOnMount: false,
    refetchOnWindowFocus: false,
  })

  useEffect(() => {
    if (data && data.pages) {
      setServerData(data.pages);
    }
  }, [data]);

  useEffect(() => {
    if (inView && hasNextPage && hasNext){
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage])

  if(status === 'pending') {
    return <p>Loading...</p>
  }

  if(status === 'error') {
    // return <p>Error: {error.message}</p>
  }

  const addF = async () => {
    const newMessages = await handleRefresh(firstId);
    console.log("newMessages", newMessages);
    setServerData([newMessages, ...serverData]);
  }

  return (
      <div className={styles.mainPage}>
        <Header info={info} />
        <div className={styles.mainPageBody}>
            <div 
              ref={div}
              className={styles.mainPageBodyContainer}
              onTouchStart={handleTouchStart}
              onTouchMove={handleTouchMove}
              onTouchEnd={handleTouchEnd}
            >
              {serverData && serverData?.map((messageList: MessageInfoDetail[]) => {
                return messageList?.map((message:MessageInfoDetail) => {
                  if (message.contentType === "text") {
                    return <InboxTextComponent innerRef={ref} key={message.id} message={message} />;
                  } else if (message.contentType === "image") {
                    return <InboxImageComponent innerRef={ref} key={message.id} message={message} />;
                  } else if (message.contentType === "voice") {
                    return <InboxVoiceComponent innerRef={ref} key={message.id} message={message} />;
                  }
                  return undefined;
                });
              })}
              {isFetchingNextPage && <div style={{textAlign:"center"}}>Loading</div>}
            </div>
        </div>
        <NavigationBar />
    </div>
  );
};

export default MainPage;
