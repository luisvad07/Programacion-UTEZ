import { StyleSheet, Text, View, Dimensions } from "react-native";
import React, { useEffect, useState } from 'react'
import { Avatar, Button } from 'react-native-elements'
import { getAuth, updateProfile } from 'firebase/auth'
import Loading from '../../../../kernel/components/Loading';
import { ButtonGroup, Icon, Image } from '@rneui/base';
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function UserLogged({ user, navigation }) {

    const auth = getAuth();
    const [show, setShow] = useState(false)
    const [text, setText] = useState('Procesando...')
    const [session, setSession] = useState(null)

    useEffect(() => {
        setShow(false);
    }, [])

    const logOut = () => {
        setShow(true); setText("Cerrando sesión")
        const x = setInterval(function () {
            clearInterval(x)
            deleteData('sessionId');
            auth.signOut();
        }, 900)
    }

    const deleteData = async (key) => {
        try {
          await AsyncStorage.removeItem(key);
        } catch (error) {
          console.log(error);
        }
      };

    return (
        <View style={styles.container}>
            <View style={styles.card}>
                <View
                style={{
                    flexDirection: "row",
                    alignItems: "center",
                    marginTop: 3,
                    width: "100%",
                }}
                >
                <View style={{ width: "50%" }}>
                    <Text
                    style={{
                        fontSize: 20,
                        fontWeight: "bold",
                        color: "#fff",
                        marginTop: 78,
                    }}
                    >
                    REDRE
                    </Text>
                </View>

                <View style={{ width: "50%", alignItems: "flex-end", marginTop: 38 }}>
                    <Button icon={<Icon type="material-community" name="power" size={15} color="white" />} buttonStyle={styles.btn} onPress={logOut} />
                </View>
                </View>
            </View>

            <View
                style={{
                backgroundColor: "#fff",
                paddingVertical: 8,
                borderRadius: 15,
                marginTop: 150,
                position: "absolute",
                alignSelf: "center",
                width: 300,
                }}>

                <View style={{ alignItems: "center", marginTop: 20 }}>
                    <Icon  type='material-community' name="account-circle-outline" size={100} color="#012258" />
                <Text style={styles.textName}>{user.nombre} {user.apellidos}</Text>
                </View>
            </View>

            <View style={styles.data1}>
                <View style={{ alignItems: "flex-start" }}>
                <Icon
                    style={{ marginLeft: 15 , position: 'relative'}}
                    type='material-community'
                    name="circle"
                    size={24}
                    color="#012258"
                />
                <Text style={styles.textData}>Matrícula: {user.matricula}</Text>
                </View>
            </View>

            <View style={styles.data2}>
                <View style={{ alignItems: "flex-start" }}>
                <Icon
                style={{ marginLeft: 15 , position: 'relative'}}
                type='material-community'
                    name="circle"
                    size={24}
                    color="#012258"
                />
                <View>
                    <Text style={styles.textData}>División Académica: {user.divisionAcademica}</Text>
                </View>
                </View>
            </View>

            <View style={styles.data2}>
                <View style={{ alignItems: "flex-start" }}>
                <Icon
                    style={{ marginLeft: 15 ,position: 'relative'}}
                    type='material-community'
                    name="circle"
                    size={24}
                    color="#012258"
                />
                    <Text style={styles.textData}>Carrera: {user.carrera}</Text>
                </View>
            </View>

            <View style={styles.data2}>
            <View style={{ alignItems: "flex-start" }}>
                <Icon
                    style={{ marginLeft: 15 , position: 'relative'}}
                    type='material-community'
                    name="circle"
                    size={24}
                    color="#012258"
                />
                    <Text style={styles.textData}>Grado y Grupo: {user.grado}° {user.grupo}</Text>
                    </View>
            </View>
            <Loading show={show} text={text} />
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        minHeight: "100%",
        backfaceVisibility: "#FFF"
    },
    btn: {
        marginTop: 30,
        borderRadius: 5,
        backgroundColor: "#012258",
        width: 40
    },
    card: {
        backgroundColor: "#1C8F71",
        height: "35%",
        paddingHorizontal: 20,
    },
    infoContainer: {
        alignItems: "center",
        flexDirection: "row",
        paddingVertical: 10,
    },
    avatar: {
        marginLeft: 20,
        marginRight: 20,
    },
    textName: {
        margin: 15,
        fontFamily: "Roboto",
        fontSize: 25,
      },
    data1: {
        backgroundColor: "#fff",
        paddingVertical: 8,
        borderRadius: 15,
        marginTop: 130,
        alignSelf: "center",
        width: 300,
      },
      data2: {
        backgroundColor: "#fff",
        paddingVertical: 10,
        borderRadius: 15,
        marginTop: 30,
        alignSelf: "center",
        width: 300,
      },
      textData: {
        fontFamily: "Roboto",
        fontSize: 20,
        marginLeft: 60,
        marginTop: -24
      },
})