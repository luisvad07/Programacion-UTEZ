import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import Profile from '../../modules/profile/adapters/screens/Profile'
import Login from '../../modules/auth/adapters/screens/Login'
import CreateUser from '../../modules/user/CreateUser'
const Stack = createNativeStackNavigator()

export default function ProfileStack() {
    return (
        <Stack.Navigator
            screenOptions={{
                headerMode: 'screen',
                headerTintColor: 'white', headerStyle: {backgroundColor: '#A1278B' }
            }}>
            <Stack.Screen
                name='profileStack'
                options={{ title: 'Perfil' }}
                component={Profile}
            />
  
            <Stack.Screen
                name='loginStack'
                options={{ title: 'Inicio de sesión' }}
                component={Login} 
            />
            <Stack.Screen
                name='createUserStackt'
                options={{ title: 'Crea tu cuenta' }}
                component={CreateUser} 
            />
        </Stack.Navigator>
    )
}