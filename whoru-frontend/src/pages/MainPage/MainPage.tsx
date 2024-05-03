import { useState, useEffect,useRef } from "react";
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
import { QueryFunctionContext, useInfiniteQuery } from "@tanstack/react-query";


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
    }
    hasNext: boolean;
  }
  
  
  const [refreshing, setRefreshing] = useState(false);

  // const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  // const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  const messageInfoSize: number = 20;
  const [lastId, setLastId] = useState<string | null>(null);
  const [hasMore, setHasMore] = useState(true);

  	
  const targetRef = useRef<HTMLDivElement>(null)
  const [intersecting, setIntersecting] = useState(false);

  const fetchData = async function ({pageParm = 1}:QueryFunctionContext):Promise<ResponseData> {
    const res = await axios.post(`http://192.168.100.208:8080/api/message?lastid=${lastId}&size=${messageInfoSize}`,{
      headers: { 
       Authorization: 'BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g'
      }
    }
    )
    return res.data
  }

  const { data, isLoading, hasNextPage, isFetchingNextPage,isSuccess, fetchNextPage } = useInfiniteQuery(
    {
      queryKey: ['main'],
      queryFn: fetchData,
      getNextPageParam: (lastPage, allPages) => lastPage.nextPageId,
    }
  )

  // useEffect(() => {
  //   // axios.get<ResponseData>(`https://k10d203.p.ssafy.io/api/message?lastid=${lastId}&size=${messageInfoSize}`, {
  //   axios.get<ResponseData>(`http://192.168.100.208:8080/api/message?size=${messageInfoSize}`, {
  //     headers: {
  //       // Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
  //       Authorization: 'BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g',
  //     },
  //   })
  //   .then((res) => {
  //     console.log(res);
  //     setMessageInfo(res.data.data.content);
  //     setHasMore(res.data.hasNext);
  //     setLastId(res.data.data.content[res.data.data.content.length - 1].id);
  //   })
  //   .catch((err) => {
  //     console.log(err);
  //   });
  // }, []);

  // const handleRefresh = (): Promise<void> => {
  //   setRefreshing(true);
  //   return axios.get<ResponseData>(`https://k10d203.p.ssafy.io/api/message/resent`, {
  //     headers: {
  //       Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
  //     },
  //   })
  //   .then((res) => {
  //     setMessageInfo(prevMessageInfo => [...res.data.data.content, ...(prevMessageInfo || [])]);
  //     setLastId(res.data.data.content[res.data.data.content.length - 1].id);
  //     setHasMore(res.data.hasNext);
  //   })
  //   .catch((err) => {
  //     console.log(err);
  //   })
  //   .finally(() => {
  //     setRefreshing(false);
  //   });
  // };

  // const fetchMoreData = () => {
  //   console.log('fetchMoreData');
  //   // axios.get<ResponseData>(`https://k10d203.p.ssafy.io/api/message?lastid=${lastId}&size=${messageInfoSize}`, {
  //   axios.get<ResponseData>(`http://192.168.100.208:8080/api/message?lastid=${lastId}&size=${messageInfoSize}`, {
  //     headers: {
  //       // Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
  //       Authorization: 'BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g',
  //     },
  //   })
  //   .then((res) => {
  //     if (res.data.data.content.length > 0) {
  //       setMessageInfo(prevMessageInfo => [...(prevMessageInfo || []), ...res.data.data.content]);
  //       setLastId(res.data.data.content[res.data.data.content.length - 1].id);
  //       setHasMore(res.data.hasNext);
  //     } else {
  //       setHasMore(false);
  //     }
  //   })
  //   .catch((err) => {
  //     console.log(err);
  //   });
  // };

  useEffect(()=>{
    if(!targetRef.current){
        return;
    }
    const observer = new IntersectionObserver((entries)=>{
        console.log(entries[0].isIntersecting);
        setIntersecting(entries[0].isIntersecting);
    });
    observer.observe(targetRef.current);
    return ()=>{
        observer.disconnect();
    }
  },[isSuccess])


  useEffect(()=>{
      if(intersecting && hasMore ){
        fetchMoreData();
      }
  },[intersecting, hasMore])
  
  return (
      <div className={styles.mainPage}>
        <Header info={info} />
        <div className={styles.mainPageBody}>
          <PullToRefresh onRefresh={handleRefresh}>
            
        

          </PullToRefresh>
        </div>
        <NavigationBar />
    </div>
  );
};

export default MainPage;