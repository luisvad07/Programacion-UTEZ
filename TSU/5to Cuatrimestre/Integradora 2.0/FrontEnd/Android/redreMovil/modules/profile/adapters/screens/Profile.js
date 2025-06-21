import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import Loading from '../../../../kernel/components/Loading'
import UserGuest from './UserGuest'
import Login from '../../../auth/adapters/screens/Login'
import UserLogged from './UserLogged'
import { useNavigation } from '@react-navigation/native'
import { getAuth, onAuthStateChanged } from 'firebase/auth'
import axios from 'axios'
import AsyncStorage from '@react-native-async-storage/async-storage'


export default function Profile() {
    const navigation = useNavigation(); 
    const backIP = "http://192.168.43.234:8080/redre/estudiante/";

    const [user, setUser] = useState(null)
    const [session, setSession] = useState(null)
    const [show, setShow] = useState(true)

    useEffect(() => {
        traerUser();
        const auth = getAuth();
        onAuthStateChanged(auth, (credential) =>{
            setUser(credential);
            !credential ? setSession(false): setSession(true)
        });
    }, [])

    const traerUser = async () => {
        const sessionId = await AsyncStorage.getItem('sessionId');
        axios.get(`${backIP}${sessionId}`).then((response) => {
            const usuario = response.data.obj;
            setUser(usuario);
        });
    }

    return user ? <UserLogged user={user}/> : <Login texto={"Cerrando sesión"} navigation={navigation} />
}

const styles = StyleSheet.create({})