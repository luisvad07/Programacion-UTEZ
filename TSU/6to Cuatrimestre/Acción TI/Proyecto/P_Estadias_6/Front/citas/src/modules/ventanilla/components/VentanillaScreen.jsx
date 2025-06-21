import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Modal, Card, Col, Row, Badge, Button, Form, InputGroup } from 'react-bootstrap'
import '../../../shared/plugins/Screens.css'

import FeatherIcon from 'feather-icons-react/build/FeatherIcon'
import Swal from 'sweetalert2';


const VentanillaScreen = () => {

  const navigate = useNavigate();
  const urlVenta = `http://localhost:8080/api/ventanillas/`

  /*Cargar Ventanilla */
  const [vetanilla, setVentanilla] = useState([]);
  const [administrador, setAdmin] = useState([]);

  const [id, setId] = useState('');
  const [nombreVent, setNombreVent] = useState('');
  const [apePaternoVent, setApePaternoVent] = useState('');
  const [apeMaternoVent, setApeMaternoVent] = useState('');
  const [correoElectronico, setCorreoElectronico] = useState('');
  const [pass, setPass] = useState('');
  const [status, setStatus] = useState('');
  const [changePassword, setChangePassword] = useState('');

  const [adminId, setAdminId] = useState(localStorage.getItem('sesionId'));

  useEffect(() => {
    sesionActiva();
    cargarVentanilla();
    cargarAdmin();
  }, []);

  const sesionActiva = () => {
    const id = localStorage.getItem("sesionId")
    const rol = localStorage.getItem("rol")

    if (id === null || rol != 'admin') {
        navigate('/login');
    }
}

  const cargarVentanilla = async () => {
    try {
      const respuesta = await axios.get(urlVenta);
      setVentanilla(respuesta.data.data);
      //console.log(respuesta.data.data)
      //console.clear();
    } catch (error) {
      console.log('Error:', error.message);
      // Otro manejo de errores
    }
  };

  const cargarAdmin = async () => {
    try {
      const respuesta = await axios.get(`http://localhost:8080/api/administrador/`);
      setAdmin(respuesta.data.data);
      //console.log(respuesta.data.data)
      //console.clear();
    } catch (error) {
      console.log('Error:', error.message);
      // Otro manejo de errores
    }
  };

  /*Intento de modal */
  const [mode, setMode] = useState('');
  const [title, setTitle] = useState('');
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);

  const handleShow = (mode, id, nombreVent, apePaternoVent, apeMaternoVent,
    correoElectronico, pass, status, changePassword) => {
    setId('');
    setNombreVent('');
    setApePaternoVent('');
    setApeMaternoVent('');
    setCorreoElectronico('');
    setPass('');
    setStatus(true);
    setChangePassword(false);
    setMode(mode);
    if (mode === "add") {
      setTitle('Registrar ventanilla');
    } else if (mode === "edit") {
      setTitle('Editar ventanilla');
      setId(id);
      setNombreVent(nombreVent);
      setApePaternoVent(apePaternoVent);
      setApeMaternoVent(apeMaternoVent);
      setCorreoElectronico(correoElectronico);
      setPass(pass);
      setStatus(status);
      setChangePassword(changePassword);
      setAdminId(adminId);
      //setMode(mode);
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
    if (![nombreVent, apePaternoVent, /*apeMaternoVent*/, correoElectronico, pass].every(field => field !== '')) {
      Swal.fire({
        icon: 'warning',
        title: 'Llena todos los campos',
        showConfirmButton: false,
        timer: 1500
      });
    } else {
      if (modo === "add") {
        parametros = {
          nombreVent: nombreVent.trim(), apePaternoVent: apePaternoVent.trim(), apeMaternoVent: apeMaternoVent.trim(),
          correoElectronico: correoElectronico.trim(), pass: pass.trim(), status: status, changePassword: changePassword, admin: { id: adminId }
        };
        metodo = 'POST';
      } else {
        parametros = {
          id: id, nombreVent: nombreVent.trim(), apePaternoVent: apePaternoVent.trim(), apeMaternoVent: apeMaternoVent.trim(),
          correoElectronico: correoElectronico.trim(), pass: pass.trim(), status: status, changePassword: changePassword, admin: { id: adminId }
        };
        metodo = 'PUT';
      }
      enviarSolicitud(metodo, parametros);
    }
  }

  const enviarSolicitud = async (metodo, parametros) => {
    if (metodo === 'PUT') {
      await axios({ method: metodo, url: `http://localhost:8080/api/ventanillas/${id}`, data: parametros })
        .then(function (respuesta) {
          var hasError = respuesta.data.status;
          var msj = respuesta.data.message;
          Swal.fire({
            icon: 'success',
            iconColor: '#58BEC4',
            title: msj,
            text: 'Ventanilla: ' + nombreVent + ' ' + apePaternoVent,
            showConfirmButton: false,
            timer: 2000
          });
          if (hasError === false) {
            cargarVentanilla();
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
          cargarVentanilla();
          handleClose();
        });
    } else {
      await axios({ method: metodo, url: urlVenta, data: parametros })
        .then(function (respuesta) {

          var hasError = respuesta.data.status;
          var msj = respuesta.data.message;
          Swal.fire({
            icon: 'success',
            iconColor: '#58BEC4',
            title: msj,
            text: 'Ventanilla: ' + nombreVent + ' ' + apePaternoVent,
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
            cargarVentanilla();
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
          console.log(error);*/
        })
        .finally(function () {
          cargarVentanilla();
          handleClose();
        });
    }

  }

  const changeStatus = (id, nombreVent, apePaternoVent, apeMaternoVent,
    correoElectronico, pass, status, changePassword) => {
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
          nombreVent: nombreVent,
          apePaternoVent: apePaternoVent,
          apeMaternoVent: apeMaternoVent,
          correoElectronico: correoElectronico,
          pass: pass,
          status: nuevoStatus, // Asignar el nuevo estado
          changePassword: changePassword
        };

        axios.patch(`http://localhost:8080/api/ventanillas/${id}`, data)
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
              text: `Solicitante: ${nombreVent} ${apePaternoVent}`,
              showConfirmButton: false,
              timer: 2000
            });

            if (hasError === false) {
              cargarVentanilla();
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
            cargarVentanilla();
            handleClose();
          });
      }
    });
  }

  ///BUSCAR 
  const [searchTerm, setSearchTerm] = useState("");
  const filteredData = vetanilla.filter(item =>
    item.nombreVent.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.apePaternoVent.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.apeMaternoVent.toLowerCase().includes(searchTerm.toLowerCase()) ||
    item.correoElectronico.toLowerCase().includes(searchTerm.toLowerCase())
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
                  <th colSpan="5" style={{ fontSize: '24px', fontWeight: 'bold' }}>VENTANILLAS</th>
                </tr>
                <tr>
                  {/*<th>ID</th>*/}
                  <th >Nombre(s)</th>
                  <th>Apellido(s)</th>
                  <th>Correo electrónico</th>
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
                    <td className="rounded-border">{item.nombreVent}</td>
                    <td className="rounded-border">{item.apePaternoVent} {item.apeMaternoVent}</td>
                    <td className="rounded-border">{item.correoElectronico}</td>
                    <td className="rounded-border">{
                      item.status ? (
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
                              handleShow('edit', item.id, item.nombreVent, item.apePaternoVent,
                                item.apeMaternoVent, item.correoElectronico, item.pass, item.status, item.changePassword);
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
                        <button className="btn-b">
                          <FeatherIcon
                            icon='trash-2'
                            style={{ color: 'black' }}
                            onClick={() => changeStatus(item.id, item.nombreVent, item.apePaternoVent,
                              item.apeMaternoVent, item.correoElectronico, item.pass, item.status, item.changePassword)}
                          />
                        </button>
                      ) : (
                        <button className="btn-inactive" >
                          <FeatherIcon
                            icon='pocket'
                            style={{ color: 'black' }}
                            onClick={() => changeStatus(item.id, item.nombreVent, item.apePaternoVent,
                              item.apeMaternoVent, item.correoElectronico, item.pass, item.status, item.changePassword)}
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
          <input type="text" placeholder="Buscar" value={searchTerm} onChange={e => setSearchTerm(e.target.value)} />
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
                <Form.Label style={{ color: '#2A4172' }}><b>Nombre(s)</b></Form.Label>
                <Form.Control
                  required
                  type="text"
                  value={nombreVent} onChange={(e) => setNombreVent(e.target.value)}
                //placeholder="First name"
                />
                {/*<Form.Control.Feedback>Completado</Form.Control.Feedback>*/}
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustom02">
                <Form.Label style={{ color: '#2A4172' }}><b>Apellido Paterno</b></Form.Label>
                <Form.Control
                  required
                  type="text"
                  value={apePaternoVent} onChange={(e) => setApePaternoVent(e.target.value)}
                //placeholder="Last name"
                />

              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group as={Col} md="6" controlId="validationCustom03">
                <Form.Label style={{ color: '#2A4172' }}><b>Apellido Materno</b></Form.Label>
                <Form.Control type="text" required
                  value={apeMaternoVent} onChange={(e) => setApeMaternoVent(e.target.value)} />
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustom04">
                <Form.Label style={{ color: '#2A4172' }}><b>Correo</b></Form.Label>
                <Form.Control type="text" required
                  value={correoElectronico} onChange={(e) => setCorreoElectronico(e.target.value)} />
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

export default VentanillaScreen