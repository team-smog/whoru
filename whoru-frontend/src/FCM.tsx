// FCM.jsx
import { useEffect, useRef, useState } from "react"
import { getFirebaseMessagingObject } from "./FirebaseUtil"
import { onMessage } from "firebase/messaging";


type Props = {
    token: string;
  };
  

export const FCMComponent = (props: Props) => {
  // const baseUrl = 'https://k10d203.p.ssafy.io/api'
  // const baseUrl = 'https://codearena.shop/api'
  const baseUrl = import.meta.env.VITE_BASE_URL
    const [text, setText] = useState("");
    const messagingObject = useRef(null);
    useEffect(()=>{
        messagingObject.current = getFirebaseMessagingObject()
    },[])
    if(messagingObject.current !== null){
        onMessage(messagingObject.current, (body)=>{
            if (body.notification) {
                alert(body.notification.body);
              }
        })
    }
    const onChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) =>{
        setText(e.target.value);
    }
    const onClickHandler = () =>{
        fetch(`${baseUrl}/message/text`,{
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify({
                token: props.token,
                content: text
            })
        })
    }
    return (
        <div>
            <input type="text" value={text} onChange={onChangeHandler}/>
            <button onClick={onClickHandler}> 메세지 발송 </button>
        </div>
    )
}