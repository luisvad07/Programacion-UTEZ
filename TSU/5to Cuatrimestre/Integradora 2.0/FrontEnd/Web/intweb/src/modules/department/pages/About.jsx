import React, { useEffect, useState } from 'react'
import axios from 'axios';
import FeatherIcon from "feather-icons-react";
import Modal from 'react-bootstrap/Modal'
import Button from 'react-bootstrap/Button'
import ModalAdm from '../../../shared/components/ModalAdm';
export const About = () => {
    const bodyC = {
        background: "#F2F2F2"
    }
    const url = '';
    const [students, setStudents] = useState([]);

    const [nombre, setNombre] = useState('');
    const [correo, setCorreo] = useState('');
    const [carrera, setCarrera] = useState('');
    const [grado, setGrado] = useState('');
    const [da, setDA] = useState('');

    const [operation, setOperation] = useState(1);

    useEffect(() => {
        getStudents();
    }, []);

    const getStudents = async () => {
        const respuesta = await axios.get(url);
        setStudents(respuesta.data)
    }
    const styeleTable = {
        width: '100%',
        background: '#fff',
        //padding: '20px',
        boxShadow: '0px 0px 5px #ccc',
        borderRadius: '10px',
        marginTop: '20px',
        textAlign: 'start',
        //borderBottom: 'inset',
        paddingBottom: '5px',
    }

    const linea = {
        height: '2px',
        border: '3px solid #3D9982',
        color: '#3D9982',
        backgroundColor: '#3D9982',
        opacity: '100',
    }

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return (
        <>
            <div className='App'>
                <div fluid="true">

                    <div className="col-sm p-3 min-vh-100">
                        <div className="container">
                            <div className="row">
                                <div className="col d-flex justify-content-start">
                                    <h1>Departamento de Estadías</h1>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br />
                    <hr className="linea" />
                    <br />

                    <div className='row mt-3'>
                        <div className='col-md-4 offset-4'>
                            <div className='d-grid mx-auto'>
                                <button className='btn btnstyle' type='submit' onClick={handleShow} style={{ float: 'left' }}>
                                    Lista de Alumnos
                                </button>
                                <button className='btn btn-accion' type='submit' onClick={handleShow} style={{ float: 'right' }}>
                                    <FeatherIcon icon={"plus"} style={{ stroke: "white" }} />
                                </button>
                            </div>
                        </div>
                    </div>
                    <br />
                    <br />
                    <div className='row mt-3'>
                        <div className='col-12 col-lg-8 offset-0 offset-lg-12'>
                            <div className='table-responsive'>
                                <table className='table table-bordered styeleTable' style={{ textAlign: 'left' }} >
                                    <thead  >
                                        <tr>
                                            <th></th>
                                            <th>Nombre</th>
                                            <th>Correo Institucional</th>
                                            <th>Carrera</th>
                                            <th>Grado y grupo</th>
                                            <th>DA</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <br />
                                    <tbody className='table-group-divider border-top'>
                                        <tr>
                                            <td><FeatherIcon icon={"circle"} style={{ stroke: "#3D9982" }} /></td>
                                            <td>Andrea Michelle Estrada Hernández</td>
                                            <td>20213tn011@utez.edu.mx</td>
                                            <td>DSM</td>
                                            <td>5A DSM</td>
                                            <td>
                                                <button className='btn btn-accion'>
                                                    <FeatherIcon icon={"edit"} style={{ stroke: "white" }} />
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>

                </div>
            </div>
            <ModalAdm show={show} handleClose={handleClose} />
        </>
    )
}

export default About