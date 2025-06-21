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
import { FaUserAlt, FaCircle } from "react-icons/fa";
import { show_alerta } from '../../../shared/components/functions';
import { FaUserCircle } from "react-icons/fa";
import { localhost } from '../../../shared/plugins/axios';
import { Loading } from '../../../shared/plugins/Loading';
import { useNavigate } from 'react-router-dom';


export const Responsable = () => {
    const url = `http://${localhost}:8080/redre/responsable/`
    const [responsable, setResp] = useState([]);
    const [administrador, setAdmin] = useState([]);
    const [id, setId] = useState('');
    const [nombre, setNombre] = useState('');
    const [apellidos, setApellidos] = useState('');
    const [correo, setCorreo] = useState('');
    const [password, setPassword] = useState('');
    const [divisionAcademica, setDivisionAcademica] = useState('');
    const [status, setStatus] = useState('');
    const [depId, setDepId] = useState(localStorage.getItem('sesionId'));

    const [isLoading, setIsLoading] = useState('');
    const [mode, setMode] = useState('');

    const navigate = useNavigate();

    useEffect(() => {
        cargarResponsables();
        cargarAdmin();
        sesionActiva();
    }, []);

    const cargarResponsables = async () => {
        const respuesta = await axios.get(url);
        setResp(respuesta.data.obj)
    };

    const cargarAdmin = async () => {
        const respuesta = await axios.get(`http://${localhost}:8080/redre/departamento/`);
        setAdmin(respuesta.data.obj)
    };

    const sesionActiva = () => {
        const id = localStorage.getItem("sesionId")
        const rol = localStorage.getItem("rol")

        if (id===null || rol!=='departamento') {
            navigate('/')
        }
    }

    /*
    FINCIONAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    useEffect(() => {
        axios.get(url)
            .then((response) => {
                console.log(response.data);
                setEstudiante(response.data.obj);
                setIsLoading(false);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);*/

    //console.log(estudiante);

    const [title, setTitle] = useState('');

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);

    const handleShow = (mode, id, nombre, apellidos, correo, divisionAcademica, password) => {
        setId('');
        setNombre('');
        setApellidos('');
        setCorreo('');
        setDivisionAcademica('');
        setPassword('');
        setStatus(true);
        setMode(mode);
        if (mode === "add") {
            setTitle('Registrar responsable');
        } else if (mode === "edit") {
            setTitle('Editar responsable');
            setId(id);
            setNombre(nombre);
            setApellidos(apellidos);
            setCorreo(correo);
            setDivisionAcademica(divisionAcademica);
            setDepId(depId);
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
        if (![nombre, apellidos, correo, password, divisionAcademica].every(field => field !== '')) {
            show_alerta('Llena todos los campos', 'warning');
        } else {
            if (modo === "add") {
                parametros = {
                    nombre: nombre.trim(), apellidos: apellidos.trim(),
                    correo: correo.trim(), divisionAcademica: divisionAcademica.trim(), password: password.trim(), status: status, departamento: { id: depId }
                };
                metodo = 'POST';
            } else {
                parametros = {
                    id: id, nombre: nombre.trim(), apellidos: apellidos.trim(),
                    correo: correo.trim(), divisionAcademica: divisionAcademica.trim(), password: password.trim(), status: status, departamento: { id: depId }
                };
                metodo = 'PUT';
            }
            enviarSolicitud(metodo, parametros);
        }
    }

    const enviarSolicitud = async (metodo, parametros) => {
        if (metodo === 'PUT') {
            await axios({ method: metodo, url: `http://localhost:8080/redre/responsable/${id}`, data: parametros })
                .then(function (respuesta) {

                    var hasError = respuesta.data.status;
                    var msj = respuesta.data.message;
                    show_alerta(msj, 'success');
                    if (hasError === false) {
                        cargarResponsables();
                        handleClose();
                    }
                })
                .catch(function (error) {
                    show_alerta('Error en la solicitud', 'error');
                    handleClose();
                    console.log(error);
                });
        } else {
            await axios({ method: metodo, url: url, data: parametros })
                .then(function (respuesta) {

                    var hasError = respuesta.data.status;
                    var msj = respuesta.data.message;
                    show_alerta(msj, 'success');
                    if (hasError === false) {
                        cargarResponsables();
                        handleClose();
                    }
                })
                .catch(function (error) {
                    show_alerta('Error en la solicitud', 'error');
                    handleClose();
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
                    <Col><h1>Departamento de estadías</h1></Col>
                </Row>
                <hr className="linea" />
                <Row>
                    <Container >
                        <Row>
                            <Col xs={4} className="text-center pt-1 pb-1">
                                <h5 className="recuadro" style={{ textAlign: "center" }}>
                                    Lista de responsables
                                </h5>
                            </Col>
                            <Col xs={7}></Col>
                            <Col className="text-center pt-1 pb-1">
                                <Button
                                    variant="secondary"
                                    className="btn btn-accion gradient-custom-2 "
                                    type="submit"
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
                                        <th>Nombre </th>
                                        <th>Apellidos </th>
                                        <th>Correo</th>
                                        <th>DA</th>
                                        {/*<th>Status</th>*/}
                                        {/*<th>Contraseña</th>*/}
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody >
                                    {responsable.length === 0 ? (
                                        <tr>
                                            <td colSpan="9" style={{ textAlign: 'center' }}>No hay registros</td>
                                        </tr>
                                    ) : (
                                        responsable.map((resp, index) => (
                                            <tr key={index}>
                                                <td style={{ textAlign: 'center' }}><FaCircle style={{ color: '#3D9982' }} /></td>
                                                <td>{resp.nombre}</td>
                                                <td>{resp.apellidos}</td>
                                                <td>{resp.correo}</td>
                                                <td>{resp.divisionAcademica}</td>
                                                <td>
                                                    <Button
                                                        style={{ background: '#3D9982' }}
                                                        onClick={() => handleShow('edit', resp.id, resp.nombre, resp.apellidos,
                                                            resp.correo, resp.divisionAcademica, resp.password
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
                    <Form /*noValidate validated={validated} */ onSubmit={handleSubmit}>
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
                                <Form.Label style={{ color: '#012258' }}><b>División Académica</b></Form.Label>
                                <Form.Control type="text" required
                                    value={divisionAcademica} onChange={(e) => setDivisionAcademica(e.target.value)}
                                />
                            </Form.Group>
                        </Row>
                        {/*
                        <Row className="mb-3">
                            <Form.Group as={Col} md="8" controlId="validationCustom06">
                                <Form.Label style={{ color: '#012258' }}><b>Departamento</b></Form.Label>
                                <Form.Select aria-label="Default select example" required onChange={(e) => setDepId(e.target.value)}>
                                    <option>Lista de Departamentos</option>
                                    {administrador.length === 0 ? (
                                        <option>No hay Departamentos</option>
                                    ) : (

                                        administrador.map((admin, index) => (
                                            <option key={index} value={admin.id}>{admin.nombre} {admin.apellidos}</option>
                                        ))
                                    )}
                                </Form.Select>
                            </Form.Group>
                        </Row>*/}
                            
                        <Row className="mb-5">
                            <Form.Group as={Col} md="6" controlId="validationCustom05">
                            <Form.Label style={{ color: '#012258' }}><b>Contraseña</b></Form.Label>
                                <Form.Control type={showPassword ? 'text' : 'password'} required
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </Form.Group>
                            <Form.Group as={Col} md="1" controlId="validationCustom06">
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
export default Responsable