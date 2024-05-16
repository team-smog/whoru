// import React from 'react'
import styles from './InboxVoiceComponent.module.css'
// import Header from '@/assets/components/InboxVoiceComponent/voice-component-header.svg'
import back from '@/assets/components/InboxVoiceComponent/voice-component-back-button.svg'
import front from '@/assets/components/InboxVoiceComponent/voice-component-front-button.svg'
import re from '@/assets/components/InboxVoiceComponent/voice-component-re-button.svg'
import xbtn from'@/assets/components/InboxTextComponent/text-component-x-button.svg'
// import star from '@/assets/components/InboxVoiceComponent/voice-component-star-button.svg'
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';
import "./audioStyles.css";
import { MessageInfoDetail } from '../../types/mainTypes'
import { useDispatch } from 'react-redux';
import { setReplyMessage } from '@/stores/store';
import { useNavigate } from 'react-router-dom'
import Swal from 'sweetalert2'
import { useState } from 'react'
import ParentInboxImageComponent from './ParentInboxImageComponent'
import ParentInboxTextComponent from './ParentInboxTextComponent'
import ParentInboxVoiceComponent from './ParentInboxVoiceComponent'
import { axiosWithCredentialInstance } from '@/apis/axiosInstance'



interface InboxVoiceComponentProps extends React.HTMLAttributes<HTMLDivElement>{
  message: MessageInfoDetail;
  innerRef?: React.Ref<HTMLDivElement>;
  
}

const InboxVoiceComponent: React.FC<InboxVoiceComponentProps> = ({ message, innerRef, ...props }) => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const accessToken = localStorage.getItem('AccessToken')
  const [openModal, setOpenModal] = useState<boolean>(false);

  const replyButtonStyle = message.responseStatus ?  {backgroundColor: 'gray'} : {}
  const reportButtonStyle = message.isReported ? { backgroundColor: 'gray' } : {}


  const handleReply = (messageId: number) => {
    dispatch(setReplyMessage(messageId))
    // console.log('messageId', messageId)
    navigate('/post')
  }

  const handleReport = (messageId:number, senderId:number) => {
    Swal.fire({
      title: '신고하시겠습니까?',
      showDenyButton: true,
      confirmButtonText: `신고`,
      denyButtonText: `취소`,
    }).then((result) => {
      if (result.isConfirmed) {
        axiosWithCredentialInstance.post(`report/member`,
        {
          messageId: messageId,
          senderId: senderId,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${accessToken}`
        }}
        )
        .then(() => {
          // console.log(res);
          Swal.fire({
            title: '신고가 완료되었습니다.',
            icon: 'success',
            timer: 2500, // 2.5초 후에 자동으로 닫힘
            showConfirmButton: false
          })
          .then(() => {
            window.location.reload();
          });
        })
        .catch(() => {
          // console.log(err);
        })
      }
    })
  }

  const createDate = new Date(message.createDate);
  const now = new Date();

  const diffInMilliseconds = now.getTime() - createDate.getTime();
  const diffInSeconds = Math.floor(diffInMilliseconds / 1000);
  const diffInMinutes = Math.floor(diffInSeconds / 60);
  const diffInHours = Math.floor(diffInMinutes / 60);
  const diffInDays = Math.floor(diffInHours / 24);

  let timeFromNow = '';
  if (diffInDays > 0) {
    timeFromNow = `${diffInDays}일 전`;
  } else if (diffInHours > 0) {
    timeFromNow = `${diffInHours}시간 전`;
  } else if (diffInMinutes > 0) {
    timeFromNow = `${diffInMinutes}분 전`;
  } else {
    timeFromNow = `${diffInSeconds}초 전`;
  }

  return (
    <div className={styles.inboxVoiceComponent} key={message.id} ref={innerRef} {...props}>
      <div className={styles.inboxVoiceComponentHeader} key={message.id} {...props}>
        <div className={styles.inboxVoiceComponentHeaderText}>
          <p className={styles.inboxVoiceComponentHeaderTextTitle}>{message.isResponse ? "답장 메세지" : "익명 메세지"}</p>
          <p className={styles.inboxVoiceComponentHeaderTime}>{timeFromNow}</p>
        </div>
        <div className={styles.inboxVoiceComponentHeaderIcons}>
          <img src={back} alt="back-icon" />
          <img src={front} alt="front-icon" />
          <img src={re} alt="re-icon" />
          <div className={styles.inboxVoiceComponentHeaderSearchArea}></div>
          <img src={xbtn} alt="xbtn-icon" className={styles.xbtn}/>
        </div>
      </div>
      <div className={styles.inboxVoiceComponentBody}>
        <div className={styles.inboxVoiceComponentBodyMain}>
          <AudioPlayer
            className={styles.inboxVoiceComponentBodyMainAudio}
            src={message.content}
            onPlay={() => {}}
            layout="stacked-reverse"
            style={{ width: "100%",
                      height: "100%",
                      background: "linear-gradient(90deg, #E08EDC 0%, #AFA4F4 100%)", 
                      border: "3px solid #423752", 
                      borderRadius: "10px"}}
          />
        </div>
        <div className={styles.inboxVoiceComponentFooter}>
          <button className={message.responseStatus || message.isResponse ? styles.inboxVoiceComponentFooterButtonDisable : styles.inboxVoiceComponentFooterButton} 
            onClick={() => handleReply(message.id)}
            style={replyButtonStyle}
            disabled={message.responseStatus || message.isResponse}
          >
            답장
          </button>
          <button className={styles.inboxVoiceComponentFooterReportButton} 
            onClick={() => handleReport(message.id, message.senderId)}
            style={reportButtonStyle}
            disabled={message.isReported}
          >
            신고
        </button>
        {message.isResponse && <button className={styles.inboxImageComponentFooterFromButton} onClick={() => {
          if (openModal === false) {
            setOpenModal(true)
          } else {
            setOpenModal(false)
          }
        }}>from.</button>}
      </div>
      {openModal && message.parent.contentType === 'text' && 
        <ParentInboxTextComponent message={message.parent} setOpenModal={setOpenModal} />
      }
      {openModal && message.parent.contentType === 'image' && 
        <ParentInboxImageComponent message={message.parent} setOpenModal={setOpenModal} />
      }
      {openModal && message.parent.contentType === 'voice' && 
        <ParentInboxVoiceComponent message={message.parent} setOpenModal={setOpenModal} />
      }
      </div>
    </div>
  )
}

export default InboxVoiceComponent