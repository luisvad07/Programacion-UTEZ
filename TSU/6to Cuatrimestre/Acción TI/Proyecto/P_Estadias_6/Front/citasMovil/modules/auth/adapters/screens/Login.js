import { StyleSheet, Text, TextInput, View, Button, Icon } from "react-native";
import React from "react";
import { StatusBar } from "expo-status-bar";

export default function Login() {
  return (
    <View style={styles.container}>
      <Text style={styles.titulo}>CITAT</Text>
      <Text style={styles.subTitle}>únicamente para solicitantes</Text>
      <TextInput
      placeholder="Correo electrónico"
      style={styles.input}
      />
      <TextInput
      placeholder="Contraseña"
      style={styles.input}
      />
      
      <StatusBar style="auto"/>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  titulo:{
    fontSize: 80,
    color: '#000',
    fontWeight: 'bold'
  },
  subTitle:{
    fontSize: 20,
    color: 'gray',

  }, 
  input:{
    borderWidth: 1,
    borderColor: 'gray',
    padding: 10,
    width: '80%',
    height: 50,
    marginTop: 20,
    borderRadius: 30,

  },
  logBtn: {
    alignSelf: "center",
    backgroundColor: "#012258",
    borderRadius: 500,
  },
  btnContainer: {
    margin: 10
  },
});
