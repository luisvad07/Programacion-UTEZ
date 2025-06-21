import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { Avatar, Icon, Image } from '@rneui/base'

const Home = ({ user }) => {
    return (
        <View >
            <View style={{
                alignItems: "center",
                flexDirection: "row",
                paddingVertical: 15
            }}>
                <Avatar size={50} rounded source={{ uri: user.photoURL }} containerStyle={{ marginHorizontal: 10 }} />
                <View style={{ marginRight: 120 }}>
                    <Text >Luis Valladolid</Text>
                    <Text style={{ fontSize: 12 }}>Cuernavaca Morelos</Text>
                </View>
                <Icon type="material-community"
                    name='dots-vertical'
                >
                </Icon>
            </View>
            <View>
                <Image style={{ height: 300 }} source={{ uri: 'https://th.bing.com/th/id/OIP.sfuxNNdWxeJ4UNLyoR6wDgHaHa?pid=ImgDet&rs=1' }} />
            </View>
            <View  >
                <View style={{
                    alignItems: "center",
                    flexDirection: "row",
                    paddingVertical: 10
                }}>
                    <Icon type="material-community"
                        name='heart-outline'
                        size={32}
                        style={{marginHorizontal:10}}

                    >
                    </Icon>


                    <Icon type="material-community"
                        name='comment-outline'
                        size={32}
                        style={{marginRight:250}}

                    >
                    </Icon>

                    <Icon type="material-community"
                        name='bookmark-outline'
                        size={32}

                    >
                    </Icon>

                    
                </View>
                <View>
                    <Text style={{ fontSize: 12, marginLeft:20 }}>5,000 Me gusta</Text>
                    <Text style={{ fontSize: 15, marginLeft:20 , marginTop:3 }}><Text style={{fontWeight:"bold"}}>LuisVad:</Text> El Señor de la Noche</Text>

                </View>
            </View>
        </View>
    )
}

export default Home

const styles = StyleSheet.create({})