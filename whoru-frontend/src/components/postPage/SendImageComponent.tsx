import React, { useRef, useState } from 'react'
import styles from './SendImageComponent.module.css'
import ulIcon from '../../assets/components/InboxImageComponent/image-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxImageComponent/image-component-sq-button.svg'
import xIcon from '../../assets/components/InboxImageComponent/image-component-x-button.svg'
import camerabutton from '../../assets/components/InboxImageComponent/image-component-camera-button.svg'
import axios from 'axios'


const SendImageComponent = () => {
  const fileInputRef = useRef(null);
  const [imageSrc, setImageSrc] = useState(null);

  const handleUploadAreaClick = () => {
    fileInputRef.current.click();
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImageSrc(reader.result);
      };
      reader.readAsDataURL(file); 
    }
  };

  const handleSendClick = async () => {
    if (imageSrc) {
      try {
        const formData = new FormData();
        formData.append('image', imageSrc);

        const response = await axios.post('https://S10P31D203WRU.com//message/file', formData, { //TODO: 서버 URL을 입력
          headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
          },
        });

        setImageSrc(null);

      } catch (error) {
        console.error('Error:', error);
      }
    }
  };

  return (
    <div className={styles.sendImageComponent}>
      <div className={styles.sendImageComponentHeader}>
        <div className={styles.sendImageComponentHeaderText}>
          <p className={styles.sendImageComponentHeaderTextTitle}>익명 메세지</p>
          <p className={styles.sendImageComponentHeaderTime}>1분전</p>
        </div>
        <div className={styles.sendImageComponentHeaderIcons}>
          <img src={ulIcon} alt="ul-icon" />
          <img src={sqIcon} alt="sq-icon" />
          <img src={xIcon} alt="x-icon" />
        </div>
      </div>
      <div className={styles.sendImageComponentBody}>
        <input 
          type='file' 
          accept='image/*' 
          className={styles.sendImageComponentBodyInput} 
          ref={fileInputRef}
          onChange={handleFileChange}
          style={{ display: 'none' }} // input을 숨깁니다. 
        />
          <div className={styles.sendImageComponentBodyUploadArea} onClick={handleUploadAreaClick}>
          {imageSrc ? (
            <img src={imageSrc} alt="Uploaded" />
          ) : (
            <>
              <img src={camerabutton} alt="camera-button" />
              <p className={styles.sendImageComponentBodyUploadAreaText}>사진을 업로드해주세요</p>
            </>
          )}
        </div>
      </div>
      <div className={styles.sendImageComponentFooter}>
        <button className={styles.sendImageComponentFooterButton} onClick={handleSendClick}>전송</button>
        <button className={styles.sendImageComponentFooterReportButton}>취소</button>
      </div>
    </div>
  )
}

export default SendImageComponent