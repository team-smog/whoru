import './App.css'
import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom'
import MainPage from './pages/MainPage/MainPage'
import PostBoxPage from './pages/PostBoxPage/PostBoxPage'
import TodayMessagesPage from './pages/TodayMessages/TodayMessagesPage'
import LoginPage from './pages/Login/LoginPage'
import ProfilePage from './pages/Profile/ProfilePage'
import PostPage from './pages/Post/PostPage'
import AnnouncementPage from './pages/Announcement/AnnouncementPage'
import AnnouncementDetail from './components/Announcement/AnnouncementDetail'
import Chacollection from './pages/Chacollection/Chacollection'
import Inquiry from './pages/Inquiry/Inquiry'
import InquiryDetail from './components/Inquiry/InquiryDetail'
import AdminPage from './pages/Admin/AdminPage'
import CallBackPage from './pages/Login/CallBackPage'
import CreateInquiry from './components/Inquiry/CreateInquiry'
import NotFound from './pages/NotFound/NotFound'
import AdminLoginPage from './pages/Admin/AdminLoginPage'
import { useSelector } from 'react-redux'

interface AuthWrapperProps {
	children: React.ReactNode
}

interface AdminAuthWrapperProps {
	children: React.ReactNode
}

const AuthWrapper: React.FC<AuthWrapperProps> = ({ children }) => {
	const accessToken = localStorage.getItem('AccessToken')

	if (accessToken == null) {
		return <Navigate to="/login" replace />
	}

	return <>{children}</>
}

const AdminAuthWrapper: React.FC<AdminAuthWrapperProps> = ({ children }) => {
	const role = useSelector((state: any) => state.user.role)

	if (role !== 'ROLE_ADMIN') {
		return <Navigate to="/admin/login" replace />
	}

	return <>{children}</>
}

const router = createBrowserRouter([
	{
		path: '/mymessage',
		element: (
			<AuthWrapper>
				<MainPage />
			</AuthWrapper>
		),
	},
	{
		path: '/',
		element: (
			<AuthWrapper>
				<PostBoxPage />
			</AuthWrapper>
		),
	},
	{
		path: '/daily',
		element: (
			<AuthWrapper>
				<TodayMessagesPage />
			</AuthWrapper>
		),
	},
	{
		path: '/login',
		element: <LoginPage />,
	},
	{
		path: '/profile',
		element: (
			<AuthWrapper>
				<ProfilePage />
			</AuthWrapper>
		),
	},
	{
		path: '/announcement',
		element: (
			<AuthWrapper>
				<AnnouncementPage />
			</AuthWrapper>
		),
	},
	{
		path: '/announcement/:id',
		element: (
			<AuthWrapper>
				<AnnouncementDetail />
			</AuthWrapper>
		),
	},
	{
		path: '/chacollection',
		element: (
			<AuthWrapper>
				<Chacollection />
			</AuthWrapper>
		),
	},
	{
		path: '/inquiry',
		element: (
			<AuthWrapper>
				<Inquiry />
			</AuthWrapper>
		),
	},
	{
		path: '/inquiry/:id',
		element: (
			<AuthWrapper>
				<InquiryDetail />
			</AuthWrapper>
		),
	},
	{
		path: '/inquiry/create',
		element: (
			<AuthWrapper>
				<CreateInquiry />
			</AuthWrapper>
		),
	},
	{
		path: 'inquiry/detail',
		element: (
			<AuthWrapper>
				<InquiryDetail />
			</AuthWrapper>
		),
	},
	{
		path: '/post',
		element: (
			<AuthWrapper>
				<PostPage />
			</AuthWrapper>
		),
	},
	{
		path: '/admin',
		element: (
			<AdminAuthWrapper>
				<AdminPage />
			</AdminAuthWrapper>
		),
	},
	{
		path: '/callback',
		element: <CallBackPage />,
	},
	{
		path: '/admin/login',
		element: (
				<AdminLoginPage />
		),
	},
	{
		path: '*',
		element: <NotFound />,
	},
])

const App = () => {
	return (
		<>
			<RouterProvider router={router} />
		</>
	)
}

export default App
