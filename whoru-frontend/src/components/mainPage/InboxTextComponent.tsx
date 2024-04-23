import axios from 'axios'
import React, { useEffect,useState } from 'react'

const InboxTextComponent = () => {
  const [content, setContent] = useState<string>("")

  // 일단 랜덤 글 불러오기
  // TODO: 백엔드와 연동하여 실제 데이터를 받아오도록 수정
  useEffect(() => {
    axios.get('https://api.chucknorris.io/jokes/random')
    .then((res) => {
      console.log(res.data.value)
      setContent(res.data.value)
    })
    .catch((err) => {
      console.log(err)
    })
  }, [])

  return (
    <div className="">
      <div className="m-4 text-xs">
        <div className="flex flex-1 justify-start items-center">
          <h3 className="font-bold text-base">익명 이름</h3>
          <p className="mx-3 text-gray-400">1분 전</p>
        </div>
        <div className="mx-5 mt-3 mb-4">
          <p className="break-keep ">{content}</p>
        </div>
        <div className="flex flex-1 justify-around items-conter mb-3">
          <button>답장하기</button>
          <button>번역보기</button>
          <button className="text-gray-400">신고</button>
        </div>
      </div>
      <hr/>
    </div>
  )
}

export default InboxTextComponent