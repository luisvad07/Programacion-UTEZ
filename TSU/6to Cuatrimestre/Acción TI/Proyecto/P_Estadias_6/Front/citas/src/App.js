import React, { useEffect } from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
//import 'bootswatch/dist/minty/bootstrap.min.css';

import Loading from "./shared/plugins/Loading";
import Login from "./modules/auth/Login";

import AdminScreen from "./modules/admin/Components/AdminScreen";
import SolicitanteScreen from "./modules/solicitante/SolicitanteScreen";
import VentanillaScreen from "./modules/ventanilla/components/VentanillaScreen";
import CitaScreen from "./modules/citas/components/CitaScreen";
import HorarioScreen from "./modules/horario/components/HorarioScreen";

import AdminNav from "./shared/components/AdminNav";
import VentanillaNav from "./shared/components/VentanillaNav";

import Main from "./modules/admin/Main";
import MainVentanilla from "./modules/ventanilla/MainVentanilla";
import UserMain from "./modules/admin/Components/UserMain";

import ServiceScreen from "./modules/servicios/ServiceScreen";
import Error from "./shared/plugins/Error";
import Animation from "./shared/plugins/Animation";
import HoraVentScreen from "./modules/horario/HoraVentScreen";
import AdminRoutes from "./modules/routes/AdminRoutes";
import VentanillaRoutez from "./modules/routes/VentanillaRoutez.jsx";
import ForgetPass from "./modules/auth/ForgetPass";

function App() {
  useEffect(() => {
    document.title = "CITAT"; // Cambia el nombre de la pestaña aquí
  }, []);
  return (
    <BrowserRouter>
      <Routes>
        {<Route path="/" element={<Login />} />}
        <Route path="/login" element={<Login />} />
        {/*<Route path="/changePassword" element={<ForgetPass />} />*/}
        <Route path="/adminDashboard/*" element={<AdminRoutes />} />
        <Route path="/ventanillaDashBoard/*" element={<VentanillaRoutez />} />
        <Route path="/*" element={<Error />} />
      </Routes>
    </BrowserRouter>
    /*<Login/>*/
    /*Admin
    <BrowserRouter>
      <AdminNav />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/home" element={<Main />} />
        <Route path="/service" element={<ServiceScreen />} />
        <Route path="/user" element={<UserMain />} />
        <Route path="/user/admin" element={<AdminScreen />} />
        <Route path="/user/ventanilla" element={<VentanillaScreen />} />
        <Route path="/user/solicitante" element={<SolicitanteScreen />} />
        <Route path="/*" element={<Error/>} />
      </Routes>
      <Animation/>
    </BrowserRouter>*/

    /*Ventanilla 
    <BrowserRouter>
      <VentanillaNav />
      <Routes>
        <Route path="/" element={<MainVentanilla />} />
        <Route path="/homeVentanilla" element={<MainVentanilla />} />
        <Route path="/cita" element={<CitaScreen />} />
        <Route path="/horario" element={<HoraVentScreen />} />
        <Route path="/*" element={<Error/>} />
      </Routes>
      <Animation/>
    </BrowserRouter>*/
  );
}

export default App;
