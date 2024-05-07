// import React from 'react'
import styles from './InboxImageComponent.module.css'
import ulIcon from '../../assets/components/InboxImageComponent/image-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxImageComponent/image-component-sq-button.svg'
import xIcon from '../../assets/components/InboxImageComponent/image-component-x-button.svg'
import { MessageInfoDetail } from '../../types/mainTypes'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom'
import { setReplyMessage } from '@/stores/storeMessageId';
import axios from 'axios'


interface InboxImageComponentProps extends React.HTMLAttributes<HTMLDivElement>{
  message: MessageInfoDetail;
  innerRef?: React.Ref<HTMLDivElement>;
}

const InboxImageComponent: React.FC<InboxImageComponentProps> = ({ message, innerRef, ...props }) => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  // const messageId = useSelector((state: any) => state.reply.messageId)

  const handleReply = (messageId: number) => {
    dispatch(setReplyMessage(messageId))
    // console.log('messageId', messageId)
    navigate('/post')
  }

  const handleReport = (messageId:number, senderId:number) => {
    axios.post('http://k10d203.p.ssafy.io:18080/api/report/member',
    {
      messageId: messageId,
      senderId: senderId,
    },
    {
      headers: {
        'Content-Type': 'application/json',
        // Authorization: `Bearer ${localStorage.getItem('accessToken')}`
        Authorization: `BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g`,
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
    <div className={styles.InboxImageComponent} key={message.id} ref={innerRef} {...props}>
      <div className={styles.inboxImageComponentHeader} key={message.id} {...props}>
        <div className={styles.inboxImageComponentHeaderText}>
          <p className={styles.inboxImageComponentHeaderTextTitle}>익명 메세지</p>
          <p className={styles.inboxImageComponentHeaderTime}>{timeFromNow}</p>
        </div>
        <div className={styles.inboxImageComponentHeaderIcons}>
          <img src={ulIcon} alt="ul-icon" />
          <img src={sqIcon} alt="sq-icon" />
          <img src={xIcon} alt="x-icon" />
        </div>
      </div>
      <div className={styles.inboxImageComponentBody}>
          <img 
            className={styles.inboxImageComponentBodyMain}
            src={message.content}
            alt="이미지"
          />
      </div>
      <div className={styles.inboxImageComponentFooter}>
        <button className={styles.inboxImageComponentFooterButton} onClick={() => handleReply(message.id)}>답장</button>
        <button className={styles.inboxImageComponentFooterReportButton} onClick={() => handleReport(message.id, message.senderId)}>신고</button>
      </div>
    </div>
  )
}

export default InboxImageComponent