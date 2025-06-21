import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';// Importa el hook useHistory para la navegación
import { FaUserTie, FaUserGraduate, FaUserShield } from 'react-icons/fa';
import '../../../shared/plugins/MainThree.css'

const UserMain = () => {
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

    const handleVenta = () => {
        navigate('ventanilla');
    };
    const handleSoli = () => {
        navigate('solicitante');
    };

    const handleAdmin = () => {
        navigate('admin');
    };
    return (
        <>
            <div className="main-screen" style={{ marginTop: '-70px', position: 'relative', zIndex: 1 }}>
                <div className="circle-wrapper">
                    <div className="circle">
                        <div className="circle-icon-wrapper mb-4">
                            <div className="inner-circle">
                                <FaUserShield className="circle-icon" />
                            </div>
                        </div>
                        <button className="btn" onClick={handleAdmin}>
                            Administrador
                        </button>
                    </div>
                </div>
                <div className="circle-wrapper">
                    <div className="circle">
                        <div className="circle-icon-wrapper mb-4">
                            <div className="inner-circle">
                                <FaUserGraduate className="circle-icon" />
                            </div>
                        </div>
                        <button className="btn" onClick={handleSoli}>
                            Solicitante
                        </button>
                    </div>
                </div>
                <div className="circle-wrapper">
                    <div className="circle">
                        <div className="circle-icon-wrapper mb-4">
                            <div className="inner-circle">
                                <FaUserTie className="circle-icon" />
                            </div>
                        </div>
                        <button className="btn" onClick={handleVenta}>
                            Ventanilla
                        </button>
                    </div>
                </div>

            </div>
        </>

    );
}

export default UserMain