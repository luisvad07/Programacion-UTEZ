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
import { localhost } from '../../shared/plugins/axios'

export const Adviser = () => {
    const url = `http://${localhost}:8080/redre/asesor/`

    const sessionId = localStorage.getItem("sesionId")
    const [asesor, setAsesor] = useState([]);
    const [resp, setResp] = useState([]); ///responsables

    const [respId, setRespId] = useState('');
    const [id, setId] = useState('');
    const [nombre, setNombre] = useState('');
    const [apellidos, setApellidos] = useState('');
    const [correo, setCorreo] = useState('');
    const [password, setPassword] = useState('');
    const [divisionAcademica, setDivisionAcademica] = useState('');
    const [status, setStatus] = useState('');

    const [isLoading, setIsLoading] = useState('');
    const [mode, setMode] = useState('');

    useEffect(() => {
        cargarResponsables();
        cargarAsesores();
    }, []);

    const cargarAsesores = async () => {
        const respuesta = await axios.get(url);
        setAsesor(respuesta.data.obj)
    };

    const cargarResponsables = async () => {
        const respuesta = await axios.get(`http://${localhost}:8080/redre/responsable/`);
        setResp(respuesta.data.obj)
    };

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
            setTitle('Registrar asesor');
        } else if (mode === "edit") {
            setTitle('Editar asesor');
            setId(id);
            setNombre(nombre);
            setApellidos(apellidos);
            setCorreo(correo);
            setDivisionAcademica(divisionAcademica);
            setRespId(sessionId);
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
                    correo: correo.trim(), divisionAcademica: divisionAcademica.trim(), password: password.trim(), status: status, responsable: { id: sessionId }
                };
                metodo = 'POST';
            } else {
                parametros = {
                    id: id, nombre: nombre.trim(), apellidos: apellidos.trim(),
                    correo: correo.trim(), divisionAcademica: divisionAcademica.trim(), password: password.trim(), status: status, responsable: { id: sessionId }
                };
                metodo = 'PUT';
            }
            enviarSolicitud(metodo, parametros);
        }
    }

    const enviarSolicitud = async (metodo, parametros) => {
        if (metodo === 'PUT') {
            await axios({ method: metodo, url: `http://localhost:8080/redre/asesor/${id}`, data: parametros })
                .then(function (respuesta) {

                    var hasError = respuesta.data.status;
                    var msj = respuesta.data.message;
                    show_alerta(msj, 'success');
                    if (hasError === false) {
                        cargarAsesores();
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
                        cargarAsesores();
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
                    <Col><h1>Responsable</h1></Col>
                </Row>
                <hr className="linea" />
                <Row>
                    <Container >
                        <Row>
                            <Col xs={4} className="text-center pt-1 pb-1">
                                <h5 className="recuadro" style={{ textAlign: "center" }}>
                                    Lista de asesores
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
                                    {asesor.length === 0 ? (
                                        <tr>
                                            <td colSpan="9" style={{ textAlign: 'center' }}>No hay registros</td>
                                        </tr>
                                    ) : (
                                        asesor.map((asesores, index) => (
                                            <tr key={index}>
                                                <td style={{ textAlign: 'center' }}><FaCircle style={{ color: '#3D9982' }} /></td>
                                                <td>{asesores.nombre}</td>
                                                <td>{asesores.apellidos}</td>
                                                <td>{asesores.correo}</td>
                                                <td>{asesores.divisionAcademica}</td>
                                                {/*<td>{estudiante.status}</td>*/}
                                                {/*<td>{estudiante.password}</td>*/}
                                                <td>
                                                    <Button
                                                        style={{ background: '#3D9982' }}
                                                        onClick={() => handleShow('edit', asesores.id, asesores.nombre, asesores.apellidos,
                                                            asesores.correo, asesores.divisionAcademica, asesores.password
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
                        <Row className="mb-5">
                            <Form.Group as={Col} md="6" controlId="validationCustom05">
                                <Form.Label style={{ color: '#012258' }}><b>Contraseña</b></Form.Label>
                                <Form.Control type={showPassword ? 'text' : 'password'} required
                                    value={password}
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

export default Adviser