import React from 'react'

const InboxImageComponent = () => {
  return (
    <div className="">
      <div className="m-4 text-xs">
        <div className="flex flex-1 justify-start items-center">
          <h3 className="font-bold text-base">익명 이름</h3>
          <p className="mx-3 text-gray-400">1분 전</p>
        </div>
        <div className="mx-5 mt-3 mb-4 flex justify-center">
          {/* 
            일단 랜덤 사진 불러오기
            TODO: 백엔드와 연동하여 실제 데이터를 받아오도록 수정
            React Query 사용
          */}
          <img 
            className='min-w-[100px] max-w-[360px] min-h-[100px] max-h-[500px] rounded-lg shadow-[0_4px_8px_-4px_rgba(0,0,0,0.3)]'
            src="https://source.unsplash.com/random"
            alt="이미지"
          />
        </div>
        <div className="flex flex-1 justify-around items-conter mb-3">
          <button>답장하기</button>
          <button className="text-gray-400">신고</button>
        </div>
      </div>
      <hr/>
    </div>
  )
}

export default InboxImageComponent