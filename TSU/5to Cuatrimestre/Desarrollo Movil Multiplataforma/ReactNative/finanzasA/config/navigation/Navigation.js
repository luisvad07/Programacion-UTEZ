import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Icon } from "@rneui/base";
import ProfileStack from "../stack/ProfileStack";
import AboutStack from "../stack/AboutStack";
const Tab = createBottomTabNavigator();
export default function Navigation() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        initialRouteName="profile"
        screenOptions={({ route }) => ({
          tabBarIcon: ({ color }) => screenOptions(route, color),
          tabBarActiveTintColor: "tomato",
          tabBarInactiveTintColor: "gray",
          headerShown: false,
        })}
      >
        <Tab.Screen
          name="profile"
          options={{ title: "Perfil" }}
          component={ProfileStack}
        />
        <Tab.Screen
          name="about"
          options={{ title: "Conócenos" }}
          component={AboutStack}
        />
      </Tab.Navigator>
    </NavigationContainer>
  );
}
const screenOptions = (route, color) => {
  let iconName;
  switch (route.name) {
    case "profile":
      iconName = "account";
      break;
    case "about":
      iconName = "information";
      break;
  }
  return (
    <Icon type="material-community" name={iconName} size={22} color={color} />
  );
};
