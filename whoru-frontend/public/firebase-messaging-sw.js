importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js');

firebase.initializeApp({
    apiKey: "AIzaSyBih65GoaQRf_RbFYM3XYNDb0WjJQj6xhc",
    authDomain: "whoru-29e56.firebaseapp.com",
    projectId: "whoru-29e56",
    storageBucket: "whoru-29e56.appspot.com",
    messagingSenderId: "1051109766348",
    appId: "1:1051109766348:web:dbc586010d56e55b0bfe69",
    measurementId: "G-16THNFKQN9"
});

const messaging = firebase.messaging();


messaging.onBackgroundMessage((payload) => {
    // 백그라운드 메세지 핸들러, 백그라운드 메세지는 Service-worker에서만 작동함!    
    const notificationTitle = 'Background Message Title'; // 메세지 제목
    const notificationOptions = {
        body: payload.body, // 매세지 내용
        icon: '/firebase-logo.png' // 로고 이미지 들어가는곳
    };
    self.registration.showNotification(notificationTitle, notificationOptions);
});