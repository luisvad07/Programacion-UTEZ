import React, { useEffect, useState } from 'react'
import axios from 'axios';
import FeatherIcon from "feather-icons-react";
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { Button, Form, FormControl } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal'
import Table from 'react-bootstrap/Table';

import { FaCircle } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';

export const Consulta = () => {
    const rolSession = localStorage.getItem('rol');
    const navigate = useNavigate();
    const [sessionId, setSessionId] = useState(localStorage.getItem('sesionId'));

    const [show, setShow] = useState(false);

    const [showValidacionModal, setShowValidacionModal] = useState(false);
    const [showRetroalimentacionModal, setShowRetroalimentacionModal] = useState(false);

    useEffect(() => {
        comporbarSesion();
    }, []);

    const comporbarSesion = () => {
        if (sessionId === null || rolSession != 'departamento') {
            navigate('/login');
        }
    }
    const handleClose = () => {
        setShowValidacionModal(false);
        setShowRetroalimentacionModal(false);
    };

    const handleShow = (modal) => {
        if (modal === 'validacion') {
            setShowValidacionModal(true);
        } else if (modal === 'retroalimentacion') {
            setShowRetroalimentacionModal(true);
        }
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
                                    Consulta de reportes
                                </h5>
                            </Col>
                        </Row>
                        <br />
                        <Row style={{ margin: '5px' }}>
                            <Table className='table styeleTable' style={{ background: 'white' }}>
                                <thead >
                                    <tr>
                                        <th></th>
                                        <th>Estudiante </th>
                                        <th>Fecha de consulta </th>
                                        <th>Hora de consulta</th>
                                        <th>Reporte Consultado</th>
                                        {/*<th></th>
                                        <th></th>
    <th></th>*/}
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td style={{ textAlign: 'center' }}><FaCircle style={{ color: '#3D9982' }} /></td>
                                        <td>Andrea Michelle Estrada Hernández</td>
                                        <td>20213tn011@utez.edu.mx</td>
                                        <td>DSM</td>
                                        <td>
                                            <Button style={{ background: '#012258' }}>
                                                <FeatherIcon icon={"eye"} style={{ stroke: "white" }} />
                                            </Button>
                                        </td>
                                        {/*<td>
                                            <Button style={{ background: '#012258' }}>
                                                <FeatherIcon icon={"eye"} style={{ stroke: "white" }} />
                                            </Button>
                                        </td>
                                        <td>
                                            <Button style={{ background: '#3D9982' }} onClick={() => handleShow('validacion')}>
                                                <FeatherIcon icon={"check-circle"} style={{ stroke: "white" }} />
                                            </Button>
                                        </td>
                                        <td>
                                            <Button className='btn-danger' onClick={() => handleShow('retroalimentacion')}>
                                                <FeatherIcon icon={"x-circle"} style={{ stroke: "white" }} />
                                            </Button>
                                        </td>*/}
                                    </tr>
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
                    <Button type='submit' variant='outline-success'>
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
                            <Form.Control as="textarea" rows={3} />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button className='me-2' variant='outline-danger' onClick={handleClose}>
                        <FeatherIcon icon='x' />&nbsp;Cancelar
                    </Button>
                    <Button type='submit' variant='outline-success'>
                        <FeatherIcon icon='check' />&nbsp;Aceptar
                    </Button>
                </Modal.Footer>
            </Modal>


        </>
    )
}
export default Consulta
