import { StyleSheet} from 'react-native'
import React from 'react'
import { Overlay } from '@rneui/base'

export default function Modal(props) {
    const {show, setShow, children} = props;
  return (
    <Overlay isVisible={show} windowBackgroundColor="rgba(0,0,0,0.5)" overlayBackgroundColor="transparent" overlayStyle={styles.overlay} onBackdropPress={()=>setShow(false)}>
        {children}
    </Overlay>
  )
}

const styles = StyleSheet.create({
    overlay:{
        height: "auto",
        width: "90%",
        backgroundColor: "#FFF"
    }
});