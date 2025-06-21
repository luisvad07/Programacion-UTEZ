import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Container, Modal, Card, Col, Row, Badge, Button, Form, InputGroup } from 'react-bootstrap'
import DataTable, { createTheme } from 'react-data-table-component'
import '../../../shared/plugins/Screens.css'

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import FeatherIcon from 'feather-icons-react/build/FeatherIcon'
import Swal from 'sweetalert2';
import { format, parseISO } from 'date-fns';

const HorarioScreen = () => {

  const navigate = useNavigate();
  const urlHorario = `http://localhost:8080/api/horarios/`

  /*Cargar Ventanilla */
  const [horario, setHorario] = useState([]);
  const [venta, setVentanilla] = useState([]);

  const [id, setId] = useState('');
  const [diaSemana, setDia] = useState('');
  const [horarioInicio, setInicio] = useState('');
  const [horarioFin, setFin] = useState('');
  //const [cantidadRepeticiones, setRepeticion] = useState('');
  const [status, setStatus] = useState('');

  const [ventanillaId, setVentaId] = useState(2);

  useEffect(() => {
    cargarHorarios();
    cargarVentanilla();
  }, []);

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

  /*Intento de modal */

  const [isLoading, setIsLoading] = useState('');
  const [mode, setMode] = useState('');
  const [title, setTitle] = useState('');
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);

  // Agrega el estado para el DatePicker
  const [selectedDate, setSelectedDate] = useState(null);
  // En el evento onChange del DatePicker, actualiza el estado selectedDate
  const handleDatePickerChange = (date) => {
    setSelectedDate(date);
  }

  const handleShow = (mode, id, diaSemana, horarioInicio, horarioFin, status) => {
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
      setDia(format(parseISO(diaSemana), 'EEEE'));
      setInicio(parseISO(horarioInicio));
      setFin(parseISO(horarioFin));
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
    if (![diaSemana, horarioInicio, horarioFin].every(field => field !== '')) {
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
          horarioInicio: horarioInicio,
          horarioFin: horarioFin,
          status: status,
          ventanilla: { id: ventanillaId }
        };
        metodo = 'POST';
      } else {
        parametros = {
          id: id,
          diaSemana: diaSemana.trim(),
          horarioInicio: horarioInicio,
          horarioFin: horarioFin,
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
          horarioInicio: horarioInicio,
          horarioFin: horarioFin,
          status: nuevoStatus
        };

        axios.patch(`http://localhost:8080/api/horarios/${id}`, data)
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
                    <td className="rounded-border">{item.horarioInicio}</td>
                    <td className="rounded-border">{item.horarioFin}</td>
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
                              handleShow(
                                'edit', item.id, item.diaSemana, item.horarioInicio, item.horarioFin,
                                item.status)
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
                            onClick={() => changeStatus(item.id, item.diaSemana, item.horarioInicio, item.horarioFin,
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
                <Form.Label>{diaSemana}</Form.Label>
                {/*<Form.Control.Feedback>Completado</Form.Control.Feedback>*/}
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustom02">
                <Form.Label style={{ color: '#2A4172' }}><b>Hora de Inicio</b></Form.Label>
                <DatePicker
                  selected={horarioInicio}
                  onChange={(date) => setInicio(date)}
                  showTimeSelect
                  dateFormat="yyyy-MM-dd HH:mm" // Ajusta el formato aquí
                  timeFormat="HH:mm"
                  timeIntervals={15}
                />
              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group as={Col} md="6" controlId="validationCustom03">
                <Form.Label style={{ color: '#2A4172' }}><b>Horario de Fin</b></Form.Label>
                <DatePicker
                  selected={horarioFin}
                  onChange={(date) => setFin(date)}
                  showTimeSelect
                  dateFormat="yyyy-MM-dd HH:mm" // Ajusta el formato aquí
                  timeFormat="HH:mm"
                  timeIntervals={15}
                />

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

export default HorarioScreen