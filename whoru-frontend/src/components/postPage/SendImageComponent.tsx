import { useEffect ,useRef, useState } from 'react'
import styles from './SendImageComponent.module.css'
import ulIcon from '../../assets/components/InboxImageComponent/image-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxImageComponent/image-component-sq-button.svg'
import xIcon from '../../assets/components/InboxImageComponent/image-component-x-button.svg'
import camerabutton from '../../assets/components/InboxImageComponent/image-component-camera-button.svg'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import Swal from 'sweetalert2'


const SendImageComponent = ({ messageId }: { messageId: number | null}) => {
  const baseUrl = 'https://k10d203.p.ssafy.io/api'
  // const baseUrl = 'https://codearena.shop/api'
  const navigate = useNavigate();
  const fileInputRef = useRef(null);
  const [imageSrc, setImageSrc] = useState<string | null>(null);
  const [imageFile, setImageFile] = useState<File | null>(null);
  const accessToken = localStorage.getItem('AccessToken');

  const handleUploadAreaClick = () => {
    if (fileInputRef.current) {
      (fileInputRef.current as HTMLInputElement).click();
    }
  };

  const Toast = Swal.mixin({
    toast: true,
    position: 'top',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
  })

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setImageFile(null); 
    setImageSrc(null); 
    const files = event.target.files;
    if (files) {
      const file = files[0];
      if (!file) {
        return;
      }
      setImageFile(file); 
      // console.log("ImgFile", imageFile);

      const reader = new FileReader();

      reader.onloadend = () => {
        setImageSrc(reader.result as string | null);
      };

      reader.readAsDataURL(file);
    }
  };
  
  const handleSendClick = async () => {
    if (messageId !== null) {
      if (imageFile) {
        try {
          const formData = new FormData();
          formData.append('file', imageFile);
          // console.log(imageFile);
          // console.log("답장 이미지")
    
          await axios.post(`${baseUrl}/message/${messageId}/file`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
              Authorization: `Bearer ${accessToken}`
            },
          })
          .then(() => {
            // console.log(res.data);
            setImageFile(null);
            setImageSrc(null);
            window.scrollTo(0, 0);
            navigate('/');
          })
        } catch (error) {
          // console.error('Error:', error);
          Swal.fire({
            icon: 'error',
            title: '실패',
            text: '이미지 전송에 실패했습니다.',
          });
          window.scrollTo(0, 0);
          navigate('/');
        }
      } else {
        Swal.fire({
          icon: 'error',
          title: '실패',
          text: '이미지를 업로드해주세요.',
        });
      }
    } else if (messageId === null) {
      if (imageFile) {
        try {
          const formData = new FormData();
          formData.append('file', imageFile);
          // console.log(imageFile);
    
          await axios.post(`${baseUrl}/message/file`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
              Authorization: `Bearer ${accessToken}`,
            },
          })
          .then((res) => {
            // console.log(res.data);
            setImageFile(null);
            setImageSrc(null);
            // console.log("ImgSrc", imageSrc);
            if (res.data.data.randomBoxProvided === true) {
              Toast.fire({
                icon: 'success',
                title: '랜덤 박스에 당첨되었습니다!',
              });
            }
            window.scrollTo(0, 0);
            navigate('/');
          })
        } catch (error) {
          // console.error('Error:', error);
          Swal.fire({
            icon: 'error',
            title: '실패',
            text: '이미지 전송에 실패했습니다.',
          });
          window.scrollTo(0, 0);
          navigate('/');
        }
      } else {
        Swal.fire({
          icon: 'error',
          title: '실패',
          text: '이미지를 업로드해주세요.',
        });
      }
    }
  }

  const handleCancelClick = () => {
    if (fileInputRef.current) {
      (fileInputRef.current as HTMLInputElement).value = '';
    }
    setImageSrc(null);
    setImageFile(null);
  };

  useEffect(() => {
    // console.log("ImgSrc", imageSrc);
    // console.log("ImgFile", imageFile);
  }, [imageSrc, imageFile]);

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
          style={{ display: 'none' }}
        />
          <div className={styles.sendImageComponentBodyUploadArea} onClick={handleUploadAreaClick}>
          {imageSrc ? (
            <img src={imageSrc} alt="Uploaded"  className={styles.sendImageComponentBodyUploadImageArea}/>
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
        <button className={styles.sendImageComponentFooterReportButton} onClick={handleCancelClick}>취소</button>
      </div>
    </div>
  )
}

export default SendImageComponent