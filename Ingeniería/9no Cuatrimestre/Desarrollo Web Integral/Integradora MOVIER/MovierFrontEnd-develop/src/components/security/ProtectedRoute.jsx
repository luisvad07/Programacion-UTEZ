import React from "react";
import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ element: Element, allowedRoles }) {
  const role = localStorage.getItem("role");
  const isAuthenticated = Boolean(localStorage.getItem("idToken"));

  if (!role && !isAuthenticated) {
    return <Navigate to={"/login"} />
  } else if (!allowedRoles.includes(role)) {
    return <Navigate to={"/forbidden"} />
  }

  return <Element />;
}
