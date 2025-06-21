import React from 'react'
import { NavigationContainer } from '@react-navigation/native'
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs'
import { Icon } from '@rneui/base'
import ProfileStack from '../stack/ProfileStack'
import FeedStack from '../stack/FeedStack'

const Tab = createBottomTabNavigator()
export default function Navigation() {
    return (
        <NavigationContainer>
            <Tab.Navigator
                screenOptions={({ route }) => ({
                    headerShown:false,
                    tabBarIcon: ({ color }) => screenOptions(route, color),
                    tabBarActiveTintColor: '#6CCBB2', tabBarInactiveTintColor: 'gray',
                })}>
                <Tab.Screen
                    name='profile'
                    options={{ title: 'Perfil'}}
                    component={ProfileStack} //que vista esperamos que se renderice cuando el usuario le de click a una opción
                />
                <Tab.Screen
                    name='feed'
                    options={{ title: 'Reportes de Estadías' }}
                    component={FeedStack} //que vista esperamos que se renderice cuando el usuario le de click a una opción
                />
            </Tab.Navigator>
            
        </NavigationContainer>
    )
}

const screenOptions = (route, color) => {
    let iconName;
    switch (route.name) {
        case 'profile':
            iconName = 'account-outline';
            break;
        case 'feed':
            iconName = 'file-document-outline';
            break;   
    }
    return (
        <Icon type='material-community' name={iconName} size={30} color={color} />
    )
}
