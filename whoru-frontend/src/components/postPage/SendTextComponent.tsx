import React, { useEffect, useState, useRef } from 'react'
import styles from './SendTextComponent.module.css'
import ulIcon from '../../assets/components/InboxTextComponent/text-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxTextComponent/text-component-sq-button.svg'
import xIcon from '../../assets/components/InboxTextComponent/text-component-x-button.svg'
import { useNavigate } from 'react-router-dom'
import Swal from 'sweetalert2'
import { axiosWithCredentialInstance } from '@/apis/axiosInstance'

const SendTextComponent = ({ messageId }: { messageId: number | null}) => {
  const navigate = useNavigate();
  const textareaRef = useRef<HTMLTextAreaElement | null>(null);
  const [text, setText] = useState("");
  const accessToken = localStorage.getItem('AccessToken');
  
  const Toast = Swal.mixin({
    toast: true,
    position: 'top',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
  })

  const onChangeText = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setText(e.currentTarget.value);
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

  const sendMessage = async () => {
    try {
      if (messageId !== null) {
        await axiosWithCredentialInstance.post(`message/${messageId}/text`,
          {
            content: text,
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${accessToken}`
            },
          }
        )
        .then(() => {
          window.scrollTo(0, 0);
          navigate('/');
        })
      } else if (messageId === null) {
        await axiosWithCredentialInstance.post(`message`,
          {
            content: text,
          },
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${accessToken}`
            },
          }
        )
        .then((res) => {
          if (res.data.data.randomBoxProvided === true) {
            Toast.fire({
              icon: 'success',
              title: '랜덤 박스에 당첨되었습니다!',
            });
          }
          window.scrollTo(0, 0);
          navigate('/');
        })
      }
    } catch (error: any) {
      if (error.response.data.errorCode === 400) {
        Swal.fire({
          icon: 'error',
          title: '실패',
          text: '메세지가 너무 짧습니다',
        });
      } else if (error.response.data.errorCode === 403){
        Swal.fire({
          icon: 'error',
          title: '실패',
          text: '사용 정지된 유저입니다. 관리자에게 문의하세요',
        });
        window.scrollTo(0, 0);
        navigate('/');
      } else {
        Swal.fire({
          icon: 'error',
          title: '실패',
          text: '알 수 없는 오류가 발생했습니다',
        });
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
          <textarea className={styles.sendTextComponentBodyMainText} 
          name="textarea" 
          ref={textareaRef}
          value={text}
          id="textareaRef" 
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