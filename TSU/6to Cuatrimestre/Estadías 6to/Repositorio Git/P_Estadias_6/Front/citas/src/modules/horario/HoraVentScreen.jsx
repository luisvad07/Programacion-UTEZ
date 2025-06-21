import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Modal, Card, Col, Row, Badge, Button, Form, InputGroup } from 'react-bootstrap'
import DataTable, { createTheme } from 'react-data-table-component'
import '../../shared/plugins/Screens.css'

import 'react-datepicker/dist/react-datepicker.css';
import FeatherIcon from 'feather-icons-react/build/FeatherIcon'
import Swal from 'sweetalert2';

const HoraVentScreen = () => {
    const navigate = useNavigate();
    const urlHorario = `http://localhost:8080/api/horarios/`

    /*Cargar Ventanilla */
    const [horario, setHorario] = useState([]);
    const [venta, setVentanilla] = useState([]);

    const [id, setId] = useState('');
    const [diaSemana, setDia] = useState('');
    const [horaInicio, setInicio] = useState('');
    const [horaFin, setFin] = useState('');
    //const [cantidadRepeticiones, setRepeticion] = useState('');
    const [status, setStatus] = useState('');

    const [ventanillaId, setVentaId] = useState(localStorage.getItem('sesionId'));

    useEffect(() => {
        sesionActiva();
        cargarHorarios();
        cargarVentanilla();
    }, []);

    const sesionActiva = () => {
        const id = localStorage.getItem("sesionId")
        const rol = localStorage.getItem("rol")
    
        if (id === null || rol != 'ventanilla') {
            navigate('/login');
        }
    }

    const cargarHorarios = async () => {
        try {
            const respuesta = await axios.get(urlHorario);
            setHorario(respuesta.data.data);
            console.log(respuesta.data.data)
            //console.clear();
        } catch (error) {
            console.log('Error:', error.message);
        }
    };

    const cargarVentanilla = async () => {
        try {
            const respuesta = await axios.get(`http://localhost:8080/api/ventanillas/`);
            setVentanilla(respuesta.data.data);
            //console.log(respuesta.data)
            //console.clear();
        } catch (error) {
            console.log('Error:', error.message);
            // Otro manejo de errores
        }
    };

    /*Horarios */

    const diasSemanaOptions = [
        { value: 'Lunes', label: 'Lunes' },
        { value: 'Martes', label: 'Martes' },
        { value: 'Miércoles', label: 'Miércoles' },
        { value: 'Jueves', label: 'Jueves' },
        { value: 'Viernes', label: 'Viernes' },
    ];

    const generarHorarios = () => {
        const horarios = [];
        const horaInicial = 8;
        const horaFinal = 15;
        for (let hora = horaInicial; hora <= horaFinal; hora++) {
            for (let minutos = 0; minutos < 60; minutos += 30) {
                const horaFormateada = `${hora.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}`;
                horarios.push(horaFormateada);
            }
        }
        return horarios;
    };

    // Llamamos a la función generarHorarios para obtener las opciones del select
    const opcionesHorario = generarHorarios();

    /*Intento de modal */

    const [isLoading, setIsLoading] = useState('');
    const [mode, setMode] = useState('');
    const [title, setTitle] = useState('');
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);


    const handleShow = (mode, id, diaSemana, horaInicio, horaFin, status) => {
        setId('');
        setDia('');
        setInicio('');
        setFin('');
        //setRepeticion('');
        setStatus(true);
        setMode(mode);
        if (mode === "add") {
            setTitle('Registrar horario');
        } else if (mode === "edit") {
            setTitle('Editar horario');
            setId(id);
            setDia(diaSemana);
            setInicio(horaInicio);
            setFin(horaFin);
            //setRepeticion(cantidadRepeticiones);
            setStatus(status);
            setVentaId(ventanillaId);
            setMode(mode);
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
        if (![diaSemana, horaInicio, horaFin].every(field => field !== '')) {
            Swal.fire({
                icon: 'warning',
                title: 'Llena todos los campos',
                showConfirmButton: false,
                timer: 1500
            });
        } else {
            if (modo === "add") {
                parametros = {
                    diaSemana: diaSemana.trim(),
                    horaInicio: horaInicio,
                    horaFin: horaFin,
                    status: status,
                    ventanilla: { id: ventanillaId }
                };
                metodo = 'POST';
            } else {
                parametros = {
                    id: id,
                    diaSemana: diaSemana.trim(),
                    horaInicio: horaInicio,
                    horaFin: horaFin,
                    status: status,
                    ventanilla: { id: ventanillaId }
                };
                metodo = 'PUT';
            }
            enviarSolicitud(metodo, parametros);
        }
    }

    const enviarSolicitud = async (metodo, parametros) => {
        if (metodo === 'PUT') {
            await axios({ method: metodo, url: `http://localhost:8080/api/horarios/${id}`, data: parametros })
                .then(function (respuesta) {
                    var hasError = respuesta.data.status;
                    ///var msj = respuesta.data.message;
                    Swal.fire({
                        icon: 'success',
                        iconColor: '#58BEC4',
                        title: 'Horario actualizado correctamente',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    if (hasError === false) {
                        cargarHorarios();
                        handleClose();
                    }
                })
                .catch(function (error) {
                    Swal.fire({
                        icon: 'error',
                        iconColor: '#264B99',
                        title: 'Error en la petición',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    /*handleClose();
                    console.log(error);
                    Promesa cumple y sale
                    */
                })
                .finally(function () {
                    cargarHorarios();
                    handleClose();
                });

        } else {
            await axios({ method: metodo, url: urlHorario, data: parametros })
                .then(function (respuesta) {

                    var hasError = respuesta.data.status;
                    var msj = respuesta.data.message;
                    Swal.fire({
                        icon: 'success',
                        iconColor: '#58BEC4',
                        title: 'Horario registrado corrrectamente',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    if (hasError === false) {
                        cargarHorarios();
                        handleClose();
                    }
                })
                .catch(function (error) {
                    Swal.fire({
                        icon: 'error',
                        iconColor: '#264B99',
                        title: 'Error en la petición',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    /*handleClose();
                    console.log(error);*/
                })
                .finally(function () {
                    cargarHorarios();
                    handleClose();
                });
        }

    }

    const changeStatus = (id, diaSemana, horarioInicio, horarioFin, status) => {
        const nuevoStatus = !status; // Cambiar el estado actual
        Swal.fire({
            title: '¿Deseas cambiar el estado?',
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
                    diaSemana,
                    horaInicio: horaInicio,
                    horaFin: horaFin,
                    status: nuevoStatus
                };

                axios.patch(`http://localhost:8080/api/horarios/${id}`, data)
                    .then(function (respuesta) {
                        var hasError = respuesta.data.status;
                        var msj = respuesta.data.message;

                        let successMessage;

                        if (nuevoStatus) {
                            successMessage = 'Horario dado de alta correctamente';
                        } else {
                            successMessage = 'Horario dado de baja correctamente';
                        }

                        Swal.fire({
                            icon: 'success',
                            iconColor: '#58BEC4',
                            title: successMessage,
                            showConfirmButton: false,
                            timer: 2000
                        });

                        if (hasError === false) {
                            cargarHorarios();
                            handleClose();
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
                        cargarHorarios();
                        handleClose();
                    });
            }
        });
    }

    ///BUSCAR 
    const [searchTerm, setSearchTerm] = useState("");
    const filteredData = horario.filter(item =>
        item.diaSemana.toLowerCase().includes(searchTerm.toLowerCase()) ||
        item.horarioInicio.toLowerCase().includes(searchTerm.toLowerCase()) ||
        item.horarioFin.toLowerCase().includes(searchTerm.toLowerCase()) ||
        item.cantidadRepeticiones.toLowerCase().includes(searchTerm.toLowerCase())
    );

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

    ////
    const [pass, setPass] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    const togglePassword = () => {
        setShowPassword(!showPassword);
    };

    return (
        <>
            <div className="container">
                <div className="table-container">
                    <div className="table-wrapper">
                        <table className="table rounded-border">
                            <thead style={{ textAlign: 'center' }}>
                                <tr>
                                    <th colSpan="5" style={{ fontSize: '24px', fontWeight: 'bold' }}>HORARIOS</th>
                                </tr>
                                <tr>
                                    {/*<th>ID</th>*/}
                                    <th>Día de la semana </th>
                                    <th>Hora de Inicio</th>
                                    <th>Hora de Fin</th>
                                    <th>Estado</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {filteredData && filteredData.length === 0 ? (
                                    <tr>
                                        <td colSpan="9" style={{ textAlign: 'center' }}>No hay registros</td>
                                    </tr>
                                ) : (filteredData && filteredData.map((item) => (
                                    <tr key={item.id} style={{ border: 'none' }} className='mb-4'>
                                        {/*<td className="rounded-border">{item.id}</td>*/}
                                        <td className="rounded-border">{item.diaSemana}</td>
                                        <td className="rounded-border">{item.horaInicio}</td>
                                        <td className="rounded-border">{item.horaFin}</td>
                                        <td className="rounded-border">
                                            {item.status ? (
                                                <Badge bg='success'>Alta</Badge>
                                            ) : (
                                                <Badge bg='danger'>Baja</Badge>)}
                                        </td>
                                        <td style={{ background: '#2A4172', border: 'none' }}>
                                            <button className="btn-b" style={{ marginRight: '5px' }}>
                                                <FeatherIcon
                                                    icon='edit'
                                                    style={{ color: 'black' }}
                                                    onClick={() => handleShow('edit', item.id, item.diaSemana, item.horaInicio, item.horaFin,
                                                        item.status)}
                                                />
                                            </button>
                                            {item.status ? (
                                                <button className="btn-b" /*style={{ marginRight: '5px' }}*/>
                                                    <FeatherIcon
                                                        icon='trash-2'
                                                        style={{ color: 'black' }}
                                                        onClick={() => changeStatus(item.id, item.diaSemana, item.horaInicio, item.horaFin,
                                                            item.status)}
                                                    />
                                                </button>
                                            ) : (
                                                <button className="btn-inactive" /*style={{ marginRight: '5px' }}*/>
                                                    <FeatherIcon
                                                        icon='pocket'
                                                        style={{ color: 'black' }}
                                                        onClick={() => changeStatus(item.id, item.diaSemana, item.horarioInicio, item.horarioFin,
                                                            item.status)}
                                                    />
                                                </button>
                                            )}
                                        </td>
                                    </tr>
                                ))
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className="bottom-section">
                    <input type="text" placeholder="Buscar" />
                    <button className="btn-b" onClick={() => handleShow("add")}>Agregar</button>
                </div>
            </div>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton className='titleModal' style={{ backgroundColor: '#58BEC4', color: 'white' }}>
                    <Modal.Title>{title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form /*noValidate validated={validated}*/ onSubmit={handleSubmit}>
                        <Row className="mb-3">
                            <Form.Group as={Col} md="6" controlId="validationCustom01">
                                <Form.Label style={{ color: '#2A4172' }}><b>Dia de la semana</b></Form.Label>
                                <Form.Select
                                    required
                                    value={diaSemana}
                                    onChange={(e) => setDia(e.target.value)}>
                                    <option value="">Seleccione un día</option>
                                    {diasSemanaOptions.map((dia) => (
                                        <option key={dia.value} value={dia.value}>
                                            {dia.label}
                                        </option>
                                    ))}
                                </Form.Select>
                            </Form.Group>
                            <Form.Group as={Col} md="6" controlId="validationCustom02">
                                <Form.Label style={{ color: '#2A4172' }}><b>Hora de Inicio</b></Form.Label>
                                <Form.Control
                                    required
                                    as="select"
                                    value={horaInicio}
                                    onChange={(e) => setInicio(e.target.value)}
                                >
                                    <option value="">Seleccione una hora</option>
                                    {opcionesHorario.map((hora) => (
                                        <option key={hora} value={hora}>
                                            {hora}
                                        </option>
                                    ))}
                                </Form.Control>
                            </Form.Group>
                        </Row>
                        <Row className="mb-3">
                            <Form.Group as={Col} md="6" controlId="validationCustom03">
                                <Form.Label style={{ color: '#2A4172' }}><b>Horario de Fin</b></Form.Label>
                                <Form.Control
                                    required
                                    as="select"
                                    value={horaFin}
                                    onChange={(e) => setFin(e.target.value)}
                                >
                                    <option value="">Seleccione una hora</option>
                                    {opcionesHorario.map((hora) => (
                                        <option key={hora} value={hora}>
                                            {hora}
                                        </option>
                                    ))}
                                </Form.Control>
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
            </Modal>
        </>
    )
}
export default HoraVentScreen