import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Modal, Card, Col, Row, Badge, Button, Form, InputGroup } from 'react-bootstrap'
import DataTable, { createTheme } from 'react-data-table-component'
import '../../../shared/plugins/Screens.css'
import FeatherIcon from 'feather-icons-react/build/FeatherIcon'
import Swal from 'sweetalert2';

const CitaScreen = () => {

  const navigate = useNavigate();
  const urlCita = `http://localhost:8080/api/citas/`

  /*Cargar Ventanilla */
  const [citas, setCitas] = useState([]);
  const [venta, setVentanilla] = useState([]);

  const [id, setId] = useState('');
  const [fecha, setFecha] = useState('');
  const [hora, setHora] = useState('');
  const [numeroVentanilla, setNumeroVentanilla] = useState('');
  const [documentosAnexos, setDocumentosAnexos] = useState('');
  const [montoPago, setMontoPago] = useState('');
  const [atendida, setAtendida] = useState('');

  const [ventanillaId, setVentaId] = useState(localStorage.getItem('sesionId'));

  useEffect(() => {
    sesionActiva();
    cargarCitas();
    cargarVentanilla();
  }, []);

const sesionActiva = () => {
    const id = localStorage.getItem("sesionId")
    const rol = localStorage.getItem("rol")

    if (id === null || rol != 'ventanilla') {
        navigate('/login');
    }
}

  const cargarCitas = async () => {
    try {
      const respuesta = await axios.get(urlCita);
      setCitas(respuesta.data.data);
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

  /*Intento de modal */
  const [isLoading, setIsLoading] = useState('');
  const [mode, setMode] = useState('');
  const [title, setTitle] = useState('');
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);

  const handleShow = (mode, id, fecha, hora, numeroVentanilla, documentosAnexos, montoPago, atendida) => {
    if (mode === "view") {
      setTitle('Cita atendida');
      setId(id);
      setFecha(fecha);
      setHora(hora);
      setNumeroVentanilla(numeroVentanilla);
      setDocumentosAnexos(documentosAnexos);
      setMontoPago(montoPago);
      setAtendida(atendida);
      setMode(mode);
    } else if (mode === "edit") {
      setTitle('Cita por atender');
      setId(id);
      setFecha(fecha);
      setHora(hora);
      setNumeroVentanilla(numeroVentanilla);
      setDocumentosAnexos(documentosAnexos);
      setMontoPago(montoPago);
      setAtendida(atendida);
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

    if (modo === "edit") {
      parametros = {
        id: id,
        fecha: fecha,
        hora: hora,
        numeroVentanilla: numeroVentanilla,
        montoPago: montoPago,
        atendida: atendida,
        ventanilla: { id: ventanillaId }
      };
      metodo = 'PATCH';
    }
    //enviarSolicitud(metodo, parametros);

  }


  const changeStatus = (id, atendida) => {
    const nuevoStatus = !atendida; // Cambiar el estado actual
    Swal.fire({
      title: '¿Seguro de atender la cita?',
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
          atendida: nuevoStatus
        };
        axios.patch(`http://localhost:8080/api/citas/${id}`, data)
          .then(function (respuesta) {
            var hasError = respuesta.data.status;
            var msj = respuesta.data.message;
            let successMessage;
            if (nuevoStatus) {
              successMessage = 'Cita atendida correctamente';
            } else {
              successMessage = 'Cita Error';
            }
            Swal.fire({
              icon: 'success',
              iconColor: '#58BEC4',
              title: successMessage,
              showConfirmButton: false,
              timer: 2000
            });
            if (hasError === false) {
              cargarCitas();
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
            cargarCitas();
            handleClose();
          });
      }
    });
  }

  ///BUSCAR 
  const [searchTerm, setSearchTerm] = useState("");
  const filteredData = citas.filter(item =>
    item.documentosAnexos && item.documentosAnexos.toLowerCase().includes(searchTerm.toLowerCase())
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
                  <th colSpan="7" style={{ fontSize: '24px', fontWeight: 'bold' }}>CITAS</th>
                </tr>
                <tr>
                  {/*<th>ID</th>*/}
                  <th>No. Ventanilla</th>
                  <th>Fecha</th>
                  <th>Hora</th>
                  <th>Documentos</th>
                  <th>Monto Pago</th>
                  <th>Estado</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {filteredData.length === 0 ? (
                  <tr>
                    <td colSpan="9" style={{ textAlign: 'center' }}>No hay registros</td>
                  </tr>
                ) : (filteredData.map((item) => (
                  <tr key={item.id} style={{ border: 'none' }} className='mb-4'>
                    {/*<td className="rounded-border">{item.id}</td>*/}
                    <td className="rounded-border">{item.numeroVentanilla}</td>
                    <td className="rounded-border">{item.fecha}</td>
                    <td className="rounded-border">{item.hora}</td>
                    <td className="rounded-border">{item.documentosAnexos}</td>
                    <td className="rounded-border">{item.montoPago}</td>
                    <td className="rounded-border">
                      {item.atendida ? (
                        <Badge bg='success'>Atendida</Badge>
                      ) : (
                        <Badge bg='danger'>En espera</Badge>)}
                    </td>
                    <td style={{ background: '#2A4172', border: 'none' }}>
                      {item.atendida ? (
                        <button className="btn-b" /*style={{ marginRight: '5px' }}*/>
                          <FeatherIcon
                            icon='check-circle'
                            style={{ color: 'black' }}
                            onClick={() => handleShow('view', item.id, item.fecha, item.hora, item.numeroVentanilla,
                              item.documentosAnexos, item.montoPago, item.atendida)}
                          />
                        </button>
                      ) : (
                        <button className="btn-alert" /*style={{ marginRight: '5px' }}*/>
                          <FeatherIcon
                            icon='alert-circle'
                            style={{ color: 'black' }}
                            onClick={() => handleShow('edit', item.id, item.fecha, item.hora, item.numeroVentanilla,
                              item.documentosAnexos, item.montoPago, item.atendida)}
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
        </div>
      </div>


      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton className='titleModal' style={{ backgroundColor: '#58BEC4', color: 'white' }}>
          <Modal.Title>{title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form /*noValidate validated={validated}*/ onSubmit={handleSubmit}>
            <Row className="mb-3">
              <Form.Group as={Col} md="4" controlId="validationCustom01">
                <Form.Label style={{ color: '#2A4172' }}><b>No. Ventanilla</b></Form.Label>
                <Form.Control disabled
                  value={numeroVentanilla} onChange={(e) => setNumeroVentanilla(e.target.value)} />
              </Form.Group>
              <Form.Group as={Col} md="5" controlId="validationCustom02">
                <Form.Label style={{ color: '#2A4172' }}><b>Fecha</b></Form.Label>
                <Form.Control disabled
                  value={fecha} onChange={(e) => setFecha(e.target.value)} />
              </Form.Group>
              <Form.Group as={Col} md="3" controlId="validationCustom03">
                <Form.Label style={{ color: '#2A4172' }}><b>Hora</b></Form.Label>
                <Form.Control disabled
                  value={hora} onChange={(e) => setHora(e.target.value)} />
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} md="6" controlId="validationCustom04">
                <Form.Label style={{ color: '#2A4172' }}><b>Documentos requeridos</b></Form.Label>
                <Form.Control as="textarea" rows={3} type="text" disabled
                  value={documentosAnexos} onChange={(e) => setDocumentosAnexos(e.target.value)} />
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustom05">
                <Form.Label style={{ color: '#2A4172' }}><b>Monto</b></Form.Label>
                <Form.Control disabled
                  value={montoPago} onChange={(e) => setMontoPago(e.target.value)} />
              </Form.Group>
            </Row>
            {/* Renderizar los botones solo si el campo "atendida" es false */}
            {!atendida && (
              <>
                <Button className='me-2' variant='outline-danger' onClick={handleClose}>
                  <FeatherIcon icon='x' />&nbsp;Cerrar
                </Button>
                <Button variant="outline-success" type='button' onClick={() => changeStatus(id, atendida)}>
                  <FeatherIcon icon='check' />&nbsp;Atender
                </Button>
              </>
            )}
          </Form>
        </Modal.Body>
      </Modal>
    </>
  )
}


export default CitaScreen