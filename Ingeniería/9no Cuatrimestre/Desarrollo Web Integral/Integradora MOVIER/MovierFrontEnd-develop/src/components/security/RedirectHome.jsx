import React from "react";
import { Navigate } from "react-router-dom";

export default function RedirectHome() {
  const role = localStorage.getItem('role');

  if(role === "Administrador") {
    return <Navigate to="/admin" />
  } else if (role === "Usuario") {
    return <Navigate to="/movies" />
  } else {
    return <Navigate to="/login" />
  }
}
