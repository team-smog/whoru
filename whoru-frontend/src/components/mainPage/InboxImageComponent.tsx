import React from 'react'
import styles from './InboxImageComponent.module.css'
import ulIcon from '../../assets/components/InboxImageComponent/image-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxImageComponent/image-component-sq-button.svg'
import xIcon from '../../assets/components/InboxImageComponent/image-component-x-button.svg'


const InboxImageComponent = () => {
  return (
    <div className={styles.InboxImageComponent}>
      <div className={styles.inboxImageComponentHeader}>
        <div className={styles.inboxImageComponentHeaderText}>
          <p className={styles.inboxImageComponentHeaderTextTitle}>익명 메세지</p>
          <p className={styles.inboxImageComponentHeaderTime}>1분전</p>
        </div>
        <div className={styles.inboxImageComponentHeaderIcons}>
          <img src={ulIcon} alt="ul-icon" />
          <img src={sqIcon} alt="sq-icon" />
          <img src={xIcon} alt="x-icon" />
        </div>
      </div>
      <div className={styles.inboxImageComponentBody}>
        {/* 
            일단 랜덤 사진 불러오기
            TODO: 백엔드와 연동하여 실제 데이터를 받아오도록 수정
            React Query 사용
          */}
          <img 
            // className='min-w-[100px] max-w-[360px] min-h-[100px] max-h-[500px]'
            className={styles.inboxImageComponentBodyMain}
            src="https://source.unsplash.com/random"
            alt="이미지"
          />
      </div>
      <div className={styles.inboxImageComponentFooter}>
        <button className={styles.inboxImageComponentFooterButton}>답장</button>
        <button className={styles.inboxImageComponentFooterReportButton}>신고</button>
      </div>
    </div>
  )
}

export default InboxImageComponent