import { StyleSheet, View } from 'react-native'
import React, {useState} from 'react'
import { Input, Button, Image, Icon, Text } from "@rneui/base";
import { getAuth, updateProfile } from 'firebase/auth';
import Loading from '../../../../../kernel/components/Loading';
import { isEmpty } from 'lodash';

export default function ChangeDisplayName(props) {
  const { setReload } = props
    const auth = getAuth()
    const [displayName, setDisplayName] = useState(auth.currentUser.displayName ? auth.currentUser.displayName : '')
    const [show, setShow] = useState(false)
    const [text, setText] = useState('')
    const [error, setError] = useState({ displayName: '' })

   
    const updateDisplayName = () => {
      setShow(true)
      setText('Actualizando...')
      if (!isEmpty(displayName)) {
          updateProfile(auth.currentUser, {
              displayName: displayName
          })
              .then(() => {
                  setError({displayName: ''})
                  setShow(false)
                  setReload(true)
              })
              .catch((err) => {
                  setError({displayName: 'Error al actualizar nombre'})
                  setShow(false)
                  console.log('Fallo', err);
              })
      }else{
          setShow(false)
          setError({displayName: 'Campo obligatorio'})
      }
  }  
  
  return (
    <View style={styles.container}>
      <View>
        <Text style={styles.text}>Cambiar Nombre</Text>
      </View>
        <Image
          source={require("../../../../../assets/firma.png")}
          resizeMode="contain"
          style={styles.logotype}
        />
        <Input
          placeholder="Nombre Completo"
          containerStyle={styles.input}
          onChange={(event) => setDisplayName(event.nativeEvent.text)}
          errorMessage={error.displayName}
          autoCapitalize='none'
        />
        <Button
          title="Actualizar"
          icon={
            <Icon
              type="material-community"
              name="update"
              size={22}
              color="#FFF"
            />
          }
          buttonStyle={styles.btnSuccess}
          containerStyle={styles.btnContainer}
          onPress={updateDisplayName}
        />
      <Loading show={show} text={text} />
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#73CAAF",
  },
  text:{
    backgroundColor: "tomato",
    textAlign: "center",
    color: "#FFF",
    size: 500
  },
  logotype: {
    width: "100%",
    height: 50,
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
  btnClose: {
    color: "#FFF",
    backgroundColor: "#E62727",
  },
  btnContainer: {
    margin: 5,
  },
  createAccount: {
    color: "#007bff",
  },
})