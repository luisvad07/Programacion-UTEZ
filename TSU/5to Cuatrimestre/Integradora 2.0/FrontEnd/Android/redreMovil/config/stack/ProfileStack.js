import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import Profile from '../../modules/profile/adapters/screens/Profile'
import Login from '../../modules/auth/adapters/screens/Login'
const Stack = createNativeStackNavigator()

export default function ProfileStack() {
    return (
        <Stack.Navigator
            screenOptions={{
                headerShown: false,
                headerMode: 'screen',
                headerTintColor: 'white', headerStyle: {backgroundColor: '#6CCBB2' }
            }}>
            <Stack.Screen
                name='profileStack'
                options={{ title: 'Perfil'}}
                component={Profile}
            />
  
            <Stack.Screen
                name='loginStack'
                options={{ title: 'Perfil' }}
                component={Login} 
            />
        </Stack.Navigator>
    )
}