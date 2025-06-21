import { StyleSheet, Text, View, Dimensions } from "react-native";
import React, { useState } from "react";
import { Image, Divider } from '@rneui/base';
import { localhost } from "../../../../config/utils/axios";

export default function Home() {

  const [pdfSource, setPdfSource] = useState()

  const baseUrl = `http://${localhost}:8080/redre/`

    return (
        <View style={styles.container}>
          <View style={styles.card}>
            <View style={{
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
                <Image source={require("../../../../assets/datid.png")} style={styles.profile} />
                <Text style={styles.textCarrer}>DSM</Text>
            </View>
          </View>
          <View style={styles.data}>
            <Text style={styles.textData}>Listas de Reportes de Estadías</Text>
          </View>
          <View style={styles.reportes}>
              <Text>Reporte1.pdf</Text>
              <Divider style={styles.divider}/>
              <Text>Reporte2.pdf</Text>
              <Divider style={styles.divider}/>
          </View>
        </View>
      );
    }
    
    const deviceContainer = Math.round(Dimensions.get("window").width);
    
    const styles = StyleSheet.create({
      container: {
        minHeight: "100%",
        backfaceVisibility: "#FFF"
      },
      card: {
        backgroundColor: "#1C8F71",
        height: "35%",
        paddingHorizontal: 20,
      },
      textCarrer: {
        margin: 10,
        fontFamily: "Roboto",
        fontSize: 25,
      },
      data: {
        backgroundColor: "#fff",
        paddingVertical: 8,
        borderRadius: 15,
        marginTop: 100,
        alignSelf: "center",
        width: 300,
      },
      textData: {
        fontFamily: "Roboto",
        fontSize: 20,
        textAlign: "center",
      },
      profile: {
        width: 250,
        height: 80,
      },
      reportes: {
          alignItems: 'center',
          backgroundColor: "#fff",
          paddingVertical: 8,
          borderRadius: 15,
          marginTop: 30,
          alignSelf: "center",
          width: 300,
      },
      divider: {
        width: "100%",
      },
    });
    