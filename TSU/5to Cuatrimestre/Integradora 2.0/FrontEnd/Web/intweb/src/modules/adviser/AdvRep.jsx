import React, { useEffect, useState } from 'react'
import axios from 'axios';
import FeatherIcon from "feather-icons-react";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { Button, Form, FormControl } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal'
import Table from 'react-bootstrap/Table';
import { localhost } from '../../shared/plugins/axios'
import { show_alerta } from '../../shared/components/functions';
import { Navigate, useNavigate } from 'react-router-dom';

import { FaCircle } from "react-icons/fa";

export const AdvRep = () => {
    const rol = localStorage.getItem('rol');
    const navigate = useNavigate();
    const [estudiante, setEstudiante] = useState([]);
    const [sessionId, setSessionId] = useState(localStorage.getItem('sesionId'));
    const url = `http://${localhost}:8080/redre/estudiante/asesor/${sessionId}/hasReport`

    const [show, setShow] = useState(false);

    /*const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);*/

    const [showValidacionModal, setShowValidacionModal] = useState(false);
    const [showRetroalimentacionModal, setShowRetroalimentacionModal] = useState(false);
    const [mensaje, setMensaje] = useState('');
    const [estudianteMatricula, setMatricula] = useState('');
    const [estudianteId, setEstudianteId] = useState('');

    useEffect(() => {
        comporbarSesion();
        cargarEstudiantes();
    }, []);

    const comporbarSesion = () => {
        if (sessionId === null || rol != 'asesor') {
            navigate('/login');
        }
    }

    const cargarEstudiantes = async () => {
        const respuesta = await axios.get(url);
        setEstudiante(respuesta.data.obj)
    };

    const handleClose = () => {
        setShowValidacionModal(false);
        setShowRetroalimentacionModal(false);
    };

    const handleShow = (modal, matricula) => {
        if (modal === 'validacion') {
            setMatricula(matricula);
            setShowValidacionModal(true);
        } else if (modal === 'retroalimentacion') {
            setMatricula(matricula);
            setShowRetroalimentacionModal(true);
        }
    };

    const aprobar = async () => {
        console.log(estudianteId);
        axios.put(`http://${localhost}:8080/redre/mensajesAsesor/aprobado/${estudianteMatricula}`)
        .then((response) => {
            console.log(response);
            show_alerta('Reporte Aprobado', 'success');
            handleClose();
            cargarEstudiantes();
        });
    }

    const rechazarReporte = () => {
        var parametros;
        let fecha = new Date().toISOString();

        parametros = {
            mensaje: mensaje,
            fechaEnvio: fecha,
            status: false,
            nombre: estudianteMatricula,
            nombreA: localStorage.getItem('nombre')
        }
        enviarMensaje(parametros)
    }

    const enviarMensaje = async (parametros)  => {
        const url = `http://${localhost}:8080/redre/mensajesAsesor/As/`
        const respuesta = await axios.post(url, parametros);
        cargarEstudiantes();
        handleClose();
        show_alerta('Reporte Rechazado\nSe envio la retroalimentación al Estudiante', 'success');
    }


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
                                    Lista de reportes de estadía
                                </h5>
                            </Col>
                        </Row>
                        <br />
                        <Row style={{ margin: '5px' }}>
                            <Table className='table styeleTable' style={{ background: 'white' }}>
                                <thead >
                                    <tr>
                                        <th></th>
                                        <th>Nombre </th>
                                        <th>Correo </th>
                                        <th>Carrera</th>
                                        <th>Grado y grupo</th>
                                        <th></th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {estudiante.length === 0 ? (
                                        <tr>
                                            <td colSpan="9" style={{ textAlign: 'center' }}>No hay Reportes Por Revisar</td>
                                        </tr>
                                    ) : (
                                        estudiante.map((estudianten, index) => (
                                            
                                            <tr key={index}>
                                                <td style={{ textAlign: 'center' }}><FaCircle style={{ color: '#3D9982' }} /></td>
                                                <td>{estudianten.nombre}</td>
                                                <td>{estudianten.correo}</td>
                                                <td>{estudianten.carrera}</td>
                                                <td>{estudianten.grado}{estudianten.grupo}</td>
                                                <td>
                                                    <Button style={{ background: '#012258' }} href={estudianten.urlReporte} disabled={estudianten.urlReporte === null}>
                                                        <FeatherIcon icon={"eye"} style={{ stroke: "white" }} />
                                                    </Button>
                                                </td>
                                                <td>
                                                    <Button style={{ background: '#3D9982' }} onClick={() => handleShow('validacion', estudianten.matricula)}>
                                                        <FeatherIcon icon={"check-circle"} style={{ stroke: "white" }} />
                                                    </Button>
                                                </td>
                                                <td>
                                                    <Button className='btn-danger' onClick={() => handleShow('retroalimentacion', estudianten.matricula)}>
                                                        <FeatherIcon icon={"x-circle"} style={{ stroke: "white" }} />
                                                    </Button>
                                                </td>
                                            </tr>

                                        ))
                                    )}

                                </tbody>
                            </Table>
                        </Row>
                    </Container>
                </Row>


            </Container>

            <Modal show={showValidacionModal} onHide={handleClose}>
                <Modal.Header closeButton className='titleModal'>
                    <Modal.Title>Validación</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>¿Está seguro de validar el reporte?</p>
                </Modal.Body>
                <Modal.Footer>
                    <Button className='me-2' variant='outline-danger' onClick={handleClose}>
                        <FeatherIcon icon='x' />&nbsp;Cancelar
                    </Button>
                    <Button type='submit' variant='outline-success' onClick={aprobar}>
                        <FeatherIcon icon='check' />&nbsp;Aceptar
                    </Button>
                </Modal.Footer>
            </Modal>

            <Modal show={showRetroalimentacionModal} onHide={handleClose}>
                <Modal.Header closeButton className='titleModal'>
                    <Modal.Title>Retroalimentación</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group
                            className="mb-3"
                            controlId="exampleForm.ControlTextarea1"
                        >
                            <Form.Label>Añade un Comentario</Form.Label>
                            <Form.Control as="textarea" rows={3} onChange={(e) => {setMensaje(e.target.value)}} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button className='me-2' variant='outline-danger' onClick={handleClose}>
                        <FeatherIcon icon='x' />&nbsp;Cancelar
                    </Button>
                    <Button type='submit' variant='outline-success' onClick={() => rechazarReporte()}>
                        <FeatherIcon icon='check' />&nbsp;Aceptar
                    </Button>
                </Modal.Footer>
            </Modal>


        </>
    )
}

export default AdvRep