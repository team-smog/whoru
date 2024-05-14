import ReportItems from './ReportItems'
import { useIntersectionObserver } from '@/hooks/useIntersectionObserver'
import { useReportDetail } from '@/hooks/Admin/useAdmin'

// const categories = ['전체', '비속어', '음란', '스팸', '광고'];

const Report = () => {
  const { data: reportData, fetchNextPage, hasNextPage } = useReportDetail();
  const { setTarget } = useIntersectionObserver({ fetchNextPage, hasNextPage });
  
	// const [selectedCategory, setSelectedCategory] = useState<string>('전체')
	// const [selectedReport, setSelectedReport] = useState<ReportInfo | null>(null);

	// // 신고내역 필터링
	// const filteredData = data.filter(item => {
	//   if (selectedCategory === '전체') return true;
	//   return item.report_type === selectedCategory;
	// });



	return (
		<>
			<div className="w-full max-w-[500px]">
				<div className="h-[calc(100vh-180px)] overflow-y-auto">
					{reportData &&
						reportData.pages.map((page) =>
							page.content.map((item, index) => {
								return <ReportItems key={index} data={item}/>
							})
						)}
        <div ref={setTarget} className='h-[2rem]'></div>
				</div>
			</div>
		</>
	)
}

export default Report;
