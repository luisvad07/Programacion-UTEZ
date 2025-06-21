import { StyleSheet, Text, View, ScrollView } from "react-native";
import React, { useState } from "react";
import { Input, Button, Image, Icon } from "@rneui/base";
import { isEmpty } from "lodash";
import Loading from "../../../../kernel/components/Loading";
import { signInWithEmailAndPassword } from "firebase/auth";
import { getAuth } from "firebase/auth";
import CreateUser from "../../../user/CreateUser";
export default function Login(props) {
  const auth = getAuth();
  const { navigation } = props;
  const [error, setError] = useState({email: '', password: ''});
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(true);
  const [show, setShow] = useState(false);
  //const [failSession, setFailSession] = useState(false);
  const login = () => {
    if (!(isEmpty(email) || isEmpty(password))) {
      setShow(true);
      setError({email: '', password: ''});
      signInWithEmailAndPassword(auth, email, password)
        .then(async (userCredential) => {
          setShow(false);
          navigation.navigate("profileStack");
        })
        .catch((error) => {
          setError({email: '', password: 'Usuario o contraseña incorrectos'});
          setShow(false);
          console.log("error", error);
          // ..
        });
    } else {
      setError({email: 'Campo obligatorio', password: 'Campo obligatorio'});
    }
  };
  return (
    <View style={styles.container}>
      <ScrollView>
        <Image
          source={require("../../../../assets/hucha.png")}
          resizeMode="contain"
          style={styles.logotype}
        />
        <Input
          placeholder="Correo Electrónico"
          keyboardType="email-address"
          containerStyle={styles.input}
          onChange={(event) => setEmail(event.nativeEvent.text)}
          errorMessage={error.email}
          autoCapitalize="none"
        />
        <Input
          placeholder="Contraseña"
          containerStyle={styles.input}
          onChange={(event) => setPassword(event.nativeEvent.text)}
          secureTextEntry={showPassword}
          rightIcon={
            <Icon
              type="material-community"
              name={showPassword ? "eye-off-outline" : "eye-outline"}
              color="#007bff"
              onPress={() => setShowPassword(!showPassword)}
            />
          }
          errorMessage={error.password}
        />
        <Button
          title="Iniciar sesión"
          icon={
            <Icon
              type="material-community"
              name="login"
              size={22}
              color="#FFF"
            />
          }
          buttonStyle={styles.btnSuccess}
          containerStyle={styles.btnContainer}
          onPress={login}
        />
        <Text style={styles.createAccount} onPress={() => navigation.navigate("createUserStack")}>
          ¡Registrate!
        </Text>
        <Loading show={show} text="Iniciando sesión" name="Luis" />
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#FFF",
    height: "100%",
  },
  logotype: {
    width: "100%",
    height: 150,
    marginTop: 16,
    marginBottom: 16,
  },
  input: {
    width: "100%",
    marginBottom: 16,
  },
  btnSuccess: {
    color: "#FFF",
    backgroundColor: "#28a745",
  },
  btnContainer: {
    margin: 16,
  },
  createAccount: {
    color: "#007bff",
    textAlign: "center"
  },
});
