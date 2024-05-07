import { initializeApp } from 'firebase/app'
import { getToken, getMessaging } from 'firebase/messaging'

const firebaseConfig = {
	apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
	authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
	projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
	storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
	messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
	appId: import.meta.env.VITE_FIREBASE_APP_ID,
	measurementId: import.meta.env.VITE_FIREBASE_MEASUREMENT_ID,
}


// Initialize Firebase
const app = initializeApp(firebaseConfig)
const messaging = getMessaging(app)

const requestPermission = async () => {
	const permission = await Notification.requestPermission()
	if (permission === 'denied') {
		console.log('알림받기가 꺼져있습니다.')
		return ''
	}

	const token = await getToken(messaging, {
    vapidKey: import.meta.env.VITE_FIREBASE_VAPID_KEY,
})

	if (token) {
		console.log('token:', token)
		console.log(token)
		// 토큰 잘 받아오는지 체크하는 console 나중에 꼭 지울것!!
		return token
	} else {
		return ''
	}
}

function getFirebaseMessagingObject() {
	return messaging
}

export { requestPermission, getFirebaseMessagingObject }
