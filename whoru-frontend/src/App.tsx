import './App.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
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

const router = createBrowserRouter([
  {
    path: '/',
    element: <MainPage />
  },
  {
    path: '/login',
    element: <LoginPage />
  },
  {
    path: '/alarm',
    element: <AlarmPage />
  },
  {
    path: '/profile',
    element: <ProfilePage />
  },
  {
    path: '/profile/ask',
    element: <AskPage />
  },
  {
    path: '/profile/ask/post',
    element: <AskPostPage />
  },
  {
    path : '/announcement',
    element: <AnnouncementPage />
  },
  {
    path : '/chacollection',
    element : <Chacollection />
  },
  {
    path : 'Inquiry',
    element : <Inquiry />
  },
  {
    path: '/post',
    element: <PostPage />
  },
  {
    path: '/admin',
    element: <AdminPage />
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