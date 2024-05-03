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
import ReceivePage from './pages/Login/ReceivePage';
import { useEffect, useRef, useState } from 'react';
import { getFirebaseMessagingObject } from "./FirebaseUtil"
import { onMessage } from "firebase/messaging";
import { requestPermission } from './FirebaseUtil';

import CallBackPage from './pages/CallBack/CallBackPage';

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
    path: '/receive',
    element: <ReceivePage />
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
  },
  {
    path: '/callback',
    element: <CallBackPage />
  }
])

const App = () => {

  const [token, setToken] = useState<string>("");
  useEffect(() => {
    const resultToken = requestPermission();
    resultToken.then((token) => {
      setToken(token);
    });
  }, []);

  const messagingObject = useRef(null);
  useEffect(()=>{
      messagingObject.current = getFirebaseMessagingObject()
    },[])
  if(messagingObject.current !== null){
    console.log(messagingObject.current)
      onMessage(messagingObject.current, (body)=>{
        if (body && body.notification) {
          console.log(body.notification.body);
          alert(body.notification.body);
        }
      })
  }


  useEffect(() => {
    navigator.serviceWorker.register('../public/firebase-messaging-sw.js')
      .then((registration) => {
        console.log('Service Worker registered with scope:', registration.scope);
      }).catch((err) => {
        console.error('Service Worker registration failed:', err);
      });
  }, []);


  return (
    <>
      <RouterProvider router={router} />
    </>
  )
};

export default App;