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
import { useInfiniteQuery } from "@tanstack/react-query";
import { useInView } from 'react-intersection-observer';
import { next } from "million/compiler";


//todo: 

const MainPage = () => {
  const info: IHeaderInfo = {
    left_1: "Main",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  interface InboxData {
    content: MessageInfoDetail[];
  }

  interface ResponseData {
    data: InboxData;
    hasNext: boolean;
  }
  
  // const [refreshing, setRefreshing] = useState(false);

  // const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  // const [messageInfo, setMessageInfo] = useState<MessageInfoDetail[]>();
  // const messageInfoSize: number = 20;
  // const [lastId, setLastId] = useState<string | null>(null);
  // const [hasMore, setHasMore] = useState(true);

  

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
    // // setRefreshing(true);
    // return axios.get<ResponseData>(`https://k10d203.p.ssafy.io/api/message/resent`, {
    //   headers: {
    //     Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    //   },
    // })
    // .then((res) => {
    //   // setMessageInfo(prevMessageInfo => [...res.data.data.content, ...(prevMessageInfo || [])]);
    //   // setLastId(res.data.data.content[res.data.data.content.length - 1].id);
    //   // setHasMore(res.data.hasNext);
    // })
    // .catch((err) => {
    //   console.log(err);
    // })
    // .finally(() => {
    //   // setRefreshing(false);
    // });
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

  // useEffect(()=>{
  //   if(!targetRef.current){
  //       return;
  //   }
  //   const observer = new IntersectionObserver((entries)=>{
  //       console.log(entries[0].isIntersecting);
  //       setIntersecting(entries[0].isIntersecting);
  //   });
  //   observer.observe(targetRef.current);
  //   return ()=>{
  //       observer.disconnect();
  //   }
  // },[isSuccess])


  // useEffect(()=>{
  //     if(intersecting && hasMore ){
  //       fetchMoreData();
  //     }
  // },[intersecting, hasMore])

  const [ lastId, setLastId ] = useState<number | null>(null);
  const [ firstId, setFirstId ] = useState<number | null>(null);
  const [ messageList, setMessageList ] = useState<MessageInfoDetail[]>([]);

  const { ref, inView } = useInView();

  // useEffect(() => {
  //   axios.get<ResponseData>(`http://k10d203.p.ssafy.io:18080/api/message?size=${20}`, {
  //     headers: {
  //       Authorization: `BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g`,
  //     },
  //   })
  //   .then((res) => {
  //     console.log(res);
  //   })
  //   .catch((err) => {
  //     console.log(err);
  //   });
  // }, []);

  useEffect(() => {
    console.log("firstId", firstId);
  }, [firstId]);

  useEffect(() => {
    console.log("lastId", lastId);
  }, [lastId]);

  const handleRefresh = async () => {
    const res = await fetch(`http://k10d203.p.ssafy.io:18080/api/message/recent?firstid=${firstId}`, {
      headers: {
        Authorization: `BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g`,
      },
    })
    const data = await res.json();
    console.log(data);
    return data.data.content;
  }
  
  const fetchData = async ({ pageParam }: {pageParam: number}) => {
    if (!lastId) {
      const res = await fetch(`http://k10d203.p.ssafy.io:18080/api/message?size=${pageParam}`, {
        headers: {
          Authorization: `BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g`,
        }, 
      })
      const data = await res.json();
      console.log(data);
      setFirstId(data.data.content[0].id);
      // console.log("firstId", firstId);
      setLastId(data.data.content[data.data.content.length - 1].id);
      // console.log("lastId", lastId);
      return data.data.content;
    } else {
      const res = await fetch(`http://k10d203.p.ssafy.io:18080/api/message?lastid=${lastId}&size=${pageParam}`, {
        headers: {
          Authorization: `BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g`,
        }, 
      })
      const data = await res.json();
      console.log(data);
      setFirstId(data.data.content[0].id);
      setLastId(data.data.content[data.data.content.length - 1].id);
      // console.log("lastId", lastId);

      return data.data.content;
    }
  }

  const { data, status, error, fetchNextPage, isFetchingNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: ['message'],
    queryFn: fetchData,
    initialPageParam: 20,
    getNextPageParam: (lastPage, allPages) => {
      // console.log({lastPage, allPages});
      // return allPages.length + 1;
      // const nextPage = lastPage.length ? allPages.length : undefined;
      return 20;
      // return nextPage;
    }
  })

  const content = data?.pages.map((messageList: MessageInfoDetail[]) => messageList.map((message, index) => {
    // if (messageList.length === index) {
      if (message.contentType === "text") {
        return <InboxTextComponent innerRef={ref} key={message.id} message={message} />;
      } else if (message.contentType === "image") {
        return <InboxImageComponent innerRef={ref} key={message.id} message={message} />;
      } else if (message.contentType === "voice") {
        return <InboxVoiceComponent innerRef={ref} key={message.id} message={message} />;
      }
    // }
  }));

  useEffect(() => {
    if (inView && hasNextPage){
      // console.log('Fire!!!');
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage])

  if(status === 'pending') {
    return <p>Loading...</p>
  }

  if(status === 'error') {
    // return <p>Error: {error.message}</p>
  }

  return (
      <div className={styles.mainPage}>
        <Header info={info} />
        <div className={styles.mainPageBody}>
          <PullToRefresh onRefresh={handleRefresh}>
            <div className={styles.mainPageBodyContainer}>
              {content}
              {/* <button 
                ref={ref}
                onClick={() => fetchNextPage}
                disabled={!hasNextPage || isFetchingNextPage}
              >
                {isFetchingNextPage? 'Loading More ...' : hasNextPage ? 'Load More' : 'Nothing more to load'}
              </button> */}
              {/* {messageList.map((message: MessageInfoDetail) => {
                if (message.contentType === "text") {
                  return <InboxTextComponent key={message.id} message={message} />;
                } else if (message.contentType === "image") {
                  return <InboxImageComponent key={message.id} message={message} />;
                } else if (message.contentType === "voice") {
                  return <InboxVoiceComponent key={message.id} message={message} />;
                }
              })} */}
              {isFetchingNextPage && <div style={{textAlign:"center"}}>Loading</div>}
            </div>
            
        

          </PullToRefresh>
        </div>
        <NavigationBar />
    </div>
  );
};

export default MainPage;