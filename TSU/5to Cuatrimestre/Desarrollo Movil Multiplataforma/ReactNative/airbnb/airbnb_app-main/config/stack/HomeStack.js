import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import Home from '../../modules/home/adapters/screens/Home'

const Stack = createNativeStackNavigator()

export default function () {
    return (
        <Stack.Navigator
            screenOptions={{
                headerMode: 'screen',
                headerTintColor: 'white',
                headerStyle: { backgroundColor: '#ff5a60' }
            }}>
            <Stack.Screen
                name='homeStack'
                options={{ title: 'Home' }}
                component={Home}
            />
        </Stack.Navigator>
    )
}
