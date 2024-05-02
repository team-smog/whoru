import { useRef, useState } from 'react'
import styles from './SendImageComponent.module.css'
import ulIcon from '../../assets/components/InboxImageComponent/image-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxImageComponent/image-component-sq-button.svg'
import xIcon from '../../assets/components/InboxImageComponent/image-component-x-button.svg'
import camerabutton from '../../assets/components/InboxImageComponent/image-component-camera-button.svg'
import axios from 'axios'


const SendImageComponent = () => {
  const fileInputRef = useRef(null);
  const [imageSrc, setImageSrc] = useState<string | null>(null);
  // const [imageSrc] = useState(null);

  const handleUploadAreaClick = () => {
    if (fileInputRef.current) {
      (fileInputRef.current as HTMLInputElement).click();
    }
  };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files) {
      const file = files[0];
      const reader = new FileReader();
      reader.onloadend = () => {
        setImageSrc(reader.result as string | null);
      };
      reader.readAsDataURL(file); 
    }
  };

  const handleSendClick = async () => {
    if (imageSrc) {
      try {
        const formData = new FormData();
        formData.append('image', imageSrc);

        await axios.post('https://k10d203.p.ssafy.io//message/file', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
          },
        })
        .then((res) => {
          console.log(res.data);
        })

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
        <button 
          className={styles.sendImageComponentFooterButton} 
          onClick={handleSendClick}
        >
          전송
        </button>
        <button className={styles.sendImageComponentFooterReportButton}>취소</button>
      </div>
    </div>
  )
}

export default SendImageComponent