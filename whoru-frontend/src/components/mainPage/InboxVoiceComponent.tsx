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
import { useDispatch, useSelector } from 'react-redux';
import { setReplyMessage } from '@/stores/store';
import { useNavigate } from 'react-router-dom'



interface InboxVoiceComponentProps extends React.HTMLAttributes<HTMLDivElement>{
  message: MessageInfoDetail;
  innerRef?: React.Ref<HTMLDivElement>;
}

const InboxVoiceComponent: React.FC<InboxVoiceComponentProps> = ({ message, innerRef, ...props }) => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const messageId = useSelector((state: any) => state.messageId)

  const handleReply = (messageId: number) => {
    dispatch(setReplyMessage(messageId))
    console.log('messageId', messageId)
    navigate('/post')
  }

  return (
    <div className={styles.inboxVoiceComponent} key={message.id} ref={innerRef} {...props}>
      {/* <img src={Header} alt="component-Header" className={styles.inboxVoiceComponentHeader} /> */}
      <div className={styles.inboxVoiceComponentHeader} key={message.id} {...props}>
        <div className={styles.inboxVoiceComponentHeaderText}>
          <p className={styles.inboxVoiceComponentHeaderTextTitle}>익명 메세지</p>
          <p className={styles.inboxVoiceComponentHeaderTime}>1분전</p>
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
          <button className={styles.inboxVoiceComponentFooterReportButton}>신고</button>
      </div>
      </div>
    </div>
  )
}

export default InboxVoiceComponent