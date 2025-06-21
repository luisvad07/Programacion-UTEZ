import React, { useEffect, useState, } from 'react'
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Container, Modal, Card, Col, Row, Badge, Button, Form, InputGroup } from 'react-bootstrap'
import '../../../shared/plugins/Screens.css'
import Swal from 'sweetalert2';

import { FaUser, FaUserCog } from 'react-icons/fa';
import FeatherIcon from 'feather-icons-react/build/FeatherIcon'


const ProfileVentanilla = () => {

    const navigate = useNavigate();

    const [vetanilla, setVentanilla] = useState([]);
    const [administrador, setAdmin] = useState([]);

    const [id, setId] = useState('');
    const [nombreVent, setNombreVent] = useState('');
    const [apePaternoVent, setApePaternoVent] = useState('');
    const [apeMaternoVent, setApeMaternoVent] = useState('');
    const [correoElectronico, setCorreoElectronico] = useState('');
    const [pass, setPass] = useState('');
    const [status, setStatus] = useState('');
    const [changePassword, setChangePass] = useState('');
    const [rol, setRol] = useState('')

    useEffect(() => {
        sesionActiva();
        cargarDatos();
    }, [])

    let sessionId = null;

    const cargarDatos = async () => {
        sessionId = localStorage.getItem('sesionId');

        const respuesta = await axios.get(`http://localhost:8080/api/ventanillas/${sessionId}`);

        datosResponsivos(
            respuesta.data.data.nombreVent,
            respuesta.data.data.apePaternoVent,
            respuesta.data.data.apeMaternoVent,
            respuesta.data.data.correoElectronico,
            respuesta.data.data.pass,
            respuesta.data.data.status)

        //console.log(respuesta.data.data.nombreAdmin)
        //console.clear()
    }

    const sesionActiva = () => {
        const id = localStorage.getItem("sesionId")
        const rol = localStorage.getItem("rol")

        if (id === null || rol !== 'ventanilla') {
            navigate('/')
        }
    }

    const datosResponsivos = (nombreVent,apePaternoVent, apeMaternoVent,  correoElectronico,
        pass, status) => {
        setNombreVent(nombreVent)
        setApePaternoVent(apePaternoVent)
        setApeMaternoVent(apeMaternoVent)
        setCorreoElectronico(correoElectronico)
        setPass(pass)
        setStatus(status)
        setRol(localStorage.getItem('rol'))
    }

    ///Change Password
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);

    const changeStatus = (id,nombreVent,apePaternoVent, apeMaternoVent,  correoElectronico,
        pass, status) => {
        Swal.fire({
            title: '¿Deseas cambiar la contraseña?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#58BEC4',
            cancelButtonColor: '#264B99',
            confirmButtonText: 'Confirmar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                const data = {
                    id: id,
                    nombreVent: nombreVent,
                    apePaternoAdmin: apePaternoVent,
                    apeMaternoAdmin: apeMaternoVent,
                    correoAdmin: correoElectronico,
                    pass: pass,
                    status: status
                };
                axios.patch(`http://localhost:8080/api/ventanillas/changePassword`, data)
                    .then(function (respuesta) {
                        var hasError = respuesta.data.status;
                        var msj = respuesta.data.message;

                        Swal.fire({
                            icon: 'success',
                            iconColor: '#58BEC4',
                            title: msj,
                            showConfirmButton: false,
                            timer: 2000
                        });
                        if (hasError === false) {
                            handleClose();
                            cargarDatos();

                        }
                    })
                    .catch(function (error) {
                        Swal.fire({
                            icon: 'error',
                            iconColor: '#264B99',
                            title: 'Error en la petición',
                            showConfirmButton: false,
                            timer: 2000
                        });
                    })
                    .finally(function () {
                        handleClose();
                        cargarDatos();
                    });
            }
        });
    }

    ///
    const [showPassword, setShowPassword] = useState(false);
    const [showPasswordNew, setShowPasswordNew] = useState(false);

    const togglePassword = () => {
        setShowPassword((prevShowPassword) => !prevShowPassword);
    };
    const togglePasswordNew = () => {
        setShowPasswordNew((prevShowPasswordNew) => !prevShowPasswordNew);
    };

    return (
        <Container className="container-profile" style={{ marginTop: '-70px', position: 'relative', zIndex: 1 }}>
            <Row className="profile-container">
                <Col xs={2} >
                    <Row >
                        <Col xs={12} className="user-icon-wrapper">
                            <FaUserCog className="user-icon" />
                        </Col>
                    </Row>
                    <Row>
                        <Col className="badge-wrapper">
                            {status ? (
                                <Badge bg='success'>Activo</Badge>
                            ) : (
                                <Badge bg='danger'>Inactivo</Badge>
                            )}
                        </Col>
                    </Row>
                </Col>
                <Col xs={5} className="form-control-wrapper">

                    <Form.Group>
                        <Form.Label style={{ color: '#2A4172' }}><b>Nombre(s)</b></Form.Label>
                        <Form.Control type="text" disabled
                            value={nombreVent} onChange={(e) => setNombreVent(e.target.value)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label style={{ color: '#2A4172' }}><b>Apellido Paterno</b></Form.Label>
                        <Form.Control type="text" disabled
                            value={apePaternoVent} onChange={(e) => setApePaternoVent(e.target.value)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label style={{ color: '#2A4172' }}><b>Apellido Materno</b></Form.Label>
                        <Form.Control
                            type="text"
                            disabled
                            value={apeMaternoVent}
                            onChange={(e) => setApeMaternoVent(e.target.value)}
                        />
                    </Form.Group>
                </Col>
                <Col xs={5} className="form-control-wrapper">
                    <Form.Group>
                        <Form.Label style={{ color: '#2A4172' }}><b>Correo Institucional</b></Form.Label>
                        <Form.Control type="text" disabled
                            value={correoElectronico} onChange={(e) => setCorreoElectronico(e.target.value)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label style={{ color: '#2A4172' }}><b>Contraseña</b></Form.Label>
                        <div className="password-wrapper">
                            <Form.Control type={showPasswordNew ? "text" : "password"} required
                                value={pass} onChange={(e) => setPass(e.target.value)} />
                            <FeatherIcon
                                className="eye-icon"
                                icon={showPasswordNew ? 'eye-off' : 'eye'}
                                onClick={togglePasswordNew}
                            />
                        </div>
                    </Form.Group>

                    <button className="custom-button"
                        onClick={() => changeStatus(id, nombreVent, apePaternoVent,
                            apeMaternoVent, correoElectronico, pass, status)}/*style={{ marginRight: '5px' }}*/>
                        <FeatherIcon
                            icon='lock'
                            style={{ color: 'white', paddingRight: '5px', }}
                        />
                        Modificar contraseña
                    </button>
                </Col>
            </Row>
        </Container>


    )
}

export default ProfileVentanilla