import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Modal, Card, Col, Row, Badge, Button, Form, InputGroup } from 'react-bootstrap'
import DataTable, { createTheme } from 'react-data-table-component'
import '../../shared/plugins/Screens.css'
import FeatherIcon from 'feather-icons-react/build/FeatherIcon'
import Swal from 'sweetalert2';

const ServiceScreen = () => {

    const navigate = useNavigate();
    const urlService = `http://localhost:8080/api/servicios/`
    const [admin, setAdmin] = useState([]);

    /*Cargar  */
    const [servicio, setServicio] = useState([]);
    const [id, setIdService] = useState('');
    const [nomserv, setNomserv] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [documentos, setDocumentos] = useState('');
    const [costo, setCosto] = useState('');
    const [status, setStatus] = useState('');

    const [adminId, setAdminId] = useState(localStorage.getItem('sesionId'));

    useEffect(() => {
        sesionActiva();
        cargarServicios();
        cargarAdmin();
    }, []);

    const sesionActiva = () => {
        const id = localStorage.getItem("sesionId")
        const rol = localStorage.getItem("rol")

        if (id === null || rol != 'admin') {
            navigate('/login');
        }
    }

    const cargarServicios = async () => {
        try {
            const respuesta = await axios.get(urlService);
            setServicio(respuesta.data.data);
            console.log(respuesta.data.data)
            //console.clear();
        } catch (error) {
            console.log('Error:', error.message);
        }
    };

    const cargarAdmin = async () => {
        try {
            const respuesta = await axios.get(`http://localhost:8080/api/administrador/`);
            setAdmin(respuesta.data.data);
            //console.log(respuesta.data)
            //console.clear();
        } catch (error) {
            console.log('Error:', error.message);
            // Otro manejo de errores
        }
    };

    /*Intento de modal */

    const [isLoading, setIsLoading] = useState('');
    const [mode, setMode] = useState('');
    const [title, setTitle] = useState('');
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);

    const handleShow = (mode, id, nomserv, descripcion, documentos, costo, status) => {
        setIdService('');
        setNomserv('');
        setDescripcion('');
        setDocumentos('');
        setCosto('');
        setStatus(true);
        setMode(mode);

        if (mode === "add") {
            setTitle('Registrar solicitante');
        } else if (mode === "edit") {
            setTitle('Editar solicitante');
            setIdService(id);
            setNomserv(nomserv);
            setDescripcion(descripcion);
            setDocumentos(documentos);
            setCosto(costo);
            setStatus(status);
            setAdminId(adminId);
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
        if (![nomserv, descripcion, documentos, costo].every(field => field !== '')) {
            Swal.fire({
                icon: 'warning',
                title: 'Llena todos los campos',
                showConfirmButton: false,
                timer: 1500
            });
        } else {
            if (modo === "add") {
                parametros = {
                    nomserv: nomserv.trim(), descripcion: descripcion.trim(), documentos: documentos.trim(),
                    costo: parseFloat(costo), status: status, admin: { id: adminId }
                };
                metodo = 'POST';
            } else {
                parametros = {
                    id: id, nomserv: nomserv.trim(), descripcion: descripcion.trim(), documentos: documentos.trim(),
                    costo: parseFloat(costo), status: status, admin: { id: adminId }
                };
                metodo = 'PUT';
            }
            enviarSolicitud(metodo, parametros);
        }
    }

    const enviarSolicitud = async (metodo, parametros) => {
        if (metodo === 'PUT') {
            await axios({ method: metodo, url: `http://localhost:8080/api/servicios/${id}`, data: parametros })
                .then(function (respuesta) {
                    var hasError = respuesta.data.status;
                    ///var msj = respuesta.data.message;
                    Swal.fire({
                        icon: 'success',
                        iconColor: '#58BEC4',
                        title: 'Servicio actualizado correctamente',
                        showConfirmButton: false,
                        timer: 2000
                    });
                    if (hasError === false) {
                        cargarServicios();
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
                    /*handleClose();
                    console.log(error);
                    Promesa cumple y sale
                    */
                })
                .finally(function () {
                    cargarServicios();
                    handleClose();
                });

        } else {
            await axios({ method: metodo, url: urlService, data: parametros })
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
                        Swal.fire({
                            icon: 'error',
                            iconColor: '#264B99',
                            title: 'Intenta de nuevo',
                            text: msj,
                            showConfirmButton: false,
                            timer: 2000
                        });
                        cargarServicios();
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
                    /*handleClose();*/
                    console.log(error);
                })
                .finally(function () {
                    cargarServicios();
                    handleClose();
                });
        }

    }

    const changeStatus = (id, nomserv, descripcion, documentos, costo, status) => {
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
                    nomserv: nomserv,
                    descripcion: descripcion,
                    documentos: documentos,
                    costo: costo,
                    status: nuevoStatus
                };

                axios.patch(`http://localhost:8080/api/servicios/`, data)
                    .then(function (respuesta) {
                        var hasError = respuesta.data.status;
                        var msj = respuesta.data.message;

                        let successMessage;

                        if (nuevoStatus) {
                            successMessage = 'Servicio dado de alta correctamente';
                        } else {
                            successMessage = 'Servicio dado de baja correctamente';
                        }

                        Swal.fire({
                            icon: 'success',
                            iconColor: '#58BEC4',
                            title: successMessage,
                            showConfirmButton: false,
                            timer: 2000
                        });

                        if (hasError === false) {
                            cargarServicios();
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
                        cargarServicios();
                        handleClose();
                    });
            }
        });
    }

    ///BUSCAR 
    const [searchTerm, setSearchTerm] = useState("");
    const filteredData = servicio.filter(item =>
        item.nomserv.toLowerCase().includes(searchTerm?.toLowerCase()) ||
        item.descripcion.toLowerCase().includes(searchTerm?.toLowerCase()) ||
        item.documentos.toLowerCase().includes(searchTerm?.toLowerCase()) 
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
                                    <th colSpan="5" style={{ fontSize: '24px', fontWeight: 'bold' }}>SERVICIOS</th>
                                </tr>
                                <tr>
                                    {/*<th>ID</th>*/}
                                    <th >Nombre del servicio</th>
                                    <th>Descripción del servicio</th>
                                    <th>Documentos requerido</th>
                                    <th>Costo</th>
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
                                        <td className="rounded-border">{item.nomserv}</td>
                                        <td className="rounded-border" >{item.descripcion}</td>
                                        <td className="rounded-border">{item.documentos}</td>
                                        <td className="rounded-border">{item.costo}</td>
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
                                                    onClick={() => {
                                                        if (item.status) {
                                                            handleShow('edit', item.id, item.nomserv, item.descripcion, item.documentos, item.costo, item.status);
                                                        } else {
                                                            Swal.fire({
                                                                icon: 'warning',
                                                                title: 'Oops...',
                                                                text: 'No se puede editar un elemento dado de baja',
                                                                showConfirmButton: false,
                                                                timer: 2000
                                                            })
                                                        }
                                                    }}
                                                />
                                            </button>
                                            {item.status ? (
                                                <button className="btn-b" /*style={{ marginRight: '5px' }}*/>
                                                    <FeatherIcon
                                                        icon='trash-2'
                                                        style={{ color: 'black' }}
                                                        onClick={() => changeStatus(item.id, item.nomserv,
                                                            item.descripcion, item.documentos, item.costo, item.status)}
                                                    />
                                                </button>
                                            ) : (
                                                <button className="btn-inactive" /*style={{ marginRight: '5px' }}*/>
                                                    <FeatherIcon
                                                        icon='pocket'
                                                        style={{ color: 'black' }}
                                                        onClick={() => changeStatus(item.id, item.nomserv,
                                                            item.descripcion, item.documentos, item.costo, item.status)}
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
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <Row className="mb-3">
                            <Form.Group as={Col} md="12" controlId="validationCustom01">
                                <Form.Label style={{ color: '#2A4172' }}><b>Nombre del servicio</b></Form.Label>
                                <Form.Control
                                    required
                                    type="text"
                                    value={nomserv} onChange={(e) => setNomserv(e.target.value)}
                                //placeholder="First name"
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-3">
                            <Form.Group md="12" controlId="validationCustom02">
                                <Form.Label style={{ color: '#2A4172' }}><b>Descripción</b></Form.Label>
                                <Form.Control
                                    as="textarea" rows={3}
                                    required
                                    type="text"
                                    value={descripcion} onChange={(e) => setDescripcion(e.target.value)}
                                //placeholder="Last name"
                                />
                            </Form.Group>
                        </Row>

                        <Row className="mb-4">
                            <Form.Group as={Col} md="6" controlId="validationCustom03">
                                <Form.Label style={{ color: '#2A4172' }}><b>Documentos</b></Form.Label>
                                <Form.Control as="textarea" rows={3} type="text" required
                                    value={documentos} onChange={(e) => setDocumentos(e.target.value)} />
                            </Form.Group>
                            <Form.Group as={Col} md="6" controlId="validationCustom04">
                                <Form.Label style={{ color: '#2A4172' }}><b>Costo</b></Form.Label>
                                <InputGroup className="mb-3">
                                    <InputGroup.Text>$</InputGroup.Text>
                                    <Form.Control type="text" required
                                        value={costo} onChange={(e) => setCosto(e.target.value)} />
                                    <InputGroup.Text>.00</InputGroup.Text>
                                </InputGroup>
                                {/*<InputGroup.Text>$</InputGroup.Text>
                                <Form.Control type="text" required
                                    value={costo} onChange={(e) => setCosto(e.target.value)} />
                                <InputGroup.Text>.00</InputGroup.Text>*/}
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
export default ServiceScreen