import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useState } from 'react'
import Loading from '../../../../kernel/components/Loading'
import UserGuest from './UserGuest'
import Login from '../../../auth/adapters/screens/Login'
import UserLogged from './UserLogged'
import { useNavigation } from '@react-navigation/native'
import { getAuth, onAuthStateChanged } from 'firebase/auth'
import Home from './Home'


export default function Feed() {
    const navigation = useNavigation(); 

    const [user, setUser] = useState(null)
    const [session, setSession] = useState(null)
    const [show, setShow] = useState(false)

    useEffect(() => {
        const auth = getAuth();

        onAuthStateChanged(auth, (credential) =>{
            setUser(credential);
            !credential ? setSession(false): setSession(true)
        });
        setShow(false)
    }, [])

    return <Home user={user}/>
}

const styles = StyleSheet.create({})