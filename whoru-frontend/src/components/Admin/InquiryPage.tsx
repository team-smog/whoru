type InquiryData = {
  id:number;
  content:string;
  date:string;
}

const InquiryPage = () => {
  const dummies:InquiryData[] = [
    {
      id: 1,
      content:'[#1] 회원탈퇴가 안됩니다.',
      date: '2024.04.24',
    },
    {
      id: 2,
      content: '[#2] 메세지가 한 통도 오지 않아요..!',
      date: '2024.04.25'
    }
  ]

  return (
    <div className="w-full max-w-[500px] px-8 py-2 border-text_color border-b-[0.5px] text-text_color">
      {dummies.map((item, id) => (
        <div key={id} className="py-2">
          <div className="text-[14px]">{item.content}</div>
          <div className="text-[10px] text-gray-400">{item.date}</div>
        </div>
      ))}
    </div>
  )
}

export default InquiryPage;