// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { initializeAuth, getReactNativePersistence } from "firebase/auth/react-native";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyDDEycpdt_MTTbHFkaHJdddYSxJcOoec_g",
    authDomain: "finanzas-ed9fa.firebaseapp.com",
    projectId: "finanzas-ed9fa",
    storageBucket: "finanzas-ed9fa.appspot.com",
    messagingSenderId: "334056049808",
    appId: "1:334056049808:web:9b654e16c0e539e6b8dc15"
  };

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const auth =  initializeAuth(app, {persistance : getReactNativePersistence(AsyncStorage)} )