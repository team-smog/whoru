import { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { getFirebaseMessagingObject } from "@/FirebaseUtil.js";
import { onMessage } from "firebase/messaging";
import Swal from "sweetalert2";

export interface IHeaderInfo {
  left_1: React.ReactNode | null;
  left_2: JSX.Element | null;
  center: string | null;
  right: React.ReactNode | null;
}

const Header = (props: {info:IHeaderInfo}) => {
  const navigate = useNavigate();
  const messagingObject = useRef(null);

  const Toast = Swal.mixin({
    toast: true,
    position: 'center',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('mouseenter', Swal.stopTimer)
      toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
  })

  const {left_1, left_2, center, right} = props.info;

  useEffect(()=>{
    messagingObject.current = getFirebaseMessagingObject()
  },[])
  if(messagingObject.current !== null){
      // console.log(messagingObject.current);
      onMessage(messagingObject.current, (body)=>{
        if (body.data) {
          const { content, type } = body.data;
          let toast;
          if (type === 'message') {
            toast = Toast.fire({
              icon: 'success',
              title: content
            })
          } else {
            toast = Toast.fire({
              icon: 'success',
              title: content
            })
          }
      
          // Toast가 성공적으로 생성되었을 때 클릭 이벤트 리스너를 추가합니다.
          toast.then(result => {
            if (result.dismiss === Swal.DismissReason.timer) {
              console.log('I was closed by the timer')
            } else {
              // 여기에 클릭 이벤트 리스너를 추가합니다.
              result.value.addEventListener('click', () => {
                // 메시지의 타입에 따라 다른 경로로 이동합니다.
                if (type === 'message') {
                  navigate('/message');
                } else {
                  navigate('/other');
                }
              });
            }
          });
        }
      })
  }

  return(
    <div className="max-w-[500px] w-full z-[2] h-12 px-4 top-0 flex fixed justify-between items-center">
      <div className="flex flex-1 justify-start items-center">
        {left_1 && (
          <div className="w-13 text-[20px] text-text_color">
            <div>{left_1}</div>
          </div>
        )}
        {left_2 && (
          <button onClick={() => navigate(-1)}>
            <div className="w-4 h-4">{left_2}</div>
          </button>
        )}
      </div>
      <div className="flex-1 flex justify-center">{center && <p className="text-text_color">{center}</p>}</div>
      <div className="flex flex-1 justify-end items-center relative">
        <div>
          {right}
        </div>
      </div>
    </div>
  )
}

export default Header;