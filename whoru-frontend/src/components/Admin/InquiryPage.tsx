type InquiryData = {
  id:number;
  content:string;
  date:string;
}

const InquiryPage = () => {
  const dummies:InquiryData[] = [
    {
      id: 1,
      content:'[공지] 업데이트 사항',
      date: '2024.04.24',
    },
  ]

  return (
    <div>
      {dummies.map((item, id) => (
        <div key={id}>
          {item.content}
          {item.date}
        </div>
      ))}
    </div>
  )
}

export default InquiryPage;