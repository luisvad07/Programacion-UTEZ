import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';// Importa el hook useHistory para la navegación
import { FaUser, FaFileSignature, FaRestroom, FaRegAddressBook } from 'react-icons/fa';
import '../../shared/plugins/MainThree.css'


const Main = () => {
  const navigate = useNavigate();

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

  const handleUserClick = () => {
    navigate('user');
  };

  const handleServiceClick = () => {
    navigate('service');
  };
  const handleProfile = () => {
    navigate('profileAdmin');
  };

  return (
    <>
      <div className="main-screen" style={{ marginTop: '-70px', position: 'relative', zIndex: 1 }}>
        <div className="circle-wrapper">
          <div className="circle">
            <div className="circle-icon-wrapper mb-4">
              <div className="inner-circle">
                <FaUser className="circle-icon" />
              </div>
            </div>
            <button className="btn" onClick={handleUserClick}>
              Usuario
            </button>
          </div>
        </div>
        <div className="circle-wrapper">
          <div className="circle">
            <div className="circle-icon-profile mb-4">
              <div className="inner-circle-profile">
                <FaRegAddressBook className="circle-icon" />
              </div>
            </div>
            <button className="btn" onClick={handleProfile}>
              Perfil
            </button>
          </div>
        </div>
        <div className="circle-wrapper">
          <div className="circle">
            <div className="circle-icon-wrapper mb-4">
              <div className="inner-circle">
                <FaFileSignature className="circle-icon" />
              </div>
            </div>

            <button className="btn" onClick={handleServiceClick}>
              Servicio
            </button>
          </div>
        </div>
      </div>
    </>
  );
}

export default Main