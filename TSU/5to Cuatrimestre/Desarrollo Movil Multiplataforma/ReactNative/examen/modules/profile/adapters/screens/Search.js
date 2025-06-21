import React from 'react'
import {SafeAreaView, StyleSheet, TextInput, ScrollView} from 'react-native';
//import the components that we'll be creating here 
import StackLayout from '../../../../kernel/StackLayout';

const Search = () => {
  return (
    <SafeAreaView>
      <TextInput style={styles.input}  placeholder="¿Qué deseas buscar?" placeholderTextColor={'red'}/>
      <StackLayout />
    </SafeAreaView>
  )
};

const styles = StyleSheet.create({
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  },
})

export default Search