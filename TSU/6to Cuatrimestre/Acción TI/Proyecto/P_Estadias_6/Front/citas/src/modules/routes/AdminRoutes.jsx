import React from 'react'
import { BrowserRouter, Route, Router, Routes } from 'react-router-dom';
import AdminNav from '../../shared/components/AdminNav';
import Main from '../admin/Main';
import ServiceScreen from '../servicios/ServiceScreen';
import UserMain from '../admin/Components/UserMain';
import AdminScreen from '../admin/Components/AdminScreen';
import VentanillaScreen from '../ventanilla/components/VentanillaScreen';
import SolicitanteScreen from '../solicitante/SolicitanteScreen';
import Error from '../../shared/plugins/Error';
import Animation from '../../shared/plugins/Animation';
import ProfileAdmin from '../admin/Components/ProfileAdmin';

const AdminRoutes = () => {
    return (
        <>
            <AdminNav />
            <Routes>
                <Route path="/" element={<Main />} />
                {/*<Route path="/home" element={<Main />} />*/}
                <Route path="/service" element={<ServiceScreen />} />
                <Route path="/profileAdmin" element={<ProfileAdmin />} />
                <Route path="/user" element={<UserMain />} />
                <Route path="/user/admin" element={<AdminScreen />} />
                <Route path="/user/ventanilla" element={<VentanillaScreen />} />
                <Route path="/user/solicitante" element={<SolicitanteScreen />} />
                <Route path="/*" element={<Error />} />
            </Routes>
            <Animation />
        </>
    )
}
export default AdminRoutes