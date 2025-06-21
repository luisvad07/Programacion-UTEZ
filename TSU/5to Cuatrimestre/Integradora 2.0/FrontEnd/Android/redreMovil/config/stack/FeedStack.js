import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import Feed from '../../modules/profile/adapters/screens/Feed'

const Stack = createNativeStackNavigator()

export default function FeedStack() {
    return (
        <Stack.Navigator
            screenOptions={{
                headerShown: false,
                headerMode: 'screen',
                headerTintColor: 'white',
                headerStyle: { backgroundColor: '#6CCBB2' }
            }}>
            <Stack.Screen
                name='feedStack'
                options={{ title: 'Reportes de Estadías' }}
                component={ Feed }
            />
        </Stack.Navigator>
    )
}