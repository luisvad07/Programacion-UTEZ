import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import Feed from '../../modules/profile/adapters/screens/Feed'

const Stack = createNativeStackNavigator()

export default function FeedStack() {
    return (
        <Stack.Navigator
            screenOptions={{
                headerMode: 'screen',
                headerTintColor: 'white',
                headerStyle: { backgroundColor: '#1728D6' }
            }}>
            <Stack.Screen
                name='feedStack'
                options={{ title: 'Perfil' }}
                component={ Feed }
            />
        </Stack.Navigator>
    )
}