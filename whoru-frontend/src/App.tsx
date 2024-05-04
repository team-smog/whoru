import './App.css'
import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom';
import MainPage from './pages/MainPage/MainPage';
import LoginPage from './pages/Login/LoginPage';
import AlarmPage from './pages/Alarm/AlarmPage';
import ProfilePage from './pages/Profile/ProfilePage';
import AskPage from './pages/Ask/AskPage';
import PostPage from './pages/Post/PostPage';
import AskPostPage from './pages/AskPost/AskPostPage';
import AnnouncementPage from './pages/Announcement/AnnouncementPage';
import Chacollection from './pages/Chacollection/Chacollection';
import Inquiry from './pages/Inquiry/Inquiry';
import AdminPage from './pages/Admin/AdminPage';
import CallBackPage from './pages/CallBack/CallBackPage';

interface AuthWrapperProps {
  children: React.ReactNode;
}

const AuthWrapper: React.FC<AuthWrapperProps> = ({ children }) => {
  const accessToken = localStorage.getItem('AccessToken');

  if (!accessToken) {
    return <Navigate to='/login' replace />
  }

  return (
    <>
      {children}
    </>
  )
}

const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <AuthWrapper>
        <MainPage />
      </AuthWrapper>
    )
  },
  {
    path: '/login',
    element: <LoginPage />
  },
  {
    path: '/alarm',
    element: (
      <AuthWrapper>
        <AlarmPage />
      </AuthWrapper>
    )
  },
  {
    path: '/profile',
    element: (
      <AuthWrapper>
        <ProfilePage />
      </AuthWrapper>
    )
  },
  {
    path: '/profile/ask',
    element: (
      <AuthWrapper>
        <AskPage />
      </AuthWrapper>
    )
  },
  {
    path: '/profile/ask/post',
    element: (
      <AuthWrapper>
        <AskPostPage />
      </AuthWrapper>
    )
  },
  {
    path : '/announcement',
    element: (
      <AuthWrapper>
        <AnnouncementPage />
      </AuthWrapper>
    )
  },
  {
    path : '/chacollection',
    element : (
      <AuthWrapper>
        <Chacollection />
      </AuthWrapper>
    )
  },
  {
    path : 'Inquiry',
    element : (
      <AuthWrapper>
        <Inquiry />
      </AuthWrapper>
    )
  },
  {
    path: '/post',
    element: (
      <AuthWrapper>
        <PostPage />
      </AuthWrapper>
    )
  },
  {
    path: '/admin',
    element: (
      <AuthWrapper>
        <AdminPage />
      </AuthWrapper>
    )
  },
  {
    path: '/callback',
    element: (
      <AuthWrapper>
        <CallBackPage />
      </AuthWrapper>
    )
  }
])

const App = () => {
  return (
    <>
      <RouterProvider router={router} />
    </>
  )
};

export default App;