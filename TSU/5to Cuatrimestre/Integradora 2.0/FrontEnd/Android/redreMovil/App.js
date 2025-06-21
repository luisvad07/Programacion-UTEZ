import { LogBox, StyleSheet} from 'react-native';
import Navigation from './config/navigation/Navigation';
import React, { useEffect, useState } from 'react'
import Login from './modules/auth/adapters/screens/Login'
//import { useNavigation } from '@react-navigation/native'
import { getAuth, onAuthStateChanged } from 'firebase/auth'
import { app } from './config/utils/firebase';
import AsyncStorage from '@react-native-async-storage/async-storage';

LogBox.ignoreAllLogs(true);  

export default function App() {
  //const navigation = useNavigation(); 
    const [user, setUser] = useState(null)
    const [session, setSession] = useState(null)
    const [show, setShow] = useState(true)

    useEffect(() => {
        const auth = getAuth();

        onAuthStateChanged(auth, (credential) =>{
            setUser(credential);
            !credential ? setSession(false): setSession(true)
        });
    }, [])

    const retrieveData = async () => {
      try {
        const correo = await AsyncStorage.getItem('sessionId');
      } catch (error) {
        console.log(error);
      }
    };

    if (session) {
      return <Navigation/>
    } else {
      return <Login texto={"Cerrando sesión"} />
    }
    //return user ? <Navigation user={user}/> : <Login texto={"Cerrando sesión"} navigation={navigation} />
}