import { useEffect, useState } from 'react'
import styles from './SendVoiceComponent.module.css'
import back from '@/assets/components/InboxVoiceComponent/voice-component-back-button.svg'
import front from '@/assets/components/InboxVoiceComponent/voice-component-front-button.svg'
import re from '@/assets/components/InboxVoiceComponent/voice-component-re-button.svg'
import star from '@/assets/components/InboxVoiceComponent/voice-component-star-button.svg'
import micbutton from '@/assets/components/InboxVoiceComponent/voice-component-mic-button.svg'
import startbutton from '@/assets/components/InboxVoiceComponent/voice-component-start-button.svg'
import stopbutton from '@/assets/components/InboxVoiceComponent/voice-component-stop-button.svg'
import repeatbutton from '@/assets/components/InboxVoiceComponent/voice-component-repeat-button.svg'
import defaultSvg from '@/assets/components/InboxVoiceComponent/voice-component-default-button.svg'
import postbutton from '@/assets/components/InboxVoiceComponent/voice-component-post-button.svg'
import pausebutton from '@/assets/components/InboxVoiceComponent/voice-component-pause-button.svg'
import { useVoiceVisualizer, VoiceVisualizer } from "react-voice-visualizer";
import 'react-h5-audio-player/lib/styles.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'
import toWav from 'audiobuffer-to-wav';
import Swal from 'sweetalert2'


const SendVoiceComponent = ({ messageId }: { messageId: number | null}) => {
    // const baseUrl = 'https://k10d203.p.ssafy.io/api'
    // const baseUrl = 'https://codearena.shop/api'
    const baseUrl = import.meta.env.VITE_BASE_URL
    const navigate = useNavigate();
    const [currentRecordType,setCurrentRecordType] = useState<string>("")
    const recorderControls = useVoiceVisualizer();
    const accessToken = localStorage.getItem('AccessToken');
    const {
        audioRef,
        isRecordingInProgress,
        isPausedRecordedAudio,
        isCleared,
        isAvailableRecordedAudio,
        recordedBlob,
        formattedRecordingTime,
    } = recorderControls;

    useEffect(() => {
      if (!isRecordingInProgress) return;

      if (isRecordingInProgress===true) {
        setCurrentRecordType("recording")
      }

      // console.log("isRecordingInProgress:", isRecordingInProgress);
    }, [isRecordingInProgress]);

    useEffect(() => {
      if (!isCleared) return;

      if (isCleared===true) {
        setCurrentRecordType("cleared")
      }

    }, [isCleared]);

    useEffect(() => {
      if (!isAvailableRecordedAudio) return;

      if (isAvailableRecordedAudio===true) {
        setCurrentRecordType("audio")
      }

    }, [isAvailableRecordedAudio]);

    const Toast = Swal.mixin({
      toast: true,
      position: 'top',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
    })

    const handlePostButtonClick = async () => {
      if (messageId !== null) {
        if (!recordedBlob) {
            return;
        }

        const audioContext = new AudioContext();
        const audioBuffer = await audioContext.decodeAudioData(await recordedBlob.arrayBuffer());
        const wav = toWav(audioBuffer);
        const blob = new Blob([new Uint8Array(wav)], { type: 'audio/wav' });

        // console.log("blob:", blob);

        const formData = new FormData();
        formData.append('file', blob);
    
        try {
            await axios.post(`${baseUrl}/message/${messageId}/file`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    Authorization: `Bearer ${accessToken}`
                }
            });
            // console.log(response.data);
            window.scrollTo(0, 0);
            navigate('/');
        } catch (error) {
            // console.error(error);
            Swal.fire({
              icon: 'error',
              title: '실패',
              text: '음성 전송에 실패했습니다.',
            });
            window.scrollTo(0, 0);
            navigate('/');
        }
      } else if (messageId === null) {
        if (!recordedBlob) {
          return;
        }

        const audioContext = new AudioContext();
        const audioBuffer = await audioContext.decodeAudioData(await recordedBlob.arrayBuffer());
        const wav = toWav(audioBuffer);
        const blob = new Blob([new Uint8Array(wav)], { type: 'audio/wav' });

        // console.log("blob:", blob);

        const formData = new FormData();
        formData.append('file', blob);
    
        try {
            const response = await axios.post(`${baseUrl}/message/file`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    Authorization: `Bearer ${accessToken}`
                }
            })
            // console.log(response.data);
            if (response.data.data.randomBoxProvided === true) {
                Toast.fire({
                    icon: 'success',
                    title: '랜덤 박스에 당첨되었습니다!',
                });
            }
            window.scrollTo(0, 0);
            navigate('/');
        } catch (error) {
            // console.error(error);
            Swal.fire({
              icon: 'error',
              title: '실패',
              text: '음성 전송에 실패했습니다.',
            });
            window.scrollTo(0, 0);
            navigate('/');
        }
      }
    };

  return (
    <div className={styles.sendVoiceComponent}>
      <div className={styles.sendVoiceComponentHeader}>
        <div className={styles.sendVoiceComponentHeaderText}>
          <p className={styles.sendVoiceComponentHeaderTextTitle}>익명 메세지</p>
          <p className={styles.sendVoiceComponentHeaderTime}></p>
        </div>
        <div className={styles.sendVoiceComponentHeaderIcons}>
          <img src={back} alt="back-icon" />
          <img src={front} alt="front-icon" />
          <img src={re} alt="re-icon" />
          <div className={styles.sendVoiceComponentHeaderSearchArea}></div>
          <img src={star} alt="star-icon" />
        </div>
      </div>
      <div className={styles.sendVoiceComponentBody}>
        <div className={styles.sendVoiceComponentBodyMain}>
          <div className={styles.sendVoiceComponentBodyRecordingTime}>
            {(() => {
              if (currentRecordType==="cleared") {
                return (
                  <p className={styles.sendVoiceComponentBodyRecordingTimeTitle}>녹음을 시작해주세요</p>
                )
              } else if (currentRecordType==="recording") {
                return (
                  <p className={styles.sendVoiceComponentBodyRecordingTimeTitle}>{formattedRecordingTime}</p>
                )
              } else if (currentRecordType==="audio") {
                return (
                  <p className={styles.sendVoiceComponentBodyRecordingTimeTitle}>녹음이 완료되었습니다</p>
                )
              }
            })()}
          </div>
          {isCleared ? 
            <div className={styles.sendVoiceComponentBodyMainSvg}>
              <img src={defaultSvg} alt="default-icon" />
            </div>
            :
            <div className={styles.sendVoiceComponentBodyRecorder}>
              <VoiceVisualizer 
                ref={audioRef} 
                controls={recorderControls}
                width="100%"
                height="100px"
                backgroundColor="transparent"
                mainBarColor="#ffffff"
                barWidth={4}
                gap={1}
                speed={1}
                isDefaultUIShown={false}
                isControlPanelShown={false}
              />
            </div>
          }
          <div className={styles.sendVoiceComponentBodyMainButtons}>
            {(() => {
              if (currentRecordType==="cleared") {
                return (
                  <img src={micbutton} alt="start-icon" onClick={recorderControls.startRecording}/>
                )
              } else if (currentRecordType==="recording") {
                return (
                  <img src={stopbutton} alt="stop-icon" onClick={recorderControls.stopRecording}/>
                )
              } else if (currentRecordType==="audio") {
                return (
                  <>
                    <img 
                      src={postbutton} 
                      alt="post-icon" 
                      onClick={handlePostButtonClick}
                    />
                    {isPausedRecordedAudio ?
                      <img src={startbutton} alt="start-icon" onClick={recorderControls.togglePauseResume}/>
                      :
                      <img src={pausebutton} alt="pause-icon" onClick={recorderControls.togglePauseResume}/>
                    }
                    <img src={repeatbutton} alt="repeat-icon" onClick={recorderControls.clearCanvas}/>  
                  </>
                )
              }
            })()}

          </div>
        </div>
      </div>
    </div>
  )
}

export default SendVoiceComponent