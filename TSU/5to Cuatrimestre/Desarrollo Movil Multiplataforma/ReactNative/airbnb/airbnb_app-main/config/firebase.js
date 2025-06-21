// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import AsyncStorage from "@react-native-async-storage/async-storage";
import {initializeAuth, getReactNativePersistence} from 'firebase/auth/react-native'

const firebaseConfig = {
    apiKey: "AIzaSyCgrxp8l4K7-UO5hM5_U9tcculTI6ZODV8",
    authDomain: "airbnbapp-f665b.firebaseapp.com",
    projectId: "airbnbapp-f665b",
    storageBucket: "airbnbapp-f665b.appspot.com",
    messagingSenderId: "1045010910686",
    appId: "1:1045010910686:web:1db506a3cd15581993a14f"
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const auth = initializeAuth(app, {persistence: getReactNativePersistence(AsyncStorage)})
