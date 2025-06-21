import { StyleSheet, View } from 'react-native'
import React, {useState} from 'react'
import { Input, Button, Image, Icon, Text } from "@rneui/base";
import { getAuth, signInWithEmailAndPassword, updatePassword } from 'firebase/auth';
import { isEmpty } from 'lodash';
import Loading from '../../../../../kernel/components/Loading';

export default function ChangePassword(props) {
  
  const { setReload } = props
    const auth = getAuth()

    const payLoad = {
        email: '',
        password: '',
        newPassword: ''
    }
  
    const [show, setShow] = useState(false)
    const [text, setText] = useState('')
    const [error, setError] = useState(payLoad)
    const [data, setData] = useState(payLoad)

  const [showPasswordA, setShowPasswordA] = useState(true);
  const [showPasswordB, setShowPasswordB] = useState(true);

  const changePayLoad = (e, type) => {
    setData({ ...data, [type]: e.nativeEvent.text })
  }

  const updatePass = () => {
    setShow(true)
    setText('Actualizando...')
    if (!(isEmpty(data.email) || isEmpty(data.password) || isEmpty(data.newPassword))) {
        console.log("Listos para iniciar sesión");
        setShow(true)
        setError({ email: '', password: '', newPassword: '' })
        signInWithEmailAndPassword(auth, data.email, data.password)
            .then(async (userCredential) => {
                console.log(userCredential)
                if (!isEmpty(data.newPassword)) {
                    updatePassword(auth.currentUser, data.newPassword).then(() => {
                        setError({ email: '', password: '', newPassword: '' })
                        console.log('Contraseña actualizada');
                        setShow(false)
                        setReload(true)
                    }).catch((error) => {
                        setError({ password: 'Error al actualizar contraseña' })
                        console.log('Error', error)
                        setShow(false)
                    })
                } else {
                    setShow(false)
                    setError({ password: 'Campo obligatorio' })
                }
            })
            .catch((error) => {
                setError({ email: '', password: 'Usuario o contraseña incorrectos', newPassword: '' })
                setShow(false)
                const errorCode = error.code;
                const errorMessage = error.message;
                console.log(errorCode)
                console.log(errorMessage)
            });
    } else {
        setError({ email: 'Campo obligatorio', password: 'Campo obligatorio', newPassword: 'Campo obligatorio' })
        setShow(false)
    }
}

  return (
    <View style={styles.container}>
      <View>
        <Text style={styles.text}>Cambiar Contraseña</Text>
      </View>
        <Image
          source={require("../../../../../assets/contrasena.png")}
          resizeMode="contain"
          style={styles.logotype}
        />
        <Input
          placeholder='Correo Electronico'
          containerStyle={styles.input}
          onChange={(e) => changePayLoad(e, 'email')}
          errorMessage={error.email}
          autoCapitalize='none'
        />
        <Input
          placeholder="Contraseña Anterior"
          containerStyle={styles.input}
          onChange={(e) => changePayLoad(e, 'password')}
          errorMessage={error.password}
          autoCapitalize='none'
          secureTextEntry={showPasswordA}
          rightIcon={
            <Icon
              type="material-community"
              name={showPasswordA ? "eye-off-outline" : "eye-outline"}
              color="#007bff"
              onPress={() => setShowPasswordA(!showPasswordA)}
            />
          }
        />
        <Input
          placeholder="Contraseña Nueva"
          containerStyle={styles.input}
          onChange={(e) => changePayLoad(e, 'newPassword')}
          errorMessage={error.newPassword}
          autoCapitalize='none'
          secureTextEntry={showPasswordB}
          rightIcon={
            <Icon
              type="material-community"
              name={showPasswordB ? "eye-off-outline" : "eye-outline"}
              color="#007bff"
              onPress={() => setShowPasswordB(!showPasswordB)}
            />
          }
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
          onPress={updatePass}
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
    height: 80,
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