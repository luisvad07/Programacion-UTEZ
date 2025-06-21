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
import Swal from 'sweetalert2';


export const ModalStudent = (props) => {

    const { id, state, onState, onHide, onId } = props
    const url = `http://localhost:8080/redre/estudiante/`
    const [estudiante, setEstudiante] = useState([]);
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
    const [isLoading, setIsLoading] = useState('');

    useEffect(() => {
        fetch(url).then((response) => { return response.json() })
            .then((data) => {
                console.log(data.obj);
                setEstudiante(data.obj)
            })
            .catch((error) => {
                console.log(error.message)
            })
    }, []);

    ////MÉTODOS
    const allData = () => {
        onHide()
        onId()
        setTimeout(() => {
            setMatricula(null)
            setNombre(null)
            setApellidos(null)
            setCorreo(null)
            setPassword(null)
            setDivisionAcademica(null)
            setCarrera(null)
            setGrado(null)
            setGrupo(null)
        }, 500)
    }

    if (state === true) {
        const URL = `http://localhost:8080/redre/estudiante/${id}`
        fetch(URL).then((response) => { return response.json() })
            .then((data) => {
                setMatricula(data.obj.matricula)
                setNombre(data.obj.nombre)
                setApellidos(data.obj.apellidos)
                setCorreo(data.obj.correo)
                setPassword(data.obj.password)
                setDivisionAcademica(data.obj.divisionAcademica)
                setCarrera(data.obj.carrera)
                setGrado(data.obj.grado)
                setGrupo(data.obj.grupo)

            })
            .catch((error) => {
                console.log(error.message)
            })
        onState()
    }

    const addStudent = async () => {
        if (matricula !== '' && nombre !== '' && apellidos !== '' && correo !== '' && password !== '' && divisionAcademica !== '' && carrera !== ''
            && grado !== '' && grupo !== '') {
            await fetch(`http://localhost:8080/redre/estudiante/`, {
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "matricula": `${matricula}`,
                    "nombre": `${nombre}`,
                    "apellidos": `${apellidos}`,
                    "correo": `${correo}`,
                    "carrera": `${carrera}`,
                    "grado": `${grado}`,
                    "grupo": `${grupo}`,
                    "divisionAcademica": `${divisionAcademica}`,
                    "password": `${matricula}`
                }),
            })
                .then(async (response) =>
                    await response.json())
                .then((data) => {
                    Swal.fire({
                        title: 'Exito!',
                        text: 'Esto es una ventana emergente de Swal',
                        icon: 'success',
                        confirmButtonText: 'Aceptar'
                    });
                    onHide()
                    setTimeout(() => {
                        window.location.reload()
                    }, 1000)
                })
                .catch((err) => {
                    console.log(err)
                });
        }
    }

    const updateStudent = async () => {
        if (matricula !== '' && nombre !== '' && apellidos !== '' && correo !== '' && password !== '' && divisionAcademica !== '' && carrera !== ''
            && grado !== '' && grupo !== '')
            setIsLoading(true);
        await axios.put(`${url}/${id}`, {
            "matricula": `${matricula}`,
            "nombre": `${nombre}`,
            "apellidos": `${apellidos}`,
            "correo": `${correo}`,
            "carrera": `${carrera}`,
            "grado": `${grado}`,
            "grupo": `${grupo}`,
            "divisionAcademica": `${divisionAcademica}`,
            "password": `${matricula}`
        })
            .then((response) => {
                Swal.fire({
                    title: 'Exito!',
                    text: 'Update',
                    icon: 'success',
                    confirmButtonText: 'Aceptar'
                });
                setTimeout(() => {
                    window.location.reload()
                }, 1000)
            })
            .catch((err) => {
                console.log(err)
            });
        setIsLoading(false);
    }


    /// VALIDAR CAMPOS modal
    const [validated, setValidated] = useState(false);

    const handleSubmit = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        setValidated(true);
    };

    return (

        <Modal
            {...props}
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton className='titleModal'>
                {id !== null
                    ? <Modal.Title>Actualizar Alumno</Modal.Title>
                    : <Modal.Title>Registro Alumno</Modal.Title>
                }
            </Modal.Header>
            <Modal.Body>
                <Row className="mb-4" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <FaUserCircle style={{ color: '#3D9982', fontSize: '50px' }} />
                </Row>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                    <Row className="mb-3">
                        <Form.Group as={Col} md="6" controlId="validationCustom01">
                            <Form.Label>Nombre(s)</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                value={nombre} onChange={(e) => setNombre(e.target.value)}
                            //placeholder="First name"
                            />
                            {/*<Form.Control.Feedback>Completado</Form.Control.Feedback>*/}
                        </Form.Group>
                        <Form.Group as={Col} md="6" controlId="validationCustom02">
                            <Form.Label>Apellido(s)</Form.Label>
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
                            <Form.Label>Correo</Form.Label>
                            <Form.Control type="text" required
                                value={correo} onChange={(e) => setCorreo(e.target.value)} />
                        </Form.Group>
                        <Form.Group as={Col} md="6" controlId="validationCustom04">
                            <Form.Label>Carrera</Form.Label>
                            <Form.Control type="text" required
                                value={carrera} onChange={(e) => setCarrera(e.target.value)}
                            />
                        </Form.Group>
                    </Row>

                    <Row className="mb-3">
                        <Form.Group as={Col} md="3" controlId="validationCustom03">
                            <Form.Label>Grado</Form.Label>
                            <Form.Control type="text" required
                                value={grado} onChange={(e) => setGrado(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group as={Col} md="3" controlId="validationCustom04">
                            <Form.Label>Grupo</Form.Label>
                            <Form.Control type="text" required
                                value={grupo} onChange={(e) => setGrupo(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group as={Col} md="6" controlId="validationCustom04">
                            <Form.Label>División Académica</Form.Label>
                            <Form.Control type="text" required
                                value={divisionAcademica} onChange={(e) => setDivisionAcademica(e.target.value)}
                            />
                        </Form.Group>
                    </Row>

                    <Row className="mb-5">
                        <Form.Group as={Col} md="6" controlId="validationCustom03">
                            <Form.Label>Contraseña</Form.Label>
                            <Form.Control type="text" required
                                value={password} onChange={(e) => setPassword(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group as={Col} md="6" controlId="validationCustom03">
                            <Form.Label>Matricula</Form.Label>
                            <Form.Control type="text" required
                                value={matricula} onChange={(e) => setMatricula(e.target.value)}
                            />
                        </Form.Group>
                    </Row>

                    <Button className='me-2' variant='outline-danger' onClick={allData()}>
                        <FeatherIcon icon='x' />&nbsp;Cerrar
                    </Button>

                    <Button variant="outline-success" type='submit' >
                        <FeatherIcon icon='check' />&nbsp;Guardar
                    </Button>
                    {id === null
                        ? <Button variant="outline-success" type='submit' onClick={() => addStudent()}>Registrar</Button>
                        : <Button variant="outline-success" type='submit' onClick={() => updateStudent()}>Actualizar</Button>
                    }
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

    )
}

export default ModalStudent