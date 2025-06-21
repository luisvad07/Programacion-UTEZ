import { StyleSheet, Text, View, ScrollView, Dimensions } from "react-native";
import { Input, Button, Image, Icon } from "@rneui/base";
import React, { useState } from "react";
import { isEmpty } from "lodash";
import Loading from "../../../../kernel/components/Loading";
import { getAuth, signInWithEmailAndPassword } from "firebase/auth"
import AsyncStorage, { useAsyncStorage } from "@react-native-async-storage/async-storage";
import axios from "axios";
import { useEffect } from "react";
import { useNavigation } from "@react-navigation/native";

export default function Login(props) {
  const { navigation, texto } = props
  const backIP = "http://192.168.43.234:8080/redre/estudiante/"; // Se debe cambiar la IP cada vez que se cambie de red

  useEffect(() => {
  }, [])

  const [error, setError] = useState({ email: '', password: '' });
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(true)
  const [show, setShow] = useState(false)
  //const [failSession, setFailSession] = useState(false)
  const auth = getAuth()
  const [text, setText] = useState(texto)

  const consumir = () => {
    setText("Iniciando sesión")
    if (!(isEmpty(email) || isEmpty(password))) {
      setShow(true)
      setError({ email: '', password: '' })
      axios.get(backIP).then(async (response) => {
        const arreglo = response.data.obj;
        for (let index = 0; index < arreglo.length; index++) {
          const element = arreglo[index];
          if (element.correo == email && element.password == password) {
            console.log("Usuario encontrado");
            await storeData('sessionId', element.id.toString());
            await signInWithEmailAndPassword(auth, "20213tn018@utez.edu.mx", "axel1234")
              .then(async (userCredential) => {
                setShow(false)
              })
              .catch((error) => {
                console.log(error);
                setError({ email: '', password: 'Usuario o contraseña incorrectos' })
                setShow(false)
                const errorCode = error.code;
                const errorMessage = error.message;
            });
          }
        }
      }).finally(()=> {
        setError({ email: '', password: 'Usuario o contraseña incorrectos' })
        setShow(false)
      }).catch((error) => console.log("error", error));
    } else {
      setError({ email: 'Campo obligatorio', password: 'Campo obligatorio' })
      setShow(false)
    }
  }

  const storeData = async (key, dato) => {
    try {
      await AsyncStorage.setItem(key, dato)
    } catch (error) {
      console.log(error);
    }
  };

  const login = () => {
    setText("Iniciando sesión")
    if (!(isEmpty(email) || isEmpty(password))) {
      setShow(true)
      setError({ email: '', password: '' })

      signInWithEmailAndPassword(auth, email, password)
        .then(async (userCredential) => {
          setShow(false)
          navigation.navigate("profileStack");
        })
        .catch((error) => {
          console.log(error);
          setError({ email: '', password: 'Usuario o contraseña incorrectos' })
          setShow(false)
          const errorCode = error.code;
          const errorMessage = error.message;
        });
    } else {
      setError({ email: 'Campo obligatorio', password: 'Campo obligatorio' })
      setShow(false)
    }
  };
  return (
    <View style={styles.container}>
      <Text style={styles.title}>REDRE</Text>

      <View style={styles.cardContainer}>
        <Image
          source={require('../../../../assets/user.png')}
          style={styles.profile}
        />
        <Input
          placeholder="Correo Electrónico"
          keyboardType="email-address"
          containerStyle={styles.input}
          onChange={(event) => setEmail(event.nativeEvent.text)}
          errorMessage={error.email}
          autoCapitalize='none'
        />
        <Input
          placeholder="Contraseña"
          containerStyle={styles.input}
          onChange={(event) => setPassword(event.nativeEvent.text)}
          secureTextEntry={showPassword}
          rightIcon={
            <Icon type="material-community"
              name={showPassword ? 'eye-off-outline' : 'eye-outline'}
              color="#3FB899"
              onPress={() => setShowPassword(!showPassword)}>
            </Icon>}
          errorMessage={error.password}
        />
        <Button
          title="Iniciar sesión"
          icon={
            <Icon
              type="material-community"
              name="login"
              size={22}
              color="#fff"
            />
          }
          buttonStyle={styles.logBtn}
          containerStyle={styles.btnContainer}
          onPress={consumir}
        />
        <Loading show={show} text={text} />
      </View>
    </View>
  );
}

const deviceContainer = Math.round(Dimensions.get("window").width);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#1C8F71",
    alignItems: "center",
    justifyContent: "center",
  },
  cardContainer: {
    width: deviceContainer - 50,
    backgroundColor: "#fff",
    height: 350,
    borderRadius: 30,
    alignItems: "center",
  },
  profile: {
    width: 80,
    height: 80,
    margin: 20,
  },
  title: {
    fontFamily: "Roboto",
    fontSize: 35,
    fontWeight: "500",
    color: "#fff",
    marginBottom: 30,
  },
  input: {
    width: '100%',
  },
  logBtn: {
    alignSelf: "center",
    backgroundColor: "#012258",
    borderRadius: 500,
  },
  btnContainer: {
    margin: 10
  },
  createAccount: {
    color: '#007bff',
    textAlign: "center"
  },
});
