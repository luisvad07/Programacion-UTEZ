import { StyleSheet, Text, View, ScrollView } from 'react-native'
import { Image, Button } from '@rneui/base'
import React from 'react'
import { useNavigation } from '@react-navigation/native'

export default function UserGuest() {
    const navigation = useNavigation()
    return (
        <View>
            <Text>Acerca de la Aplicación</Text>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        backgroundColor: '#fff',
        height: '100%',
        flex:1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    mx: {
        marginLeft: 32,
        marginRight: 32
    },
    img: {
        
        marginTop: 150,
        width: '100%',
        height: 160,
        marginLeft: 30
    },
    title: {
        fontWeight: 'bold',
        fontSize: 20,
        textAlign: 'center',
        fontFamily: 'monospace',
        margin: 10
    },
    description: {
        textAlign: 'center',
        marginBottom: 10
    },
    viewBtnContainer: {
        marginTop:40, 
        flex: 1,
        alignItems: 'center'
    },
    btn: {
        backgroundColor: '#3FB899',
        color: '#fff'
    },
    btnContainer: {
        width: '70%'
    },
})