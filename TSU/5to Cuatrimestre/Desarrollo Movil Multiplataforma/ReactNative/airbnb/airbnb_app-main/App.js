import React, { useEffect, useState } from 'react'
import Navigation from './config/navigation/Navigation'
import NavigationLogged from './config/navigation/NavigationLogged'
import { app } from './config/firebase'
import Loading from './kernel/components/Loading'
import { getAuth } from 'firebase/auth/react-native'
import { onAuthStateChanged } from 'firebase/auth'

export default function App() {
  const [user, setUser] = useState(null)
  const [session, setSession] = useState(null)
  useEffect(() => {
    const auth = getAuth()
    onAuthStateChanged(auth, (credential) => {
      setUser(credential)
      console.log('User', user);
      !credential ? setSession(false) : setSession(true)
    })
  }, [])
  if (session == null) return <Loading show={true} text='Cargando' />
  return session ? <NavigationLogged /> : <Navigation/>
}