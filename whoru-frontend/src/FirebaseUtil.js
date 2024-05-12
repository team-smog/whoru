import { initializeApp } from 'firebase/app'
import { getToken, getMessaging } from 'firebase/messaging'

const firebaseConfig = {
	apiKey: 'AIzaSyBih65GoaQRf_RbFYM3XYNDb0WjJQj6xhc',
	authDomain: 'whoru-29e56.firebaseapp.com',
	projectId: 'whoru-29e56',
	storageBucket: 'whoru-29e56.appspot.com',
	messagingSenderId: '1051109766348',
	appId: '1:1051109766348:web:dbc586010d56e55b0bfe69',
	measurementId: 'G-16THNFKQN9',
}
// Initialize Firebase
const app = initializeApp(firebaseConfig)
const messaging = getMessaging(app)

const requestPermission = async () => {
	const permission = await Notification.requestPermission()
	if (permission !== 'granted') {
		console.log('알림받기가 꺼져있습니다.')
		return ''
	}
	let token = ''
	try {
		token = await getToken(messaging, {
			vapidKey: 'BOJrA8KgnUI2pEEzLEfsVNjlEPB9vmvh_0Km-lsp714IRV9cgwgc5mi3ynx5uCBHzhbiPeMJ8v9_EH9oP_x_Emc',
		})
	} catch (err) {
		token = await getToken(messaging, {
			vapidKey: 'BOJrA8KgnUI2pEEzLEfsVNjlEPB9vmvh_0Km-lsp714IRV9cgwgc5mi3ynx5uCBHzhbiPeMJ8v9_EH9oP_x_Emc',
		})
	}

	localStorage.setItem('FCMToken', token)
	console.log('fcm token:', token)
	return token
}

function getFirebaseMessagingObject() {
	return messaging
}

export { requestPermission, getFirebaseMessagingObject }
