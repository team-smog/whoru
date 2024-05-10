import React from 'react'

interface Inquiry {
	id: number
	subject: string
	content: string
}

interface InquiryListProps {
	inquiries: Inquiry[]
	handleDelete: (id: number) => void
}

const InquiryList: React.FC<InquiryListProps> = ({ inquiries, handleDelete }) => {
	return (
		<ul>
			{inquiries.map((inquiry) => (
				<li key={inquiry.id}>
					<strong>{inquiry.subject}</strong> - {inquiry.content}
					<button
						onClick={(e) => {
							e.stopPropagation()
							handleDelete(inquiry.id)
						}}
					>
						Delete
					</button>
				</li>
			))}
		</ul>
	)
}

export default InquiryList
