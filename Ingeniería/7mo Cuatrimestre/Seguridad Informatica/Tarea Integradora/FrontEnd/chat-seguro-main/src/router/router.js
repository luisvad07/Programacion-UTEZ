import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import CustomNavbar from "../components/navbar/CustomNavbar";
import Login from "../components/usuarios/login";
import { AuthContext } from "../services/auth/context/AuthContext";
import Menu from "../components/menu/menu";
export default function Router() {
  const { userInfo, splashLoading } = React.useContext(AuthContext);
  return (
    <BrowserRouter>
      {splashLoading ? (
        <div className="d-flex justify-content-center align-items-center vh-100">
          <div className="spinner-border text-success" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      ) : userInfo.access_token ? (
        <>
          <CustomNavbar />
          <Routes>
            <Route
              path="/"
              element={<Menu />}
            />
            <Route
              path="*"
              element={<h1 className="text-center">404 no encontrado</h1>}
            />
          </Routes>
        </>

      // ) : userInfo.username=== "admin" ? (
      //   <>
      //     <CustomNavbar />
      //     <Routes>
      //       <Route
      //         path="/"
      //         element={<Menu />}
      //       />
      //       <Route
      //         path= "/gestionUsuarios"
      //         element={<GestionUsuarios />}
      //       />
      //       <Route
      //         path="*"
      //         element={<h1 className="text-center">404 no encontrado</h1>}
      //       />
      //     </Routes>
      //   </>
      ) : (
        <Routes>
          <Route index path="/" element={<Login />} />
          <Route
            path="*"
            element={<h1 className="text-center">404 no encontrado</h1>}
          />
        </Routes>
      )}
    </BrowserRouter>
  );
}
