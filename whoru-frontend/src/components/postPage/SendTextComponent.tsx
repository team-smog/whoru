import React, { useEffect, useState, useRef } from 'react'
import styles from './SendTextComponent.module.css'
import ulIcon from '../../assets/components/InboxTextComponent/text-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxTextComponent/text-component-sq-button.svg'
import xIcon from '../../assets/components/InboxTextComponent/text-component-x-button.svg'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { setBoxCountP } from '@/stores/store'
import { useDispatch } from 'react-redux'
import Swal from 'sweetalert2'

const SendTextComponent = ({ messageId }: { messageId: number | null}) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const textareaRef = useRef<HTMLTextAreaElement | null>(null);
  const [text, setText] = useState("");
  const accessToken = localStorage.getItem('AccessToken');

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
      if (messageId !== null) {
        await axios.post(
          `https://k10d203.p.ssafy.io/api/message/${messageId}/text`,
          {
            // senderId: 'your-user-id', // 보내는 사람 userId
            content: text, // text 내용
            // token: token
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${accessToken}`
            },
          }
        )
        .then((res) => {
          console.log(res);
          navigate('/');
        })
      } else if (messageId === null) {
        await axios.post(
          'https://k10d203.p.ssafy.io/api/message',
          {
            // senderId: 'your-user-id', // 보내는 사람 userId
            content: text, // text 내용
            // token: token
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${accessToken}`
            },
          }
        )
        .then((res) => {
          console.log(res);
          if (res.data.data.randomBoxProvided === true) {
            dispatch(setBoxCountP());
          }
          navigate('/');
        })
      }
    } catch (error: any) {
      console.error(error);
      if (error.response.data.errorCode === 400) {
        Swal.fire({
          icon: 'error',
          title: '실패',
          text: '메세지가 너무 짧습니다',
        });
      } else {
        Swal.fire({
          icon: 'error',
          title: '실패',
          text: '메세지 전송에 실패했습니다.',
        });
        navigate('/');
      }
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