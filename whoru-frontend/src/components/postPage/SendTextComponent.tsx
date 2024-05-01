import React, { useEffect, useState, useRef, useCallback } from 'react'
import styles from './SendTextComponent.module.css'
import ulIcon from '../../assets/components/InboxTextComponent/text-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxTextComponent/text-component-sq-button.svg'
import xIcon from '../../assets/components/InboxTextComponent/text-component-x-button.svg'
import axios from 'axios'

const SendTextComponent = () => {
  const [content, setContent] = useState<string>("")
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

  useEffect(() => {
    textareaRef.current?.focus();
  }, [])

  const sendMessage = async () => {
    try {
      const response = await axios.post(
        'https://S10P31D203WRU.com/message/text', 
        {
          senderId: 'your-user-id', // 보내는 사람 userId
          content: text // text 내용
        },
        {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
          },
        }
      );
      console.log(response.data);
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
          <textarea className={styles.sendTextComponentBodyMainText} 
          name="textarea" 
          ref={textareaRef}
          value={text}
          id="" 
          // cols="30" 
          // rows="10" 
          maxLength={200}
          autoFocus={true}
          placeholder='메세지를 입력하세요'
          onChange={onChangeText}
          ></textarea>
          <div className={styles.sendTextComponentFooter}>
            <button className={styles.sendTextComponentFooterButton} onClick={sendMessage}>전송</button>
            <button className={styles.sendTextComponentFooterReportButton}>취소</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default SendTextComponent