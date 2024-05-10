import { useRef, useState } from 'react'
import styles from './SendImageComponent.module.css'
import ulIcon from '../../assets/components/InboxImageComponent/image-component-ul-button.svg'
import sqIcon from '../../assets/components/InboxImageComponent/image-component-sq-button.svg'
import xIcon from '../../assets/components/InboxImageComponent/image-component-x-button.svg'
import camerabutton from '../../assets/components/InboxImageComponent/image-component-camera-button.svg'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { setBoxCount } from '@/stores/store'
import { useDispatch } from 'react-redux'


const SendImageComponent = ({ messageId }: { messageId: number | null}) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const fileInputRef = useRef(null);
  const [imageSrc, setImageSrc] = useState<string | null>(null);
  const [imageFile, setImageFile] = useState<File | null>(null);
  const accessToken = localStorage.getItem('AccessToken');
  // const [imageSrc] = useState(null);

  const handleUploadAreaClick = () => {
    if (fileInputRef.current) {
      (fileInputRef.current as HTMLInputElement).click();
    }
  };

  // const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
  //   const files = event.target.files;
  //   if (files) {
  //     const file = files[0];
  //     const reader = new FileReader();
  //     reader.onloadend = () => {
  //       setImageSrc(reader.result as string | null);
  //     };
  //     reader.readAsDataURL(file); 
  //   }
  // };

  // const handleSendClick = async () => {
  //   if (imageSrc) {
  //     try {
  //       const formData = new FormData();
  //       formData.append('file', imageSrc);

  //       // await axios.post('https://k10d203.p.ssafy.io/api/message/file', formData, {
  //       await axios.post('http://k10d203.p.ssafy.io:18080/api/message/file', formData, {
  //         headers: {
  //           'Content-Type': 'multipart/form-data',
  //           'Authorization': 'BearereyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsImlkIjoyLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0NzEwMDkxLCJleHAiOjE3NTA3MTAwOTF9.coDlad6k0UadtPqBvTIBFhXByytdncFAvChB0kZnN9g'
  //         },
  //       })
  //       .then((res) => {
  //         console.log(res.data);
  //       })

  //       setImageSrc(null);

  //     } catch (error) {
  //       console.error('Error:', error);
  //     }
  //   }
  // };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files) {
      const file = files[0];
      setImageFile(file); // Save the file instead of its Data URL

      // Create a new FileReader object
      const reader = new FileReader();

      // Set the image source to the result of the FileReader
      reader.onloadend = () => {
        setImageSrc(reader.result as string | null);
      };

      // Start reading the file as Data URL
      reader.readAsDataURL(file);
    }
  };
  
  const handleSendClick = async () => {
    if (messageId !== null) {
      if (imageFile) { // Check if there is a file
        try {
          const formData = new FormData();
          formData.append('file', imageFile); // Add the file to FormData
          console.log(imageFile);
          // console.log(accessToken)
          console.log("답장 이미지")
    
          await axios.post(`https://k10d203.p.ssafy.io/api/message/${messageId}/file`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
              Authorization: `Bearer ${accessToken}`
            },
          })
          .then((res) => {
            console.log(res.data);
            setImageFile(null); // Clear the file
            setImageSrc(null);
            navigate('/');
          })
        } catch (error) {
          console.error('Error:', error);
          alert('이미지 전송에 실패했습니다.');
          navigate('/');
        }
      }
    } else if (messageId === null) {
      if (imageFile) { // Check if there is a file
        try {
          const formData = new FormData();
          formData.append('file', imageFile); // Add the file to FormData
          console.log(imageFile);
          // console.log(accessToken)
    
          await axios.post('https://k10d203.p.ssafy.io/api/message/file', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
              Authorization: `Bearer ${accessToken}`,
            },
          })
          .then((res) => {
            console.log(res.data);
            setImageFile(null); // Clear the file
            setImageSrc(null);
            if (res.data.data.randomBoxProvided === true) {
              dispatch(setBoxCount());
            }
            navigate('/');
          })
        } catch (error) {
          console.error('Error:', error);
          alert('이미지 전송에 실패했습니다.');
          navigate('/');
        }
      }
    }
  }
  

  const handleCancelClick = () => {
    setImageSrc(null);
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
        <button className={styles.sendImageComponentFooterReportButton} onClick={handleCancelClick}>취소</button>
      </div>
    </div>
  )
}

export default SendImageComponent