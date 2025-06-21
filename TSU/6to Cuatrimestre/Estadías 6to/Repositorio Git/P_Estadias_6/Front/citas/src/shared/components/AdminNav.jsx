import React, { useState, useEffect } from 'react'
import { Link, NavLink, Outlet, useNavigate } from 'react-router-dom';
import '../../App.css'
import Swal from 'sweetalert2';
import { Navbar, Button, Container } from 'react-bootstrap';

import Logo from '../../assets/Logo.png';

const AdminNav = () => {

  useEffect(() => {
    sesionActiva();
  }, []);

  const sesionActiva = () => {
    const id = localStorage.getItem("sesionId")
    const rol = localStorage.getItem("rol")

    if (id === null || rol != 'admin') {
      navigate('/login');
    }
  }
  const [isOpen, setIsOpen] = useState(false);

  const navigate = useNavigate();

  const handleHomeClick = () => {
    navigate('/adminDashboard/');
  };

  const logOut = () => {
    // Mostrar alerta de confirmación antes de cerrar sesión
    Swal.fire({
      title: '¿Estás seguro de que quieres cerrar sesión?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
      confirmButtonColor: '#58BEC4',
      cancelButtonText: 'Cancelar',
      cancelButtonColor: '#264B99',
      reverseButtons: true,
    }).then((result) => {
      if (result.isConfirmed) {
        // Mostrar alerta de "Cerrando sesión" mientras se limpia el almacenamiento local
        Swal.fire({
          title: 'Cerrando sesión...',
          icon: 'info',
          showConfirmButton: false,
          allowOutsideClick: false,
        });

        // Limpiar el almacenamiento local después de un breve retraso para que el usuario vea la alerta de "Cerrando sesión"
        setTimeout(() => {
          localStorage.clear();
          // Ocultar la alerta de "Cerrando sesión" y redirigir a la página de inicio de sesión
          Swal.close();
          navigate('/login');
        }, 1500); // Puedes ajustar la duración de la alerta "Cerrando sesión" según tus preferencias
      }
    });
  };


  const toggle = () => setIsOpen(!isOpen);

  return (
    <>
      <Navbar className="custom-navbar custom-navbar-thick">
        <div className='container-fluid'>
          <Navbar.Brand onClick={handleHomeClick}>
            <div className='circle-logo'>
              <div className='circle-logo' >
                <img
                  alt="Logo"
                  src={Logo}
                  width="30"
                  height="30"
                  className="d-inline-block align-top"
                />
              </div>
            </div>
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="navbarScroll" />
          <Navbar.Collapse className="justify-content-between">
            <div className="navbar-text mx-auto" style={{ color: 'white', fontFamily: 'Arial', fontSize: '25px', fontWeight: 'bold' }}>
              Administrador
            </div>
            <button className="Btn" onClick={logOut}>
              <div className="sign"><svg viewBox="0 0 512 512">
                <path d="M377.9 105.9L500.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L377.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1-128 0c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM160 96L96 96c-17.7 0-32 14.3-32 32l0 256c0 17.7 14.3 32 32 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32l-64 0c-53 0-96-43-96-96L0 128C0 75 43 32 96 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32z"></path></svg>
              </div>
              <div className="text">Logout</div>
            </button>
          </Navbar.Collapse>
        </div>
      </Navbar>
    </>
  )
}

export default AdminNav
