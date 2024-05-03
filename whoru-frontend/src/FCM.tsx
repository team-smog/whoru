// FCM.jsx
import { useEffect, useRef, useState } from "react"
import { getFirebaseMessagingObject } from "./FirebaseUtil"
import { onMessage } from "firebase/messaging";


type Props = {
    token: string;
  };
  

// FCMComponent는 FCM을 테스트하기 위한 컴포넌트입니다.
export const FCMComponent = (props: Props) => {
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
    // 여기부분은 axios가 편하시면 바꿔서 사용하시면 됨
        fetch("https://k10d203.p.ssafy.io/message/text",{
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