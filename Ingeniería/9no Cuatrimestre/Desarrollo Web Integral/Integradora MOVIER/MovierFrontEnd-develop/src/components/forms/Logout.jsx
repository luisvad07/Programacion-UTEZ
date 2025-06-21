import React, { useEffect } from "react";
import Swal from "sweetalert2";

export default function Logout() {
  if(
    localStorage.getItem("accessToken") ||
    localStorage.getItem("refreshToken") ||
    localStorage.getItem("idToken") ||
    localStorage.getItem("role") ||
    localStorage.getItem("userId")
  ){
    Swal.fire({
      html: `
        <h3>Cerrando sesión</h3>
        <span class="spinner-border spinner-border-lg"></span>
      `,
      allowEscapeKey: false,
      allowOutsideClick: false,
      showConfirmButton: false,
      timer: 3000,
    }).then((reason) => {
      if(reason.dismiss === Swal.DismissReason.timer) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("idToken");
        localStorage.removeItem("role");
        localStorage.removeItem("userId");
        window.location.href = "/";
      }
    });
  } else {
    window.location.href = "/";
  }
  return (
    <>
    </>
  );
}
