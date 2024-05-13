importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js')

self.addEventListener('notificationclick', (e) => {
	const { content, title, type } = e.notification.data
	if (type == 'MESSAGE') {
		clients.openWindow('https://k10d203.p.ssafy.io/')
	} else {
		clients.openWindow('https://k10d203.p.ssafy.io/announcement')
	}
})

firebase.initializeApp({
	apiKey: 'AIzaSyBih65GoaQRf_RbFYM3XYNDb0WjJQj6xhc',
	authDomain: 'whoru-29e56.firebaseapp.com',
	projectId: 'whoru-29e56',
	storageBucket: 'whoru-29e56.appspot.com',
	messagingSenderId: '1051109766348',
	appId: '1:1051109766348:web:dbc586010d56e55b0bfe69',
	measurementId: 'G-16THNFKQN9',
})

const messaging = firebase.messaging()

messaging.onBackgroundMessage((payload) => {
	// 백그라운드 메세지 핸들러, 백그라운드 메세지는 Service-worker에서만 작동함!
	console.log('payload : ', payload)
	// const notificationTitle = 'Background Message Title'; // 메세지 제목
	const { content, title } = payload.data
	const notificationOptions = {
		body: content, // 매세지 내용
		icon: '/firebase-logo.png', // 로고 이미지 들어가는곳
		data: payload.data,
	}

	self.registration.showNotification(title, notificationOptions)
})
