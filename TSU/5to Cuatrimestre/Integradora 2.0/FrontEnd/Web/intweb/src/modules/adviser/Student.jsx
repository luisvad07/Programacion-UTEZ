import React, { useEffect, useState } from 'react'
import axios from 'axios';
import FeatherIcon from "feather-icons-react";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { Button, Form, FormControl } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal'
import Table from 'react-bootstrap/Table';
import InputGroup from 'react-bootstrap/InputGroup';
import { FaUserAlt, FaCircle, FaUserCircle } from "react-icons/fa";
import { show_alerta } from '../../shared/components/functions';
import Swal from 'sweetalert2';
import ModalStudent from './Components/ModalStudent';
import { localhost } from '../../shared/plugins/axios'
import { useNavigate } from 'react-router-dom';

export const Student = () => {
    const sesionId = localStorage.getItem('sesionId');
    const rol = localStorage.getItem('rol');

    const navigate = useNavigate();
    const url = `http://${localhost}:8080/redre/estudiante/asesor/${sesionId}`
    const urlPost = `http://${localhost}:8080/redre/estudiante/`
    const [estudiante, setEstudiante] = useState([]);
    const [asesor, setAsesor] = useState([]);

    const [idStudends, setIdStudens] = useState('')
    const [id, setId] = useState('');
    const [matricula, setMatricula] = useState('');
    const [nombre, setNombre] = useState('');
    const [apellidos, setApellidos] = useState('');
    const [correo, setCorreo] = useState('');
    const [password, setPassword] = useState('');
    const [divisionAcademica, setDivisionAcademica] = useState('');
    const [carrera, setCarrera] = useState('');
    const [grado, setGrado] = useState('');
    const [grupo, setGrupo] = useState('');
    const [status, setStatus] = useState('');
    const [urlReporte, setUrlReporte] = useState('');
    const [reportStatus, setReportStatus] = useState('');
    const [asesorId, setAsesorId] = useState('');

    const [isLoading, setIsLoading] = useState('');
    const [mode, setMode] = useState('');

    useEffect(() => {
        comporbarSesion();
        cargarEstudiantes();
        cargarAsesores();
    }, []);

    const comporbarSesion = () => {
        if (sesionId === null || rol != 'asesor') {
            navigate('/login');
        }
    }

    const cargarEstudiantes = async () => {
        const respuesta = await axios.get(url);
        setEstudiante(respuesta.data.obj)
    };

    const cargarAsesores = async () => {
        const respuesta = await axios.get(`http://${localhost}:8080/redre/asesor/`);
        setAsesor(respuesta.data.obj)
    };

    const [title, setTitle] = useState('');
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);

    const handleShow = (mode, id, matricula, nombre, apellidos, correo, carrera, grado, grupo, divisionAcademica, urlReporte, reportStatus) => {
        setId('');
        setMatricula('');
        setNombre('');
        setApellidos('');
        setCorreo('');
        setCarrera('');
        setGrado('');
        setGrupo('');
        setDivisionAcademica('');
        setPassword('');
        setStatus(true);
        setReportStatus('');
        setUrlReporte('');
        setMode(mode);
        if (mode === "add") {
            setTitle('Registrar estudiante');
        } else if (mode === "edit") {
            setTitle('Editar estudiante');
            setId(id);
            setMatricula(matricula);
            setNombre(nombre);
            setApellidos(apellidos);
            setCorreo(correo);
            setCarrera(carrera);
            setGrado(grado);
            setGrupo(grupo);
            setDivisionAcademica(divisionAcademica);
            setUrlReporte(urlReporte);
            setReportStatus(reportStatus);
            setAsesorId(sesionId);
            setStatus(true);
        }
        /*window.setTimeout(function(){
            document.getElementById(`nombre`).focus();
        },500);*/
        setShow(true);
    }

    const validar = (e) => {
        var parametros;
        var metodo;
        var modo = mode; // Agregar variable local para guardar mode
        if (![matricula, nombre, apellidos, correo, carrera, grado, grupo, password, divisionAcademica].every(field => field !== '')) {
            show_alerta('Llena todos los campos', 'warning');
        } else {
            if (modo === "add") {
                parametros = {
                    matricula: matricula.trim(),
                    nombre: nombre.trim(), apellidos: apellidos.trim(),
                    correo: correo.trim(), carrera: carrera.trim(), grado: grado,
                    grupo: grupo.trim(), divisionAcademica: divisionAcademica.trim(), password: password.trim(), status: status, asesor: { id: sesionId}
                };
                metodo = 'POST';
            } else {
                parametros = {
                    id: id,
                    matricula: matricula.trim(),
                    nombre: nombre.trim(), apellidos: apellidos.trim(),
                    correo: correo.trim(), carrera: carrera.trim(), grado: grado,
                    grupo: grupo.trim(), divisionAcademica: divisionAcademica.trim(), password: password.trim(), status: status, urlReporte: urlReporte, 
                    reportStatus: reportStatus, asesor: { id: sesionId}
                };
                metodo = 'PUT';
            }
            enviarSolicitud(metodo, parametros);
        }

    }

    const enviarSolicitud = async (metodo, parametros) => {
        if (metodo === 'PUT') {
            await axios({ method: metodo, url: `http://localhost:8080/redre/estudiante/${id}`, data: parametros })
                .then(function (respuesta) {

                    var hasError = respuesta.data.status;
                    var msj = respuesta.data.message;
                    show_alerta(msj, hasError ? 'error' : 'success');
                    if (hasError === false) {
                        cargarEstudiantes();
                        handleClose();
                    }
                })
                .catch(function (error) {
                    show_alerta('Error en la solicitud', 'error');
                    console.log(error);
                });
        } else {
            await axios({ method: metodo, url: urlPost, data: parametros })
                .then(function (respuesta) {

                    var hasError = respuesta.data.status;
                    var msj = respuesta.data.message;
                    show_alerta(msj, 'success');
                    if (hasError === false) {
                        cargarEstudiantes();
                        handleClose();
                    }
                })
                .catch(function (error) {
                    show_alerta('Error en la solicitud', 'error');
                    console.log(error);
                });
        }
    }


    /// VALIDAR CON DEFAULT
    const [validated, setValidated] = useState(false);

    const handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }

        setValidated(true);
    };

    const [pass, setPass] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    const togglePassword = () => {
        setShowPassword(!showPassword);
    };

    return (
        <>
            <Container >
                <br />
                <Row>
                    <Col><h1>Asesor</h1></Col>
                </Row>
                <hr className="linea" />
                <Row>
                    <Container >
                        <Row>
                            <Col xs={4} className="text-center pt-1 pb-1">
                                <h5 className="recuadro" style={{ textAlign: "center" }}>
                                    Lista de estudiantes
                                </h5>
                            </Col>
                            <Col xs={7}></Col>
                            <Col className="text-center pt-1 pb-1">
                                <Button
                                    variant="secondary"
                                    className="btn btn-accion gradient-custom-2 "
                                    type="submit"
                                    //onClick={() => handleShow("add")}
                                    onClick={() => handleShow("add")}
                                    style={{ background: '#3D9982' }}
                                //disabled={!(formik.isValid && formik.dirty)}
                                >
                                    <FeatherIcon icon={"plus"} style={{ stroke: "white" }} />
                                </Button>
                            </Col>
                        </Row>
                        <br />
                        <Row style={{ margin: '5px' }}>
                            <Table className='table styeleTable' style={{ background: 'white' }}>
                                <thead >
                                    <tr>
                                        <th></th>
                                        <th>Matrícula</th>
                                        <th>Nombre </th>
                                        <th>Apellidos </th>
                                        <th>Correo</th>
                                        <th>Carrera</th>
                                        <th>Grado y grupo</th>
                                        <th>DA</th>
                                        {/*<th>Status</th>*/}
                                        {/*<th>Contraseña</th>*/}
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody >
                                    {estudiante.length === 0 ? (
                                        <tr>
                                            <td colSpan="9" style={{ textAlign: 'center' }}>No hay registros</td>
                                        </tr>
                                    ) : (
                                        estudiante.map((estudiante, index) => (
                                            <tr key={index}>
                                                <td style={{ textAlign: 'center' }}><FaCircle style={{ color: '#3D9982' }} /></td>
                                                <td>{estudiante.matricula}</td>
                                                <td>{estudiante.nombre}</td>
                                                <td>{estudiante.apellidos}</td>
                                                <td>{estudiante.correo}</td>
                                                <td>{estudiante.carrera}</td>
                                                <td>{estudiante.grado}{estudiante.grupo}</td>
                                                <td>{estudiante.divisionAcademica}</td>
                                                {/*<td>{estudiante.status}</td>*/}
                                                {/*<td>{estudiante.password}</td>*/}
                                                <td>
                                                    <Button
                                                        style={{ background: '#3D9982' }}
                                                        onClick={() => handleShow('edit', estudiante.id, estudiante.matricula, estudiante.nombre, estudiante.apellidos,
                                                            estudiante.correo, estudiante.carrera, estudiante.grado,
                                                            estudiante.grupo, estudiante.divisionAcademica, estudiante.urlReporte, estudiante.reportStatus
                                                        )}>
                                                        <FeatherIcon icon={"edit"} style={{ stroke: "white" }} />
                                                    </Button>
                                                </td>
                                            </tr>
                                        ))
                                    )}
                                </tbody>
                            </Table>
                        </Row>
                    </Container>
                </Row >
            </Container >

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton className='titleModal'>
                    <Modal.Title>{title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row className="mb-4" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                        <FaUserCircle style={{ color: '#3D9982', fontSize: '50px' }} />
                    </Row>
                    <Form /*noValidate validated={validated}*/ onSubmit={handleSubmit}>
                        <Row className="mb-3">
                            <Form.Group as={Col} md="6" controlId="validationCustom01">
                                <Form.Label style={{ color: '#012258' }}><b>Nombre(s)</b></Form.Label>
                                <Form.Control
                                    required
                                    type="text"
                                    value={nombre} onChange={(e) => setNombre(e.target.value)}
                                //placeholder="First name"
                                />
                                {/*<Form.Control.Feedback>Completado</Form.Control.Feedback>*/}
                            </Form.Group>
                            <Form.Group as={Col} md="6" controlId="validationCustom02">
                                <Form.Label style={{ color: '#012258' }}><b>Apellido(s)</b></Form.Label>
                                <Form.Control
                                    required
                                    type="text"
                                    value={apellidos} onChange={(e) => setApellidos(e.target.value)}
                                //placeholder="Last name"
                                />

                            </Form.Group>
                        </Row>
                        <Row className="mb-3">
                            <Form.Group as={Col} md="6" controlId="validationCustom03">
                                <Form.Label style={{ color: '#012258' }}><b>Correo</b></Form.Label>
                                <Form.Control type="text" required
                                    value={correo} onChange={(e) => setCorreo(e.target.value)} />
                            </Form.Group>
                            <Form.Group as={Col} md="6" controlId="validationCustom04">
                                <Form.Label style={{ color: '#012258' }}><b>Carrera</b></Form.Label>
                                <Form.Control type="text" required
                                    value={carrera} onChange={(e) => setCarrera(e.target.value)}
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            <Form.Group as={Col} md="3" controlId="validationCustom05">
                                <Form.Label style={{ color: '#012258' }}><b>Grado</b></Form.Label>
                                <Form.Control type="text" required
                                    value={grado} onChange={(e) => setGrado(e.target.value)}
                                />
                            </Form.Group>
                            <Form.Group as={Col} md="3" controlId="validationCustom06">
                                <Form.Label style={{ color: '#012258' }}><b>Grupo</b></Form.Label>
                                <Form.Control type="text" required
                                    value={grupo} onChange={(e) => setGrupo(e.target.value)}
                                />
                            </Form.Group>
                            <Form.Group as={Col} md="6" controlId="validationCustom07">
                                <Form.Label style={{ color: '#012258' }}><b>División Académica</b></Form.Label>
                                <Form.Control type="text" required
                                    value={divisionAcademica} onChange={(e) => setDivisionAcademica(e.target.value)}
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            <Form.Group as={Col} md="6" controlId="validationCustom08">
                            <Form.Label style={{ color: '#012258' }}><b>Matricula</b></Form.Label>
                                <Form.Control type="text" required
                                    value={matricula} onChange={(e) => setMatricula(e.target.value)}
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-4">
                        <Form.Group as={Col} md="6" controlId="validationCustom09">
                                <Form.Label style={{ color: '#012258' }}><b>Contraseña</b></Form.Label>
                                <Form.Control type={showPassword ? "text" : "password"} required
                                 onChange={(e) => setPassword(e.target.value)}
                                />
                            </Form.Group>
                            <Form.Group as={Col} md="1" controlId="validationCustom10">
                                <Form.Label style={{ color: 'white' }}>P</Form.Label>
                                <Button className="btn border border-1" style={{ backgroundColor: "#375689", borderLeft: "none" }} onClick={togglePassword}>
                                    <FeatherIcon style={{ stroke: 'white' }} icon={showPassword ? 'eye-off' : 'eye'} />
                                </Button>
                            </Form.Group>
                        </Row>

                        <Button className='me-2' variant='outline-danger' onClick={handleClose}>
                            <FeatherIcon icon='x' />&nbsp;Cerrar
                        </Button>

                        <Button variant="outline-success" type='button' onClick={() => validar()}>
                            <FeatherIcon icon='check' />&nbsp;Guardar
                        </Button>
                    </Form>
                </Modal.Body>
                {/*<Modal.Footer>
                    <Button className='me-2' variant='outline-danger' onClick={handleClose}>
                        <FeatherIcon icon='x' />&nbsp;Cerrar
                    </Button>
                    <Button type='submit' variant='outline-success'>
                        <FeatherIcon icon='check' />&nbsp;Guardar
                </Button>
                                        </Modal.Footer>*/}
            </Modal>

        </>
    )
}

export default Student