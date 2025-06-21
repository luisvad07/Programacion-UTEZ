import { StyleSheet, Text, View } from "react-native";
import React, { useState, useEffect } from "react";
import { Button, Avatar } from "@rneui/base";
import Loading from "../../../../kernel/components/Loading";
import { getStorage, ref, uploadBytes, getDownloadURL } from "firebase/storage";
//import { getAuth, updateProfile } from "firebase/auth";
import * as Imagepicker from "expo-image-picker";
import * as Permissions from "expo-permissions";
import * as MediaLibrary from 'expo-media-library'
import { getAuth, updateProfile } from "firebase/auth";
import AccountOptions from "./AccountOptions";

export default function UserLogged(props) {
  const auth = getAuth();
  const { user } = props;
  const [show, setShow] = useState(false);

  const uploadImage = async (uri) => {
    setShow(true);
    const response = await fetch(uri);
    const { _bodyBlob } = response;
    const storage = getStorage();
    const storageRef = ref(storage, `avatars/${user.uid}`);
    return uploadBytes(storageRef, _bodyBlob);
  };

  const changeAvatar = async () => {
    const {status} = await MediaLibrary.requestPermissionsAsync()
    if (status !== 'denied') {
      let result = await Imagepicker.launchImageLibraryAsync({
        mediaTypes: Imagepicker.MediaTypeOptions.Images,
        allowsEditing: true,
        quality: 1,
      });
      if (!result.canceled) {
        uploadImage(result.assets[0].uri).then((response) => {
          console.log('Imagen actualizada');
          uploadPhotoProfile();
        }).catch((err) => {
          console.log('Error', err)
          setShow(false)
        })
      } else {
        console.log("No se ha seleccionado una imagen");
      }
    } else {
      //alert
    }
  };

  const uploadPhotoProfile = () => {
    const storage = getStorage();
    getDownloadURL(ref(storage, `avatars/${user.uid}`))
      .then((url) => {
        updateProfile(auth.currentUser, {
          photoURL: url,
        })
          .then(() => {
            setShow(false);
          })
          .catch((error) => {
            setShow(false);
            console.log("fallo", error);
          });
      })
      .catch((err) => {
        console.log("error al obtener la imagen", err);
      });
  };
  return (
    <View style={styles.container}> 
      {user && (
        <View style={styles.infoContainer}>
          {user.photoURL ? (
            <Avatar
              size="large"
              rounded
              source={{ uri: user.photoURL }}
              containerStyle={styles.avatar}
            >
              <Avatar.Accessory size={22} onPress={changeAvatar} />
            </Avatar>
          ) : (
            <Avatar
              size="large"
              rounded
              title={`${user.email.charAt(0)} ${user.email.charAt(1)}`}
              containerStyle={styles.avatar}
            />
          )}

          <View>
            <Text style={styles.displayName}>
              {user.displayName ? user.displayName : "Anónimo"}
            </Text>
            <Text>{user ? user.email : "sin correo"}</Text>
          </View>
        </View>
      )}
      <AccountOptions/>
      <Button
        title="Cerrar sesión"
        buttonStyle={styles.btn}
        onPress={() => auth.signOut()}
      />
      <Loading show={show} text="Actualizando imagen" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    minHeight: "100%",
    backgroundColor: "#FFF",
  },
  btn: {
    marginTop: 30,
    borderRadius: 0,
    backgroundColor: "tomato",
    paddingVertical: 10,
  },
  infoContainer: {
    alignItems: "center",
    justifyContent: "center",
    flexDirection: "row",
    paddingVertical: 30,
  },
  avatar: {
    marginRight: 16,
    backgroundColor: 'tomato'
  },
  displayName: {
    fontWeight: "bold",
    paddingBottom: 5,
  },
});
