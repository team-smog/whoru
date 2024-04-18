import './App.css';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import MainPage from './pages/MainPage/MainPage';
import LoginPage from './pages/Login/LoginPage';
import AlarmPage from './pages/Alarm/AlarmPage';
import ProfilePage from './pages/Profile/ProfilePage';
import AskPage from './pages/Ask/AskPage';
import PostPage from './pages/Post/PostPage';
import AskPostPage from './pages/AskPost/AskPostPage';

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
    path: '/post',
    element: <PostPage />
  }
])

function App() {
  return (
    <>
      <RouterProvider router={router} />
    </>
  )
};

export default App;