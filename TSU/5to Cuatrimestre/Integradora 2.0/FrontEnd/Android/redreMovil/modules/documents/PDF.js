import { SafeAreaView, StyleSheet, Text, View } from 'react-native'
import React from 'react'
import Pdf from 'react-native-pdf'

export default function PDF() {
  return (
    <SafeAreaView>
        <Pdf source={'siuuu'}/>
    </SafeAreaView>
  )
}

const styles = StyleSheet.create({})