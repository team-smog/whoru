import { useState, useRef } from 'react';

interface IModalProps {
  width: string;
  height: string;
  onClose: () => void;
  children: React.ReactNode;
}

const MessageModal = ({ width, height, onClose, children }: IModalProps) => {
  const modalRef = useRef<HTMLDivElement>(null);
  const [isRendering, setIsRendering] = useState<boolean>(true);

  const handleClose = () => {
    setIsRendering(false);
    setTimeout(() => {
      onClose();
    });
  };

  const handleModalClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === modalRef.current) {
      handleClose();
    }
  };

  return (
    <div className={`fixed top-0 left-1/2 transform -translate-x-1/2 w-[500px] bottom-0 ${isRendering ? 'bg-black/50' : 'bg-black/0'} z-50 `} ref={modalRef} onClick={handleModalClick}>
      <div
        ref={modalRef}
        className={`fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-lg bg-transparent ${isRendering ? 'animate-modalOn' : 'animate-modalOff'}`}
        onClick={handleModalClick}
      >
        <div style={{ width: width, height: height }}>
          {children}
        </div>
      </div>
    </div>
  );
};

export default MessageModal;