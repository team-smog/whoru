import { useNavigate } from "react-router-dom";
import Bell from "@/assets/@common/Bell.png"
import { useEffect, useRef, useState } from "react";
import { getFirebaseMessagingObject, requestPermission } from "@/FirebaseUtil"
import { onMessage } from "firebase/messaging";
// import { requestPermission } from './FirebaseUtil';

export interface IHeaderInfo {
  left_1: React.ReactNode | null;
  left_2: JSX.Element | null;
  center: string | null;
  right: React.ReactNode | null;
}

const Header = (props: {info:IHeaderInfo}) => {
  const navigate = useNavigate();

  // const messagingObject = useRef(null);
  //   useEffect(()=>{
  //       messagingObject.current = getFirebaseMessagingObject()
  //       console.log(messagingObject.current)
  //   },[])
  //   if(messagingObject.current !== null){
  //       onMessage(messagingObject.current, (body)=>{
  //         if (body && body.notification) {
  //           console.log(body.notification.body);
  //           alert(body.notification.body);
  //         }
  //       })
  //   }

  const [ ,setToken] = useState<string>("");
  useEffect(() => {
    const resultToken = requestPermission();
    resultToken.then((token) => {
      setToken(token);
    });
  }, []);

  const messagingObject = useRef(null);
  useEffect(()=>{
      messagingObject.current = getFirebaseMessagingObject()
    },[])
  if(messagingObject.current !== null){
    console.log(messagingObject.current)
      onMessage(messagingObject.current, (body)=>{
        if (body && body.notification) {
          console.log(body.notification.body);
          alert(body.notification.body);
        }
      })
  }


  useEffect(() => {
    navigator.serviceWorker.register('../public/firebase-messaging-sw.js')
      .then((registration) => {
        console.log('Service Worker registered with scope:', registration.scope);
      }).catch((err) => {
        console.error('Service Worker registration failed:', err);
      });
  }, []);

  const {left_1, left_2, center, right} = props.info;

  return(
    <div className="max-w-[500px] w-full z-[2] h-12 px-4 top-0 flex fixed justify-between items-center">
      <div className="flex flex-1 justify-start items-center">
        {left_1 && (
          <div className="w-13 text-[20px] text-text_color">
            <div>{left_1}</div>
          </div>
        )}
        {left_2 && (
          <button onClick={() => navigate(-1)}>
            <div className="w-4 h-4">{left_2}</div>
          </button>
        )}
      </div>
      <div className="flex-1 flex justify-center">{center && <p className="text-white">{center}</p>}</div>
      <div className="flex flex-1 justify-end items-center relative">
        <button onClick={() => navigate('/alarm')}>
          {right && <img src={Bell} alt="Alarm" className="w-6 h-6"/>}
        </button>
      </div>
    </div>
  )
}

export default Header;