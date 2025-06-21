import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import Loading from '../../../../kernel/components/Loading'
import UserGuest from './UserGuest'
import Login from '../../../auth/adapters/screens/Login'
import UserLogged from './UserLogged'
import { useNavigation } from '@react-navigation/native'
import { getAuth, onAuthStateChanged } from 'firebase/auth'


export default function Profile() {
    const navigation = useNavigation(); 

    const [user, setUser] = useState(null)
    const [session, setSession] = useState(null)
    const [show, setShow] = useState(true)

    useEffect(() => {
        const auth = getAuth();

        onAuthStateChanged(auth, (credential) =>{
            console.log("credencial", credential)
            setUser(credential);
            !credential ? setSession(false): setSession(true)
        });
        setShow(false)
    }, [])

    return user ? <UserLogged user={user}/> : <Login texto={"Cerrando sesión"} navigation={navigation} />
}

const styles = StyleSheet.create({})