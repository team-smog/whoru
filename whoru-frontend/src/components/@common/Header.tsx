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

  const ToastMessage = Swal.mixin({
    toast: true,
    position: 'top',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('click', () => navigate('/mymessage'));
    }
  })

  const ToastAnnouncement = Swal.mixin({
    toast: true,
    position: 'top',
    showConfirmButton: false,
    timer: 3000,
    didOpen: (toast) => {
      toast.addEventListener('click', () => navigate('/announcement'));
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
          if (type == "RESPONSE_MESSAGE") {
            ToastMessage.fire({
              icon: 'success',
              title: content,
            })
          } else {
            ToastAnnouncement.fire({
              icon: 'success',
              title: content,
            })
          }
        }
      })
  }


  return(
    <div className="max-w-[500px] w-full z-[2] h-12 px-4 top-0 flex fixed justify-between items-center">
      <div className="flex flex-1 justify-start items-center">
        {left_1 && (
          <div className="w-13 text-[20px] text-text_color">
            <div >{left_1}</div>
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