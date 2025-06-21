import { Dimensions, StyleSheet, View, Text, Alert, Platform } from 'react-native'
import React, {useState, useEffect, useRef} from 'react'
import { Button, Icon, Divider } from "@rneui/base";
import * as Location from 'expo-location'
import MapWiew, { Marker } from 'react-native-maps'
import * as Device from 'expo-device';
import * as Notifications from 'expo-notifications'


const widthScreen = Dimensions.get('window').width;

//configuración global - diseño
Notifications.setNotificationHandler({
  handleNotification: async () => ({
    shouldShowAlert: true,
    shouldPlaySound: false,
    shouldSetBadge: false,
  }),
});

//función que envía la notificación
async function sendPushNotification(expoPushToken){
  const message = {
    to: expoPushToken,
    sound: 'default',
    title: 'Original Title',
    body: 'And here is the body!',
    data: { someData: 'goes here' },
};

await fetch('https://exp.host/--/api/v2/push/send', {
    method: 'POST',
    headers: {
        Accept: 'application/json',
        'Accept-encoding': 'gzip, deflate',
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(message),
});
}

//permisos para acceder a las notificaciones y obtener el token
async function registerForPushNotificationsAsync(){
  let token;
    if (Device.isDevice) {
        const { status: existingStatus } = await Notifications.getPermissionsAsync();
        let finalStatus = existingStatus;
        if (existingStatus !== 'granted') {
            const { status } = await Notifications.requestPermissionsAsync();
            finalStatus = status;
        }
        if (finalStatus !== 'granted') {
            alert('Failed to get push token for push notification!');
            return;
        }
        token = (await Notifications.getExpoPushTokenAsync()).data;
        console.log(token);
    } else {
        alert('Must use physical device for Push Notifications');
    }

    if (Platform.OS === 'android') {
        Notifications.setNotificationChannelAsync('default', {
            name: 'default',
            importance: Notifications.AndroidImportance.MAX,
            vibrationPattern: [0, 250, 250, 250],
            lightColor: '#FF231F7C',
        });
    }

    return token;
}

export default function ChangeAddress(props) {
  const { setShowModal } = props;
  const [location, setLocation] = useState({
    latitude: "-99.20074623157105", 
    longitude:"18.850405228048416",
    latitudDelta: 0.004757, 
    longitudDelta: 0.006866,});
  const [expoPushToken, setExpoPushToken] = useState('');
  const [notification, setNotification] = useState(false);
  const notificationListener = useRef();
  const responseListener = useRef();

    useEffect(() => {
      (async () => {
          
          try {
              let { status } = await Location.requestForegroundPermissionsAsync();
          if (status !== "denied") {
              try {
                  const loc = await Location.getCurrentPositionAsync({});
                  
                  console.log('location', loc)
              } catch (error) {
                  console.log("error", error);
              }
          } else {
              //alert
          }
          } catch (error) {
              console.log("error", error);
          }
          
      })();
  }, []);

    useEffect(() => {
      registerForPushNotificationsAsync().then(token => setExpoPushToken(token));
  
      notificationListener.current = Notifications.addNotificationReceivedListener(notification => {
        setNotification(notification);
      });
  
      responseListener.current = Notifications.addNotificationResponseReceivedListener(response => {
        console.log(response);
      });
  
      return () => {
        Notifications.removeNotificationSubscription(notificationListener.current);
        Notifications.removeNotificationSubscription(responseListener.current);
      };
    }, []);

  const save = async () => await sendPushNotification(expoPushToken)

  return (
    <View>
    {location && (
      <MapWiew style={styles.map} initialRegion={location} showsUserLocation={true} minZoomLevel={15} onRegionChange={(region)=> setLocation(region)}>
        <Marker coordinate={{latitude: location.latitude, longitude: location.longitude}} title="Mi ubicación" draggable/>
      </MapWiew>
    )}
    <View style={{ flex: 1, alignItems: "center", marginTop: 10 }}>
      <Divider color="tomato" width={2} style={styles.divider} />
    </View>
    <View style={styles.containerButtons}>
    <Button
        title="Cancelar Ubicación"
        icon={
          <Icon
            type="material-community"
            name="close-box"
            size={22}
            color="#FFF"
          />
        }
        containerStyle={styles.btnDangerContainer}
        buttonStyle={styles.btnDanger}
        onPress={()=>setShowModal(false)}
      />
      <Button
        title="Guardar"
        icon={
          <Icon
            type="material-community"
            name="update"
            size={22}
            color="#FFF"
          />
        }
        containerStyle={styles.btnSuccessContainer}
        buttonStyle={styles.btnSuccess}
        onPress={save}
      />
    </View>
  </View>
  )
}



const styles = StyleSheet.create({
  map: {
    width: "100%",
    height: 560,
  },
  divider: {
      width: "100%",
  },
  container: {
    backgroundColor: "#73CAAF",
  },
  text:{
    backgroundColor: "tomato",
    textAlign: "center",
    color: "#FFF",
    size: 500
  },
  containerButtons: {
    flexDirection: "row",
    justifyContent: "center",
    marginTop: 10,
  },
  input: {
    width: "100%",
    marginBottom: 16,
  },
  btnSuccessContainer: {
    width: "50%",
    padding: 10
  },
  btnDangerContainer: {
      padding: 10
  },
  btnDanger: {
      backgroundColor: "#E62727",
  },
  btnSuccess: {
      backgroundColor: "#28a745",
  },
  btnContainer: {
    margin: 5,
  },
  createAccount: {
    color: "#007bff",
  },
})