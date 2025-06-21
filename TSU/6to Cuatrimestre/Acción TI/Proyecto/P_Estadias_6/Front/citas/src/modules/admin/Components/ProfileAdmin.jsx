import React, { useEffect, useState, } from 'react'
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Container, Modal, Card, Col, Row, Badge, Button, Form, InputGroup } from 'react-bootstrap'
import '../../../shared/plugins/Screens.css'
import Swal from 'sweetalert2';

import { FaUser, FaUserCog } from 'react-icons/fa';
import FeatherIcon from 'feather-icons-react/build/FeatherIcon'


const ProfileAdmin = () => {

    const navigate = useNavigate();

    const [admin, setAdmin] = useState([]);
    const [id, setId] = useState('');
    const [nombreAdmin, setNombreAdmin] = useState('');
    const [apeMaternoAdmin, setApellidoMaterno] = useState('');
    const [apePaternoAdmin, setApellidoPaterno] = useState('');
    const [correoAdmin, setCorreo] = useState('');
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

        const respuesta = await axios.get(`http://localhost:8080/api/administrador/${sessionId}`);

        datosResponsivos(
            respuesta.data.data.nombreAdmin,
            respuesta.data.data.apeMaternoAdmin,
            respuesta.data.data.apePaternoAdmin,
            respuesta.data.data.correoAdmin,
            respuesta.data.data.pass,
            respuesta.data.data.status)

        //console.log(respuesta.data.data.nombreAdmin)
        //console.clear()
    }

    const sesionActiva = () => {
        const id = localStorage.getItem("sesionId")
        const rol = localStorage.getItem("rol")

        if (id === null || rol !== 'admin') {
            navigate('/')
        }
    }

    const datosResponsivos = (nombreAdmin, apeMaternoAdmin, apePaternoAdmin, correoAdmin,
        pass, status) => {
        setNombreAdmin(nombreAdmin)
        setApellidoMaterno(apeMaternoAdmin)
        setApellidoPaterno(apePaternoAdmin)
        setCorreo(correoAdmin)
        setPass(pass)
        setStatus(status)
        setRol(localStorage.getItem('rol'))
    }

    ///Change Password
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);

    const changeStatus = (id, nombreAdmin, apePaternoAdmin, apeMaternoAdmin,
        correoAdmin, pass, status) => {
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
                    nombreAdmin: nombreAdmin,
                    apePaternoAdmin: apePaternoAdmin,
                    apeMaternoAdmin: apeMaternoAdmin,
                    correoAdmin: correoAdmin,
                    pass: pass,
                    status: status
                };
                axios.patch(`http://localhost:8080/api/administrador/changePassword`, data)
                    .then(function (respuesta) {
                        var hasError = respuesta.data.status;
                        var msj = respuesta.data.message;

                        Swal.fire({
                            icon: 'success',
                            iconColor: '#58BEC4',
                            title: msj,
                            text: `Administrador: ${nombreAdmin} ${apePaternoAdmin}`,
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
                            value={nombreAdmin} onChange={(e) => setNombreAdmin(e.target.value)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label style={{ color: '#2A4172' }}><b>Apellido Materno</b></Form.Label>
                        <Form.Control type="text" disabled
                            value={apePaternoAdmin} onChange={(e) => setApellidoPaterno(e.target.value)} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label style={{ color: '#2A4172' }}><b>Apellido Paterno</b></Form.Label>
                        <Form.Control
                            type="text"
                            disabled
                            value={apeMaternoAdmin}
                            onChange={(e) => setApellidoMaterno(e.target.value)}
                        />
                    </Form.Group>
                </Col>
                <Col xs={5} className="form-control-wrapper">
                    <Form.Group>
                        <Form.Label style={{ color: '#2A4172' }}><b>Correo Institucional</b></Form.Label>
                        <Form.Control type="text" disabled
                            value={correoAdmin} onChange={(e) => setCorreo(e.target.value)} />
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
                    onClick={() => changeStatus(id, nombreAdmin, apePaternoAdmin,
                        apeMaternoAdmin, correoAdmin, pass, status)}/*style={{ marginRight: '5px' }}*/>
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

export default ProfileAdmin