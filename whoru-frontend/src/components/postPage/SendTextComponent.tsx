import React, { useEffect, useState, useRef } from 'react'
import styles from './SendTextComponent.module.css'
import ulIcon from '../../assets/components/InboxTextComponent/text-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxTextComponent/text-component-sq-button.svg'
import xIcon from '../../assets/components/InboxTextComponent/text-component-x-button.svg'
import axios from 'axios'
// import { requestPermission } from '../../FirebaseUtil'
// import { FCMComponent } from '../../FCM';

const SendTextComponent = () => {
  const textareaRef = useRef<HTMLTextAreaElement | null>(null);
  const [text, setText] = useState("");

  const onChangeText = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setText(e.currentTarget.value);
    // textarea 높이 조절
    if (textareaRef && textareaRef.current) {
      textareaRef.current.style.height = "0px";
      const scrollHeight = textareaRef.current.scrollHeight;
      textareaRef.current.style.height = scrollHeight + "px";
    }
  };

  const onCancel = async () => {
    await setText("");
    if (textareaRef && textareaRef.current) {
      textareaRef.current.style.height = "0px";
      const scrollHeight = textareaRef.current.scrollHeight;
      textareaRef.current.style.height = scrollHeight + "px";
    }
  };

  useEffect(() => {
    textareaRef.current?.focus();
  }, [])

  // const [token, setToken] = useState<string>("");
  // useEffect(() => {
  //   const resultToken = requestPermission();
  //   resultToken.then((token) => {
  //     setToken(token);
  //   });
  // }, []);

  const sendMessage = async () => {
    try {
      await axios.post(
        // 'https://k10d203.p.ssafy.io/api/message/text',
          // 'http://192.168.100.208:8080/api/message',
          'http://k10d203.p.ssafy.io:18080/api/message',
        {
          // senderId: 'your-user-id', // 보내는 사람 userId
          content: text, // text 내용
          // token: token
        },
        {
          headers: {
            'Content-Type': 'application/json',
            // 'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
            'Authorization': 'BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g'
          },
        }
      )
      .then((res) => {
        console.log(res);
      })
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className={styles.sendTextComponent}>
      <div className={styles.sendTextComponentHeader}>
        <div className={styles.sendTextComponentHeaderText}>
          <p className={styles.sendTextComponentHeaderTextTitle}>익명 메세지</p>
          <p className={styles.sendTextComponentHeaderTime}></p>
        </div>
        <div className={styles.sendTextComponentHeaderIcons}>
          <img src={ulIcon} alt="ul-icon" />
          <img src={sqIcon} alt="sq-icon" />
          <img src={xIcon} alt="x-icon" />
        </div>
      </div>
      <div className={styles.sendTextComponentBody}>
        <div className={styles.sendTextComponentBodyMain}>
          {/* <FCMComponent token={token}/> */}
          <textarea className={styles.sendTextComponentBodyMainText} 
          name="textarea" 
          ref={textareaRef}
          value={text}
          id="textareaRef" 
          // cols="30" 
          // rows="10" 
          maxLength={200}
          autoFocus={true}
          placeholder='메세지를 입력하세요'
          onChange={onChangeText}
          ></textarea>
          <div className={styles.sendTextComponentFooter}>
            <button 
              className={styles.sendTextComponentFooterButton} 
              onClick={sendMessage}
            >
              전송
            </button>
            <button 
              className={styles.sendTextComponentFooterReportButton}
              onClick={onCancel}
            >
              취소
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default SendTextComponent