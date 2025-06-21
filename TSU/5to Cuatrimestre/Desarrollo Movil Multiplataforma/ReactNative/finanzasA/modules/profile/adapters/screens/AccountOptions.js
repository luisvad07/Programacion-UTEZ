import { StyleSheet, Text, View } from 'react-native'
import React, {useState} from 'react'
import { ListItem } from '@rneui/base'
import { map } from 'lodash'
import Modal from '../../../../kernel/components/Modal'
import ChangeDisplayName from './components/ChangeDisplayName'
import ChangePassword from './components/ChangePassword'
import ChangeAdress from './components/ChangeAdress'
import { Icon } from '@rneui/base/dist'

export default function AccountOptions(props) {
    const {userInfo} = props;
    const { setReload } = props
    const [showModal, setShowModal] = useState(false);
    const [renderComponent, setRenderComponent] = useState(null);
    const selectComponent = (key) => {
        switch (key) {
            case 'displayName':
                setRenderComponent(<ChangeDisplayName setReload={setReload}/>)
                setShowModal(true)
                break;
            case 'password':
                setRenderComponent(<ChangePassword setReload={setReload}/>)
                setShowModal(true)
                break;
            case 'address':
                setRenderComponent(<ChangeAdress setShowModal={setShowModal}/>)
                setShowModal(true)
                break;    
            default:
                setRenderComponent(false)
                setShowModal(false)
                break;
        }
    }
    const menuOption = generateOptions(selectComponent)

  return (
    <View>
      {map(menuOption, (menu, index) => (
        <ListItem  containerStyle={styles.menuOption} key={index} onPress={menu.onPress} >
        <Icon name={menu.iconNameLeft} type={menu.iconType} color={menu.iconColorLeft}/>
            <ListItem.Content>
                <ListItem.Title>{menu.title}</ListItem.Title>
            </ListItem.Content>
            <ListItem.Chevron/>
        </ListItem>
        
      ))}
      {renderComponent && (
        <Modal show={showModal} setShow={setShowModal}>
            {renderComponent}
        </Modal>
      )}
    </View>
  )
}

const styles = StyleSheet.create({
    menuOption:{
        borderBottomWidth: 1,
        borderBottomColor: "#E3E3E3"
    }
})

const generateOptions = (selectComponent) => {
    return [
        {
            title: 'Actualizar Nombre Completo',
            iconType: 'material-community',
            iconNameLeft: 'account-circle',
            iconColorLeft: 'tomato',
            onPress: () => selectComponent("displayName")
        },
        {
            title: 'Actualizar Contraseña',
            iconType: 'material-community',
            iconNameLeft: 'lock-reset',
            iconColorLeft: 'tomato',
            onPress: () => selectComponent("password")
        },
        {
            title: 'Actualizar Ubicación',
            iconType: 'material-community',
            iconNameLeft: 'map-marker',
            iconColorLeft: 'tomato',
            onPress: () => selectComponent("address")
        }
    ]
}