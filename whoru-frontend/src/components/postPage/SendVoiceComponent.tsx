import { useEffect, useState } from 'react'
import styles from './SendVoiceComponent.module.css'
// import Header from '@/assets/components/InboxVoiceComponent/voice-component-header.svg'
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
import { setBoxCountP } from '@/stores/store';
import { useDispatch } from 'react-redux';
import { createFFmpeg, fetchFile } from '@ffmpeg/ffmpeg';

const SendVoiceComponent = ({ messageId }: { messageId: number | null}) => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [currentRecordType,setCurrentRecordType] = useState<string>("")
    // Initialize the recorder controls using the hook
    const recorderControls = useVoiceVisualizer();
    const accessToken = localStorage.getItem('AccessToken');
    const ffmpeg = createFFmpeg();
    const {
        // ... (Extracted controls and states, if necessary)
        // error,
        audioRef,
        isRecordingInProgress,
        // isPausedRecording,
        // audioData,
        // recordingTime,
        // mediaRecorder,
        // duration,
        // currentAudioTime,
        // audioSrc,
        isPausedRecordedAudio,
        // isProcessingRecordedAudio,
        isCleared,
        isAvailableRecordedAudio,
        recordedBlob,
        // bufferFromRecordedBlob,
        // formattedDuration,
        formattedRecordingTime,
        // formattedRecordedAudioCurrentTime,
        // isProcessingOnResize,
        // isProcessingStartRecording,
    } = recorderControls;

    // Get the recorded audio blob
    // useEffect(() => {
    //   if (!recordedBlob) return;

    //   console.log("recordedBlob:", recordedBlob);
    // }, [recordedBlob, error]);

    // Get the error when it occurs
    // useEffect(() => {
    //   if (!error) return;

    //   console.error("error:", error);
    // }, [error]);

    useEffect(() => {
      if (!isRecordingInProgress) return;

      if (isRecordingInProgress===true) {
        setCurrentRecordType("recording")
      }

      console.log("isRecordingInProgress:", isRecordingInProgress);
    }, [isRecordingInProgress]);

    // useEffect(() => {
    //   if (!isPausedRecording) return;

    //   console.log("isPausedRecording:", isPausedRecording);
    // }, [isPausedRecording]);

    // useEffect(() => {
    //   if (!isPausedRecordedAudio) return;

    //   console.log("isPausedRecordedAudio:", isPausedRecordedAudio);
    // }, [isPausedRecordedAudio]);

    // useEffect(() => {
    //   if (!isProcessingRecordedAudio) return;

    //   console.log("isProcessingRecordedAudio:", isProcessingRecordedAudio);
    // }, [isProcessingRecordedAudio]);

    useEffect(() => {
      if (!isCleared) return;

      if (isCleared===true) {
        setCurrentRecordType("cleared")
      }

      // console.log("isCleared:", isCleared);
    }, [isCleared]);

    useEffect(() => {
      if (!isAvailableRecordedAudio) return;

      if (isAvailableRecordedAudio===true) {
        setCurrentRecordType("audio")
      }

      // console.log("isAvailableRecordedAudio:", isAvailableRecordedAudio);
    }, [isAvailableRecordedAudio]);

    // useEffect(() => {
    //   if (!isProcessingOnResize) return;

    //   console.log("isProcessingOnResize:", isProcessingOnResize);
    // }, [isProcessingOnResize]);

    // useEffect(() => {
    //   if (!isProcessingStartRecording) return;

    //   console.log("isProcessingStartRecording:", isProcessingStartRecording);
    // }, [isProcessingStartRecording]);

    // useEffect(() => {
    //   if (!audioData) return;

    //   console.log("audioData:", audioData);
    // }, [audioData]);

    // useEffect(() => {
    //   if (!recordingTime) return;

    //   console.log("recordingTime:", recordingTime);
    // }, [recordingTime]);

    // useEffect(() => {
    //   if (!mediaRecorder) return;

    //   console.log("mediaRecorder:", mediaRecorder);
    // }, [mediaRecorder]);

    // useEffect(() => {
    //   if (!duration) return;

    //   console.log("duration:", duration);
    // }, [duration]);

    // useEffect(() => {
    //   if (!currentAudioTime) return;

    //   console.log("currentAudioTime:", currentAudioTime);
    // }, [currentAudioTime]);

    // useEffect(() => {
    //   if (!audioSrc) return;

    //   console.log("audioSrc:", audioSrc);
    // }, [audioSrc]);

    // useEffect(() => {
    //   if (!formattedDuration) return;

    //   console.log("formattedDuration:", formattedDuration);
    // }, [formattedDuration]);

    // useEffect(() => {
    //   if (!formattedRecordingTime) return;

    //   console.log("formattedRecordingTime:", formattedRecordingTime);
    // }, [formattedRecordingTime]);

    // useEffect(() => {
    //   if (!formattedRecordedAudioCurrentTime) return;

    //   console.log("formattedRecordedAudioCurrentTime:", formattedRecordedAudioCurrentTime);
    // }, [formattedRecordedAudioCurrentTime]);

    // useEffect(() => {
    //   if (!bufferFromRecordedBlob) return;

    //   console.log("bufferFromRecordedBlob:", bufferFromRecordedBlob);
    // }, [bufferFromRecordedBlob]);

    async function convertToMp3(inputFile:Blob) {
      await ffmpeg.load();
      ffmpeg.FS('writeFile', 'input', await fetchFile(inputFile));
      await ffmpeg.run('-i', 'input', 'output.mp3');
      const data = ffmpeg.FS('readFile', 'output.mp3');
      return new Blob([data.buffer], { type: 'audio/mp3' });
    }

    const handlePostButtonClick = async () => {
      if (messageId !== null) {
        if (!recordedBlob) {
            // console.error("No recorded audio to send");
            return;
        }
    
        // const formData = new FormData();
        // let newBlob = new Blob([recordedBlob], {type: 'audio/weba'});
        // formData.append('file', newBlob);

        const mp3Blob = await convertToMp3(recordedBlob);
        const formData = new FormData();
        formData.append('audio', mp3Blob);

        // console.log("newBlob:", newBlob);
    
        try {
            const response = await axios.post(`https://k10d203.p.ssafy.io/api/message/${messageId}/file`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    Authorization: `Bearer ${accessToken}`
                }
            });
            console.log(response.data);
            navigate('/');
        } catch (error) {
            console.error(error);
            alert('음성 전송에 실패했습니다.');
            navigate('/');
        }
      } else if (messageId === null) {
        if (!recordedBlob) {
          // console.error("No recorded audio to send");
          return;
        }
    
        // const formData = new FormData();
        // let newBlob = new Blob([recordedBlob], {type: 'audio/weba'});
        // formData.append('file', newBlob);

        const mp3Blob = await convertToMp3(recordedBlob);
        const formData = new FormData();
        formData.append('audio', mp3Blob);

        // console.log("newBlob:", newBlob);
    
        try {
            const response = await axios.post('https://k10d203.p.ssafy.io/api/message/file', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    Authorization: `Bearer ${accessToken}`
                }
            })
            console.log(response.data);
            if (response.data.data.randomBoxProvided === true) {
                dispatch(setBoxCountP());
            }
            navigate('/');
        } catch (error) {
            console.error(error);
            alert('음성 전송에 실패했습니다.');
            navigate('/');
        }
      }
    };

  return (
    <div className={styles.sendVoiceComponent}>
      {/* <img src={Header} alt="component-Header" className={styles.sendVoiceComponentHeader} /> */}
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
            {/* <p className={styles.sendVoiceComponentBodyRecordingTimeTimer}>{formattedRecordingTime}</p> */}
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