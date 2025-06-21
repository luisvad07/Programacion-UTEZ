/*import React from 'react';
import ReactDOM from 'react-dom';*/
import { createRoot } from "react-dom/client";
import App from "./App";
import "@fortawesome/fontawesome-free/css/all.min.css";
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Login from './modules/auth/Login';
import DepartmentRoutes from './modules/routes/DepartmentRoutes';
import ResponsableRoutes from "./modules/routes/ResponsableRoutes";
import AsesorRoutes from "./modules/routes/AsesorRoutes";
import StudentRoutes from "./modules/routes/StudentRoutes";

const root = /*ReactDOM.*/ createRoot(document.getElementById("root"));
root.render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<Login/>} />
            <Route path="login" element={<Login />} />
            <Route path="adminDashboard/*" element={<DepartmentRoutes />} />
            <Route path="responsableDashboard/*" element={<ResponsableRoutes />} />
            <Route path="asesorDashboard/*" element={<AsesorRoutes />} />
            <Route path="studentDashboard/*" element={<StudentRoutes />} />
        </Routes>
    </BrowserRouter>
);
