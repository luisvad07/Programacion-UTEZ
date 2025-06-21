import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Modal, Card, Col, Row, Badge, Button, Form, InputGroup } from 'react-bootstrap'
import DataTable, { createTheme } from 'react-data-table-component'
import '../../shared/plugins/Screens.css'

import FeatherIcon from 'feather-icons-react/build/FeatherIcon'
import Swal from 'sweetalert2';

const SolicitanteScreen = () => {

  const navigate = useNavigate();
  const urlSoli = `http://localhost:8080/api/solicitantes/`

  /*Cargar Solicitante */
  const [solicitante, setSolicitante] = useState([]);
  const [administrador, setAdministrador] = useState([]);

  const [id, setId] = useState('');
  const [nombre, setNombre] = useState('');
  const [apePaterno, setApePaterno] = useState('');
  const [apeMaterno, setApeMaterno] = useState('');
  const [matricula, setMatricula] = useState('');
  const [carrera, setCarrera] = useState('');
  const [correoElectronico, setCorreoElectronico] = useState('');
  const [telefono, setTelefono] = useState('');
  const [pass, setPass] = useState('');
  const [status, setStatus] = useState('');
  const [changePassword, setChangePassword] = useState('');

  const [adminId, setAdminId] = useState(localStorage.getItem('sesionId'));

  useEffect(() => {
    sesionActiva();
    cargarSolicitante();
    cargarAdmin();
  }, []);

  const sesionActiva = () => {
    const id = localStorage.getItem("sesionId")
    const rol = localStorage.getItem("rol")

    if (id === null || rol != 'admin') {
        navigate('/login');
    }
}

  const cargarSolicitante = async () => {
    try {
      const respuesta = await axios.get(urlSoli);
      setSolicitante(respuesta.data.data);
      //console.log(respuesta.data.data)
      //console.clear();
    } catch (error) {
      console.log('Error:', error.message);
    }
  };

  const cargarAdmin = async () => {
    try {
      const respuesta = await axios.get(`http://localhost:8080/api/administrador/`);
      setAdministrador(respuesta.data.data);
      //console.log(respuesta.data.data)
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

  const handleShow = (mode, id, nombre, apePaterno, apeMaterno, matricula, carrera,
    correoElectronico, telefono, pass, status, changePassword) => {
    setId('');
    setNombre('');
    setApePaterno('');
    setApeMaterno('');
    setMatricula('');
    setCarrera('');
    setCorreoElectronico('');
    setTelefono('');
    setPass('');
    setStatus(true);
    setChangePassword(false);
    setMode(mode);
    if (mode === "add") {
      setTitle('Registrar solicitante');
    } else if (mode === "edit") {
      setTitle('Editar solicitante');
      setId(id);
      setNombre(nombre);
      setApePaterno(apePaterno);
      setApeMaterno(apeMaterno);
      setMatricula(matricula);
      setCarrera(carrera);
      setCorreoElectronico(correoElectronico);
      setTelefono(telefono);
      setPass(pass);
      setStatus(status);
      setChangePassword(changePassword);
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
    if (![nombre, apePaterno, /*apeMaterno*/, matricula, carrera, correoElectronico, telefono, pass].every(field => field !== '')) {
      Swal.fire({
        icon: 'warning',
        title: 'Llena todos los campos',
        showConfirmButton: false,
        timer: 1500
      });
    } else {
      if (modo === "add") {
        parametros = {
          nombre: nombre.trim(), apePaterno: apePaterno.trim(), apeMaterno: apeMaterno.trim(),
          matricula: matricula.trim(), carrera: carrera.trim(),
          correoElectronico: correoElectronico.trim(), telefono: telefono.trim(),
          pass: pass.trim(), status: status, changePassword: changePassword, admin: { id: adminId }
        };
        metodo = 'POST';
      } else {
        parametros = {
          id: id, nombre: nombre.trim(), apePaterno: apePaterno.trim(), apeMaterno: apeMaterno.trim(),
          matricula: matricula.trim(), carrera: carrera.trim(),
          correoElectronico: correoElectronico.trim(), telefono: telefono.trim(),
          pass: pass.trim(), status: status, changePassword: changePassword, admin: { id: adminId }
        };
        metodo = 'PUT';
      }
      enviarSolicitud(metodo, parametros);
    }
  }

  const enviarSolicitud = async (metodo, parametros) => {
    if (metodo === 'PUT') {
      await axios({ method: metodo, url: `http://localhost:8080/api/solicitantes/${id}`, data: parametros })
        .then(function (respuesta) {
          var hasError = respuesta.data.status;
          var msj = respuesta.data.message;
          Swal.fire({
            icon: 'success',
            iconColor: '#58BEC4',
            title: msj,
            text: 'Solicitante: ' + nombre + ' ' + apePaterno,
            showConfirmButton: false,
            timer: 2000
          });
          if (hasError === false) {
            cargarSolicitante();
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
          cargarSolicitante();
          handleClose();
        });
    } else {
      await axios({ method: metodo, url: urlSoli, data: parametros })
        .then(function (respuesta) {

          var hasError = respuesta.data.status;
          var msj = respuesta.data.message;
          Swal.fire({
            icon: 'success',
            iconColor: '#58BEC4',
            title: msj,
            text: 'Solicitante: ' + nombre + ' ' + apePaterno,
            showConfirmButton: false,
            timer: 1500
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
            cargarSolicitante();
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
          cargarSolicitante();
          handleClose();
        });
    }

  }

  const changeStatus = (id, nombre, apePaterno, apeMaterno, matricula, carrera,
    correoElectronico, telefono, pass, status, changePassword) => {
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
          nombre: nombre,
          apePaterno: apePaterno,
          apeMaterno: apeMaterno,
          matricula: matricula,
          carrera: carrera,
          correoElectronico: correoElectronico,
          telefono: telefono,
          pass: pass,
          status: nuevoStatus, // Asignar el nuevo estado
          changePassword: changePassword
        };

        axios.patch(`http://localhost:8080/api/solicitantes/${id}`, data)
          .then(function (respuesta) {
            var hasError = respuesta.data.status;
            var msj = respuesta.data.message;

            let successMessage;

            if (nuevoStatus) {
              successMessage = 'Solicitante dado de alta correctamente';
            } else {
              successMessage = 'Solicitante dado de baja correctamente';
            }

            Swal.fire({
              icon: 'success',
              iconColor: '#58BEC4',
              title: successMessage,
              text: `Solicitante: ${nombre} ${apePaterno}`,
              showConfirmButton: false,
              timer: 2000
            });

            if (hasError === false) {
              cargarSolicitante();
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
            cargarSolicitante();
            handleClose();
          });
      }
    });
  }


  ///BUSCAR 
  const [searchTerm, setSearchTerm] = useState("");
  const filteredData = solicitante.filter(item =>
    item.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.apePaterno.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.apeMaterno.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.correoElectronico.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.carrera.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.matricula.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.telefono.toLowerCase().includes(searchTerm.toLowerCase())
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
  const [password, setPassword] = useState('');
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
                  <th colSpan="6" style={{ fontSize: '24px', fontWeight: 'bold' }}>SOLICITANTES</th>
                </tr>
                <tr>
                  {/*<th>ID</th>*/}
                  <th >Nombre(s)</th>
                  <th>Apellido (s)</th>
                  <th>Matrícula</th>
                  <th>Carrera</th>
                  <th>Correo electrónico</th>
                  {/*<th>Teléfono</th>*/}
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
                    <td className="rounded-border">{item.nombre}</td>
                    <td className="rounded-border">{item.apePaterno} {item.apeMaterno}</td>
                    <td className="rounded-border">{item.matricula}</td>
                    <td className="rounded-border">{item.carrera}</td>
                    <td className="rounded-border">{item.correoElectronico}</td>
                    {/*<td className="rounded-border">{item.telefono}</td>*/}
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
                              handleShow('edit', item.id, item.nombre, item.apePaterno, item.apeMaterno, item.matricula,
                                item.carrera, item.correoElectronico, item.telefono, item.pass, item.status, item.changePassword);
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
                            onClick={() => changeStatus(item.id, item.nombre, item.apePaterno, item.apeMaterno, item.matricula,
                              item.carrera, item.correoElectronico, item.telefono, item.pass,
                              item.status, item.changePassword)}
                          />
                        </button>
                      ) : (
                        <button className="btn-inactive" /*style={{ marginRight: '5px' }}*/>
                          <FeatherIcon
                            icon='pocket'
                            style={{ color: 'black' }}
                            onClick={() => changeStatus(item.id, item.nombre, item.apePaterno, item.apeMaterno, item.matricula,
                              item.carrera, item.correoElectronico, item.telefono, item.pass,
                              item.status, item.changePassword)}
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
              <Form.Group as={Col} md="6" controlId="validationCustom01">
                <Form.Label style={{ color: '#2A4172' }}><b>Nombre(s)</b></Form.Label>
                <Form.Control
                  required
                  type="text"
                  value={nombre} onChange={(e) => setNombre(e.target.value)}
                //placeholder="First name"
                />
                {/*<Form.Control.Feedback>Completado</Form.Control.Feedback>*/}
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustom02">
                <Form.Label style={{ color: '#2A4172' }}><b>Apellido Paterno</b></Form.Label>
                <Form.Control
                  required
                  type="text"
                  value={apePaterno} onChange={(e) => setApePaterno(e.target.value)}
                //placeholder="Last name"
                />

              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group as={Col} md="6" controlId="validationCustom03">
                <Form.Label style={{ color: '#2A4172' }}><b>Apellido Materno</b></Form.Label>
                <Form.Control type="text" required
                  value={apeMaterno} onChange={(e) => setApeMaterno(e.target.value)} />
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustom04">
                <Form.Label style={{ color: '#2A4172' }}><b>Correo</b></Form.Label>
                <Form.Control type="text" required
                  value={correoElectronico} onChange={(e) => setCorreoElectronico(e.target.value)} />
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} md="6" controlId="validationCustom05">
                <Form.Label style={{ color: '#2A4172' }}><b>Matrícula</b></Form.Label>
                <Form.Control type="text" required
                  value={matricula} onChange={(e) => setMatricula(e.target.value)} />
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustom06">
                <Form.Label style={{ color: '#2A4172' }}><b>Carrera</b></Form.Label>
                <Form.Control type="text" required
                  value={carrera} onChange={(e) => setCarrera(e.target.value)} />
              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group as={Col} md="6" controlId="validationCustom07">
                <Form.Label style={{ color: '#2A4172' }}><b>Teléfono</b></Form.Label>
                <Form.Control type="text" required
                  value={telefono} onChange={(e) => setTelefono(e.target.value)} />
              </Form.Group>
            </Row>
            <Row className="mb-4">
              <Form.Group as={Col} md="7" controlId="validationCustom09">
                <Form.Label style={{ color: '#2A4172' }}><b>Contraseña</b></Form.Label>
                <Form.Control type={showPassword ? "text" : "password"} required
                  value={pass} onChange={(e) => setPass(e.target.value)}
                />
              </Form.Group>
              <Form.Group as={Col} md="1" controlId="validationCustom10">
                <Form.Label style={{ color: 'white' }}>P</Form.Label>
                <Button
                  className="btn"
                  style={{
                    backgroundColor: "#375689",
                    borderLeft: "none",
                    width: "fit-content", /* Ajusta el ancho del botón al contenido */
                    padding: "0.25rem", /* Ajusta el padding del botón */
                  }}
                  onClick={togglePassword}
                >
                  <FeatherIcon
                    style={{
                      stroke: 'white',
                      fontSize: '1.2em', /* Ajusta el tamaño del ícono */
                    }}
                    icon={showPassword ? 'eye-off' : 'eye'}
                  />
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
      </Modal>
    </>
  )
}
export default SolicitanteScreen