import { LogBox, StyleSheet} from 'react-native';
import Navigation from './config/navigation/Navigation';
import { app } from './config/utils/firebase';

//not show warnings or error 
LogBox.ignoreAllLogs(true);  

export default function App() {
  return (
    <Navigation/>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
