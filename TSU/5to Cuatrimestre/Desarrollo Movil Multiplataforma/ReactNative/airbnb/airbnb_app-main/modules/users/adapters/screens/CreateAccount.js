import { StyleSheet, View } from 'react-native'
import React, { useState } from 'react'
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view'
import { isEmpty, size } from 'lodash'
import { Image, Input, Button, Icon } from '@rneui/base'
import { getAuth, createUserWithEmailAndPassword } from "firebase/auth";
import { validateEmail } from '../../../../kernel/validations'
import axios from '../../../../kernel/http-client.gateway'
import AsyncStorage from '@react-native-async-storage/async-storage'

import Loading from '../../../../kernel/components/Loading'
import { useNavigation } from '@react-navigation/native'

export default function CreateAccount() {
    const { navigation } = useNavigation()
    const payLoad = {
        email: '',
        password: '',
        repeatPassword: ''
    }
    const auth = getAuth()
    const [show, setShow] = useState(false)
    const [error, setError] = useState(payLoad)
    const [data, setData] = useState(payLoad)
    const [showPassword, setShowPassword] = useState(true)
    const [showRepeatPassword, setShowRepeatPassword] = useState(true)
    const changePayLoad = (e, type) => {
        setData({ ...data, [type]: e.nativeEvent.text })
    }
    const createUser = () => {
        console.log('CreateUser 24 -> data', data);
        if (!(isEmpty(data.email || isEmpty(data.password)))) {
            if (validateEmail(data.email)) {
                if ((size(data.password)) >= 6) {
                    if (data.password == data.repeatPassword) {
                        setShow(true)
                        setError(payLoad)
                        console.log('Listo para el registro');
                        createUserWithEmailAndPassword(auth, data.email, data.password)
                            .then(async (userCredential) => {
                                const user = userCredential.user;
                                try {
                                    await AsyncStorage.setItem('@session', JSON.stringify(user))
                                } catch (e) {
                                    console.error("Error -> createUser Storage", e);
                                }
                                console.log("Created User", user);
                                setShow(false)
                                navigation.navigate("profileStack")
                            })
                            .catch((error) => {
                                setError({ email: '', password: 'No se pudo crear el usuario' })
                                setShow(false)
                                const errorCode = error.code;
                                const errorMessage = error.message;
                            });
                    } else {
                        setError({
                            email: '',
                            password: 'Debe coincidir con repetir contraseña',
                            repeatPassword: 'Debe coincidir con contraseña'
                        })
                    }
                } else {
                    setError({
                        email: '',
                        password: 'Logitud de por lo menos 6 carácteres',
                        repeatPassword: 'Logitud de por lo menos 6 carácteres'
                    })
                }
            } else {
                setError({
                    email: 'Debe ser un correo electrónico válido',
                    password: '',
                    repeatPassword: ''
                })
            }
        } else {
            setError({
                email: 'Campo obligatorio',
                password: 'Campo obligatorio',
                repeatPassword: 'Campo obligatorio'
            })
        }
    }
    return (
        <KeyboardAwareScrollView style={styles.screen}>
            <Image
                source={require('../../../../assets/airbnb.png')}
                resizeMode='contain'
                style={styles.logo}
            />
            <View style={styles.viewForm}>
                <View style={styles.container}>
                    <Input
                        placeholder='Correo Electrónico'
                        keyboardType='email-address'
                        rightIcon={
                            <Icon type='material-community' name='email' size={22} />
                        }
                        containerStyle={styles.input}
                        onChange={(e) => changePayLoad(e, 'email')}
                        errorMessage={error.email}
                        autoCapitalize='none'
                    />
                    <Input
                        placeholder='Contraseña'
                        containerStyle={styles.input}
                        rightIcon={
                            <Icon
                                type='material-community'
                                name={showPassword ? 'eye-off-outline' : 'eye-outline'}
                                onPress={() => setShowPassword(!showPassword)}
                                size={22}
                            />
                        }
                        secureTextEntry={showPassword}
                        onChange={(e) => changePayLoad(e, 'password')}
                        errorMessage={error.password}
                    />
                    <Input
                        placeholder='Repetir contraseña'
                        containerStyle={styles.input}
                        rightIcon={
                            <Icon
                                type='material-community'
                                name={showRepeatPassword ? 'eye-off-outline' : 'eye-outline'}
                                onPress={() => setShowRepeatPassword(!showRepeatPassword)}
                                size={22}
                            />
                        }
                        secureTextEntry={showRepeatPassword}
                        onChange={(e) => changePayLoad(e, 'repeatPassword')}
                        errorMessage={error.repeatPassword}
                    />
                    <Button
                        title='Crear cuenta'
                        containerStyle={styles.btnContainer}
                        buttonStyle={styles.btn}
                        onPress={createUser}
                    />
                </View>
            </View>
            <Loading show={show} text='Registrando' />
        </KeyboardAwareScrollView>
    )
}

const styles = StyleSheet.create({
    logo: {
        width: '100%',
        height: 159,
        marginTop: 100
    },
    viewForm: {
        marginHorizontal: 20
    },
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: 20
    },
    input: {
        width: '100%',
        marginVertical: 10
    },
    btnContainer: {
        marginBottom: 20,
        width: '95%'
    },
    btn: {
        backgroundColor: '#e72c5a'
    },
    screen: {
        backgroundColor: 'white'
    },
})