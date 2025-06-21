import { StyleSheet, Text, View } from 'react-native'
import React, { useState } from 'react'
import { Button } from '@rneui/base'
import { getAuth } from 'firebase/auth'
import Loading from '../../../../kernel/components/Loading'

export default function Profile() {
    const auth = getAuth()
    const [show, setShow] = useState(false)
    const [text, setText] = useState('')
    const cerrarSesion = () => {
        setText('Cerrando sesión')
        setShow(true)
        return auth.signOut()
    }
    return (
        <View style={styles.btnContainer}>
            <Button
                title='Cerrar sesión'
                buttonStyle={styles.btn}
                onPress={cerrarSesion}
            >

            </Button>
            <Loading show={show} text={text} />
        </View>
    )
}

const styles = StyleSheet.create({
    btnContainer: {
        justifyContent: 'center',
        alignItems: 'center'
    },
    btn: {
        marginTop: 30,
        backgroundColor: 'tomato',
        paddingVertical: 10,
        marginHorizontal: 20,
        borderRadius: 10,
        width: 250
    },
})