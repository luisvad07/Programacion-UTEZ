import React, { useEffect, useState } from "react";
import axios from "axios";
import FeatherIcon from "feather-icons-react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { FaUserAlt, FaCircle } from "react-icons/fa";
import { Button, Form, Card } from "react-bootstrap";
import { AlignCenter } from "feather-icons-react/build/IconComponents";
import { show_alerta } from '../../shared/components/functions';
import { localhost } from '../../shared/plugins/axios'

export const Reporte = () => {
  const url = `http://${localhost}:8080/redre/files/upload`
  const cuatri = localStorage.getItem('grado');
  const matricula = localStorage.getItem('matricula');
  const isTSU = cuatri == 6 ? true : false;
  const isIng = cuatri == 11 ? true : false;
  const localStatus = localStorage.getItem("reportStatus");

  const [archivos, setArhivo] = useState(null);
  const [reporte, setReporte] = useState('');
  const [estudiante, setEstudiante] = useState({});
  const [status, setStatus] = useState('');
  const [mensaje, setMensaje] = useState('No Hay Mensaje');
  const [tipo, setTipo] = useState('');

  useEffect(() => {
    cargarReporte();
  });

  const cargarReporte = async () => {
    await axios.get(`http://${localhost}:8080/redre/files/all`).then(response => {
      let name = '';
      for (let index = 0; index < response.data.length; index++) {
        name = response.data[index].name;
        if ((name.includes("TSU") || name.includes("ING")) && name.includes(matricula)) {
          setReporte(name);
          break;
        }
      }
    }).then(() => {
      axios.get(`http://${localhost}:8080/redre/estudiante/matricula/${matricula}`).then(response => {
      setEstudiante(response.data);
      switch (localStatus) {
        case 'esperaRespuestaDeAsesor':
          setStatus('En Revisión');
          break;
        case 'rechazadoPorAsesor':
          setStatus('Rechazado por el Asesor');
          traerMensajeAsesor();
          break;
        case 'rechazadoPorResponsable':
          setStatus('Rechazado por el Responsable');
          traerMensajeResp();
          break;
        case 'rechazadoPorDepartamento':
          setStatus('Rechazado por el Departamento');
          traerMensajeDept();
          break;
        case 'null':
          setStatus('No enviado');
        break;
        default: setStatus('En Revisión');
      }
    })
    })
  }

  const traerMensajeAsesor = async () => {
    await axios.get(`http://${localhost}:8080/redre/mensajesAsesor/estudiante/${matricula}`).then(response => {
      setMensaje(response.data[0].mensaje);
    }).catch(error => {
      setMensaje('No hay mensaje');
    })
  }

  const traerMensajeResp = async () => {
    await axios.get(`http://${localhost}:8080/redre/mensajesResponsables/estudiante/${matricula}`).then(response => {
      setMensaje(response.data[0].mensaje);
    }).catch(error => {
      setMensaje('No hay mensaje');
    })
  }

  const traerMensajeDept = async () => {
    await axios.get(`http://${localhost}:8080/redre/mensajesDepartamento/estudiante/${matricula}`).then(response => {
      setMensaje(response.data[0].mensaje);
    }).catch(error => {
      setMensaje('No hay mensaje');
    })
  }

  const reporteUrl = `http://${localhost}:8080/redre/files/${matricula}Reporte${isTSU ? "TSU" : "ING"}.pdf`;

  const insertar = async () => {
    const f = new FormData();

    f.append("files", archivos[0]);
    f.append("matricula", matricula);
    f.append("type", isTSU ? "TSU" : "ING");

    await axios.post(url, f)
      .then(response => {
        const { data, status } = response;

        if (status === 200) {
          localStorage.setItem("reportStatus", "esperaRespuestaDeAsesor")
          show_alerta("Se ha subido el archivo correctamente", "success");
          cargarReporte();
        } else{
          show_alerta("No se pudo subir el archivo", "error");
        }
      }).catch(error => {
        show_alerta("No se pudo subir el archivo", "error");
      })

  }

  return (
    <>
      <Container >
        <br />
        <Row>
          <Col>
            <h1>Alumno</h1>
          </Col>
        </Row>
        <hr className="linea" />
        <Row className="justify-content-center align-items-center d-flex">

          <Col xs={6} className="text-center">
            <Row className='mb-3'>
              <h2 style={{ color: '#375689' }}>TSU</h2>
              <h3>6 cuatrimestre</h3>

              <Form.Group controlId="formFile" className="mb-3">
                <Form.Label></Form.Label>
                <Form.Control type="file" onChange={(e) => {setArhivo(e.target.files)}} disabled={(!isTSU) || (status==='En Revisión')} />
                <br />
                <Button variant="danger" onClick={() => insertar()} disabled={(!isTSU) || (status==='En Revisión')}>Subir</Button>
              </Form.Group>
            </Row>

            <hr className="linea" />
            <Row >
              <h2 style={{ color: '#375689' }}>Ingeniería/Licenciatura</h2>
              <h3>11 cuatrimestre</h3>

              <Form.Group controlId="formFile" className="mb-3" >
                <Form.Label ></Form.Label>
                <Form.Control  type="file" onChange={(e) => {setArhivo(e.target.files)}} disabled={(!isIng) || (status==='En Revisión')} />
                <br />
                <Button variant="danger" onClick={() => insertar()} disabled={(!isIng) || (status==='En Revisión')}>Subir</Button>
              </Form.Group>
            </Row>

          </Col>

          <Col xs={1} className="text-center">
          </Col>

          <Col xs={5} >
            <Container>
              <Card>
                <Card.Header style={{background: '#379B80', color: 'white'}}>Reportes Subidos</Card.Header>
                <Card.Body>
                  <Card.Text>
                    <h5>Reporte Actual: {isTSU ? "TSU" : ""}{isIng ? "Ingeniería/Licenciatura" : ""}</h5>
                    <h5>Estado del Reporte: {status}</h5>
                    <Button variant="danger" size="sm" className="mb-3" href={reporteUrl} hidden={reporte.length === 0 || status === 'No enviado'}>Descargar Reporte</Button>
                  <Form>
                        <Form.Group
                            className="mb-3"
                            controlId="exampleForm.ControlTextarea1"
                        >
                            <Form.Label>Retroalimentación</Form.Label>
                            <Form.Control as="textarea" rows={3} value={mensaje} disabled />
                        </Form.Group>
                    </Form>
                  </Card.Text>
                </Card.Body>
              </Card>
            </Container>

          </Col>
        </Row>



      </Container>
    </>
  )
}
export default Reporte;