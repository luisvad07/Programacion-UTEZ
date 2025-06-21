import React from 'react'
import { NavigationContainer } from '@react-navigation/native'
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs'
import { Icon } from '@rneui/base'
import HomeStack from '../stack/HomeStack'
import ProfileStack from '../stack/ProfileStack'
import RentStack from '../stack/RentStack'

const Tab = createBottomTabNavigator()
export default function NavigationLogged() {
    return (
        <NavigationContainer>
            <Tab.Navigator
                initialRouteName='home'
                screenOptions={({ route }) => ({
                    tabBarIcon: ({ color }) => screenOptions(route, color),
                    tabBarActiveTintColor: 'tomato',
                    tabBarInactiveTintColor: 'gray',
                    headerShown:false
                })}>
                    <Tab.Screen
                        name='home'
                        component={HomeStack}
                        options={{title: 'Home'}}
                    />
                    <Tab.Screen
                        name='rentas'
                        component={RentStack}
                        options={{title: 'rentas'}}
                    />
                    <Tab.Screen
                        name='perfil'
                        component={ProfileStack}
                        options={{title: 'Perfil'}}
                    />

            </Tab.Navigator>
        </NavigationContainer>
    )
}

const screenOptions = (route, color) => {
    let iconName;
    switch (route.name) {
        case 'home':
            iconName = 'home-circle-outline'
            break;
        case 'rentas':
            iconName = 'cash'
            break
        case 'perfil':
            iconName = 'account'
        default:
            break;
    }
    return (<Icon type='material-community'
        name={iconName}
        size={22}
        color={color}></Icon>)
}