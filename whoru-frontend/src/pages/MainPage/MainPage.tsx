import { useState, useEffect } from "react";
// import axios from "axios";
import Header, { IHeaderInfo } from "@/components/@common/Header";
import Bell from "@/assets/@common/Bell.png"
import NavigationBar from "@/components/@common/NavigationBar";
import InboxTextComponent from "@/components/mainPage/InboxTextComponent";
import InboxImageComponent from "@/components/mainPage/InboxImageComponent";
import InboxVoiceComponent from "@/components/mainPage/InboxVoiceComponent";
import styles from "./MainPage.module.css";
import { MessageInfoDetail } from "@/types/mainTypes";
import PullToRefresh from 'react-pull-to-refresh';
import { useInfiniteQuery, useQueryClient, useMutation } from "@tanstack/react-query";
import { useInView } from 'react-intersection-observer';
// import { next } from "million/compiler";
import { useDispatch, useSelector } from "react-redux";
import { setFirstId, setLastId } from "@/stores/storeMessageId";


//todo: 

const MainPage = () => {
  const info: IHeaderInfo = {
    left_1: "Main",
    left_2: null,
    center: null,
    right: <img src={Bell} alt="Alarm"/>
  }

  // interface InboxData {
  //   content: MessageInfoDetail[];
  // }

  // interface ResponseData {
  //   data: InboxData;
  //   hasNext: boolean;
  // }

  // const messageId = useSelector((state: any) => state.reply.messageId);

  const dispatch = useDispatch();
  const firstId = useSelector((state: any) => state.message.firstId);
  const lastId = useSelector((state: any) => state.message.lastId);
  const [hasNext,setHasNext] = useState<boolean | null>(null);

  // const [ lastId, setLastId ] = useState<number | null>(null);
  // const [ firstId, setFirstId ] = useState<number | null>(null);
  

  const { ref, inView } = useInView();

  // useEffect(() => {
  //   console.log("messageId", messageId);
  // }, [messageId]);

  // useEffect(() => {
  //   console.log("firstId", firstId);
  //   console.log("lastId", lastId);
  // }, [firstId, lastId]);

  const queryClient = useQueryClient();

  const handleRefresh = async (): Promise<void> => {
    console.log("firstId", firstId);
    const res = await fetch(`http://k10d203.p.ssafy.io:18080/api/message/recent?firstid=${firstId}`, {
      headers: {
        // 'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
        Authorization: `BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g`,
      },
    })
    const data = await res.json();
    console.log(data);
    return data.data.content;
  }

  // const Refresh = async ():Promise<void> => {
  //   queryClient.setQueryData(['message'], {
  //     pages: [],
  //     pageParams: [],
  //   });
  //   await queryClient.invalidateQueries('message');
  // }
  
  const fetchData = async ({ pageParam }: {pageParam: number}) => {

    if (lastId === null) {
      console.log("firstId", firstId);
      console.log("lastId", lastId);
      const res = await fetch(`http://k10d203.p.ssafy.io:18080/api/message?size=${pageParam}`, {
        headers: {
          // 'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
          Authorization: `BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g`,
        },
      })
      const data = await res.json();
      console.log(data);
      console.log(data.data.hasNext);
      setHasNext(data.data.hasNext);
      // console.log(data.data.content[0].id);
      // console.log(data.data.content[data.data.content.length - 1].id);
      // setFirstId(data.data.content[0].id);
      dispatch(setFirstId(data.data.content[0].id));
      console.log("firstId", firstId);
      // setLastId(data.data.content[data.data.content.length - 1].id);
      dispatch(setLastId(data.data.content[data.data.content.length - 1].id));
      console.log("lastId", lastId);
      return data.data.content;
    } else {
      console.log("firstId", firstId);
      console.log("lastId", lastId);
      const res = await fetch(`http://k10d203.p.ssafy.io:18080/api/message?lastid=${lastId}&size=${pageParam}`, {
        headers: {
          // 'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
          'Authorization': 'BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g'
        },
      })
      const data = await res.json();
      console.log(data);
      setHasNext(data.data.hasNext);
      // console.log(data.data.content[0].id);
      // setFirstId(data.data.content[0].id);
      dispatch(setFirstId(data.data.content[0].id));
      console.log("firstId", firstId);
      // setLastId(data.data.content[data.data.content.length - 1].id);
      dispatch(setLastId(data.data.content[data.data.content.length - 1].id));
      console.log("lastId", lastId);

      return data.data.content;
    }
  }

  // useEffect(() => {
  //   fetchData({pageParam: 20});
  //   return () => {
  //     console.log('cleanup');
  //     queryClient.removeQueries({ queryKey: ['message'] });
  //   }
  // }, []);

  // useEffect(() => {
  //   console.log("data", data);
  // }, [data]);

  const { data, status, error, fetchNextPage, isFetchingNextPage, hasNextPage } = useInfiniteQuery({
    queryKey: ['message'],
    queryFn: fetchData,
    initialPageParam: 20,
    getNextPageParam: (lastPage, allPages) => {
      console.log("lastPage", lastPage);
      console.log("allPages", allPages);
      // return lastPage.length ? allPages.length + 1 : undefined;
      // console.log({lastPage, allPages});
      // return allPages.length + 1;
      // const nextPage = lastPage.length ? allPages.length : undefined;
      return 20;
      // return nextPage;
    },
    refetchOnMount: false,
    refetchOnWindowFocus: false,
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
    if (inView && hasNextPage && hasNext){
      // console.log('Fire!!!');
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage])

  useEffect(() => {
    data
  }, [data])

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