import Header, { IHeaderInfo } from '@/components/@common/Header'
import AdminLogin from '@/components/Admin/AdminPage/AdminLogin'

const AdminLoginPage = () => {
	const info: IHeaderInfo = {
		left_1: 'Admin',
		left_2: null,
		center: null,
		right: null,
	}

	return (
		<>
			<Header info={info} />
			<div className="h-screen bg-white pt-10">
				<AdminLogin />
			</div>
		</>
	)
}

export default AdminLoginPage
