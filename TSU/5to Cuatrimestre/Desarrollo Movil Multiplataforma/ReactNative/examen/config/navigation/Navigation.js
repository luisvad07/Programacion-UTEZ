import React from 'react'
import { NavigationContainer } from '@react-navigation/native'
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs'
import { Icon } from '@rneui/base'
import ProfileStack from '../stack/ProfileStack'
import FeedStack from '../stack/FeedStack'
import SearchStack from '../stack/SearchStack'
import CreateUser from '../../modules/user/CreateUser'

const Tab = createBottomTabNavigator()
export default function Navigation() {
    return (
        <NavigationContainer>
            <Tab.Navigator
                initialRouteName='profile'
                screenOptions={({ route }) => ({
                    tabBarIcon: ({ color }) => screenOptions(route, color),
                    tabBarActiveTintColor: 'green', tabBarInactiveTintColor: 'gray',
                    headerShown: false
                })}>
                <Tab.Screen
                    name='profile'
                    options={{ title: 'Perfil' }}
                    component={ProfileStack} //que vista esperamos que se renderice cuando el usuario le de click a una opción
                />
                <Tab.Screen
                    name='feed'
                    options={{ title: 'Inicio' }}
                    component={FeedStack} //que vista esperamos que se renderice cuando el usuario le de click a una opción
                />


                <Tab.Screen
                    name='search'
                    options={{ title: 'Buscar' }}
                    component={SearchStack}
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
            iconName = 'home';
            break;
        case 'search':
            iconName = 'magnify';
            break;    
    }
    return (
        <Icon type='material-community' name={iconName} size={30} color={color} />
    )
}
