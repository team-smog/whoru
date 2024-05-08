// import React from 'react'
import styles from './InboxVoiceComponent.module.css'
// import Header from '@/assets/components/InboxVoiceComponent/voice-component-header.svg'
import back from '@/assets/components/InboxVoiceComponent/voice-component-back-button.svg'
import front from '@/assets/components/InboxVoiceComponent/voice-component-front-button.svg'
import re from '@/assets/components/InboxVoiceComponent/voice-component-re-button.svg'
import star from '@/assets/components/InboxVoiceComponent/voice-component-star-button.svg'
import AudioPlayer from 'react-h5-audio-player';
import 'react-h5-audio-player/lib/styles.css';
import "./audioStyles.css";
import { MessageInfoDetail } from '../../types/mainTypes'
import { useDispatch } from 'react-redux';
import { setReplyMessage } from '@/stores/storeMessageId';
import { useNavigate } from 'react-router-dom'
import axios from 'axios'



interface InboxVoiceComponentProps extends React.HTMLAttributes<HTMLDivElement>{
  message: MessageInfoDetail;
  innerRef?: React.Ref<HTMLDivElement>;
}

const InboxVoiceComponent: React.FC<InboxVoiceComponentProps> = ({ message, innerRef, ...props }) => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const accessToken = localStorage.getItem('AccessToken')
  // const messageId = useSelector((state: any) => state.messageId)

  const handleReply = (messageId: number) => {
    dispatch(setReplyMessage(messageId))
    console.log('messageId', messageId)
    navigate('/post')
  }

  const handleReport = (messageId:number, senderId:number) => {
    axios.post('http://k10d203.p.ssafy.io/api/report/member',
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
    .then((res) => {
      console.log(res);
      alert('신고가 완료되었습니다.');
    })
    .catch((err) => {
      console.log(err);
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
      {/* <img src={Header} alt="component-Header" className={styles.inboxVoiceComponentHeader} /> */}
      <div className={styles.inboxVoiceComponentHeader} key={message.id} {...props}>
        <div className={styles.inboxVoiceComponentHeaderText}>
          <p className={styles.inboxVoiceComponentHeaderTextTitle}>익명 메세지</p>
          <p className={styles.inboxVoiceComponentHeaderTime}>{timeFromNow}</p>
        </div>
        <div className={styles.inboxVoiceComponentHeaderIcons}>
          <img src={back} alt="back-icon" />
          <img src={front} alt="front-icon" />
          <img src={re} alt="re-icon" />
          <div className={styles.inboxVoiceComponentHeaderSearchArea}></div>
          <img src={star} alt="star-icon" />
        </div>
      </div>
      <div className={styles.inboxVoiceComponentBody}>
        <div className={styles.inboxVoiceComponentBodyMain}>
          <AudioPlayer
            className={styles.inboxVoiceComponentBodyMainAudio}
            src={message.content}
            onPlay={() => {console.log("onPlay")}}
            layout="stacked-reverse"
            style={{ width: "100%", 
                      height: "100%", 
                      background: "linear-gradient(90deg, #E08EDC 0%, #AFA4F4 100%)", 
                      border: "3px solid #423752", 
                      borderRadius: "10px"}}
          />
        </div>
        <div className={styles.inboxVoiceComponentFooter}>
          <button className={styles.inboxVoiceComponentFooterButton} onClick={() => handleReply(message.id)}>답장</button>
          <button className={styles.inboxVoiceComponentFooterReportButton} onClick={() => handleReport(message.id, message.senderId)}>신고</button>
      </div>
      </div>
    </div>
  )
}

export default InboxVoiceComponent