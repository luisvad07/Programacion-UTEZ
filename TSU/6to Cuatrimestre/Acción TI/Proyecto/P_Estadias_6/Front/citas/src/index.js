import React, { useEffect } from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import App from "./App";
import Login from "./modules/auth/Login";
import AdminRoutes from "./modules/routes/AdminRoutes";
import VentanillaRoutez from "./modules/routes/VentanillaRoutez";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
