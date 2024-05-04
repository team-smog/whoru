// import axios from 'axios'
// import { useEffect,useState } from 'react'
import styles from './InboxTextComponent.module.css'
import ulIcon from '../../assets/components/InboxTextComponent/text-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxTextComponent/text-component-sq-button.svg'
import xIcon from '../../assets/components/InboxTextComponent/text-component-x-button.svg'
import { MessageInfoDetail } from '../../types/mainTypes'


interface InboxTextComponentProps extends React.HTMLAttributes<HTMLDivElement>{
  message: MessageInfoDetail;
  innerRef?: React.Ref<HTMLDivElement>;
}

const InboxTextComponent: React.FC<InboxTextComponentProps> = ({ message, innerRef, ...props }) => {
  // const [content, setContent] = useState<string>("")

  // 일단 랜덤 글 불러오기
  // TODO: 백엔드와 연동하여 실제 데이터를 받아오도록 수정
  // React Query 사용
  // useEffect(() => {
  //   axios.get('https://api.chucknorris.io/jokes/random')
  //   .then((res) => {
  //     console.log(res.data.value)
  //     setContent(res.data.value)
  //   })
  //   .catch((err) => {
  //     console.log(err)
  //   })
  // }, [])

  return (
    <div className={styles.inboxTextComponent} key={message.id} ref={innerRef} {...props}>
      <div className={styles.inboxTextComponentHeader} key={message.id} {...props}>
        <div className={styles.inboxTextComponentHeaderText}>
          <p className={styles.inboxTextComponentHeaderTextTitle}>익명 메세지</p>
          <p className={styles.inboxTextComponentHeaderTime}>시간계산</p>
        </div>
        <div className={styles.inboxTextComponentHeaderIcons}>
          <img src={ulIcon} alt="ul-icon" />
          <img src={sqIcon} alt="sq-icon" />
          <img src={xIcon} alt="x-icon" />
        </div>
      </div>
      <div className={styles.inboxTextComponentBody}>
        <div className={styles.inboxTextComponentBodyMain}>
          <p className={styles.inboxTextComponentBodyMainText}>{message.content}</p>
          <div className={styles.inboxTextComponentFooter}>
            <button className={styles.inboxTextComponentFooterButton}>답장</button>
            <button className={styles.inboxTextComponentFooterButton}>번역</button>
            <button className={styles.inboxTextComponentFooterReportButton}>신고</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default InboxTextComponent