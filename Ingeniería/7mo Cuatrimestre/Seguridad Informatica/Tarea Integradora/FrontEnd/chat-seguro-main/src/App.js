import "./App.css";
import Router from "./router/router";
import { AuthProvider } from "./services/auth/context/AuthContext";
function App() {
  return (
    <>
    <AuthProvider>
    <Router />
    </AuthProvider>
    </>
  );
}

export default App;
