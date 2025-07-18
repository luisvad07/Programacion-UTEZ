import React, { useEffect, useState } from "react";
import axios from "axios";
import FeatherIcon from "feather-icons-react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { FaUserAlt, FaCircle } from "react-icons/fa";

export const ProfileStudent = () => {
  const [nombre , setNombre] = useState(localStorage.getItem('nombre'));
  const [apellidos, setApellidos] = useState(localStorage.getItem('apellidos'));
  const [correo, setCorreo] = useState(localStorage.getItem('correo'));
  const [division, setDivision] = useState(localStorage.getItem('divisionAcademica'));
  const [matricula, setMatricula] = useState(localStorage.getItem('matricula'));
  const [carrera, setCarrera] = useState(localStorage.getItem('carrera'));
  const [grado, setGrado] = useState(localStorage.getItem('grado'));
  const [grupo, setGrupo] = useState(localStorage.getItem('grupo'));
  const [rol, setRol] = useState(localStorage.getItem('rol'));

  return (
    <>
      <Container>
        <br />
        <Row>
          <Col>
            <h1>Alumno</h1>
          </Col>
        </Row>

        <hr className="linea" />
        <Row style={{ paddingTop: '5px' }}>
          <Col>
            <Container className="squareProfile col-lg-10"></Container>
          </Col>
        </Row>

        <Row >
          <Container>
            <Row
              className="d-flex justify-content-start"
              style={{ paddingTop: '35px', paddingLeft: '60px' }}>
              <Col /*className="col-md-9 col-lg-6 col-xl-5"*/>
                <div
                  className="card styleProfile"
                  style={{ width: '40vh', height: '70vh', borderRadius: '15px', textAlign: 'center' }}
                >
                  <div className="card-body d-flex flex-column align-items-center justify-content-center">
                    <div style={{ margin: '40px' }}>
                      <FaUserAlt style={{ width: '120px', height: '120px', color: '#555555' }} />
                    </div>
                    <h5 className="card-title">
                      {nombre} {apellidos}
                    </h5>
                  </div>
                </div>
              </Col>
              <Col
                /*className="col-md-9 col-lg-6 col-xl-5"*/
                style={{ paddingTop: '140px' }}>
                <div className="form-outline">
                  <div className="input-group mb-4" style={{ width: '100%' }}>
                    <h2
                      type="email"
                      className="form-control form-control-lg"
                      aria-describedby="basic-addon2"
                      required
                    >
                      <FaCircle style={{ color: '#012258', marginRight: '15px' }} />
                      {nombre}
                    </h2>
                  </div>
                  <div className="input-group mb-4" style={{ width: '100%' }}>
                    <h2
                      type="email"
                      className="form-control form-control-lg"
                      aria-describedby="basic-addon2"
                      required
                    >
                      <FaCircle style={{ color: '#012258', marginRight: '15px' }} />
                      {apellidos}
                    </h2>
                  </div>
                  <div className="input-group mb-4" style={{ width: '100%' }}>
                    <h2
                      type="email"
                      className="form-control form-control-lg"
                      aria-describedby="basic-addon2"
                      required
                    >
                      <FaCircle style={{ color: '#012258', marginRight: '15px' }} />
                      {correo}
                    </h2>
                  </div>
                  <div className="input-group mb-4" style={{ width: '100%' }}>
                    <h2
                      type="email"
                      className="form-control form-control-lg"
                      aria-describedby="basic-addon2"
                      required
                    >
                      <FaCircle style={{ color: '#012258', marginRight: '15px' }} />
                      Carrera: {carrera}
                    </h2>
                  </div>
                </div>
              </Col>
              <Col /*className="col-md-9 col-lg-6 col-xl-5"*/
                style={{ paddingTop: '140px' }}>
                <div className="form-outline">
                  <div className="input-group mb-4" style={{ width: '90%' }}>
                    <h2
                      type="email"
                      className="form-control form-control-lg"
                      aria-describedby="basic-addon2"
                      required
                    >
                      <FaCircle style={{ color: '#012258', marginRight: '15px' }} />
                      Grado y grupo: {grado}° {grupo}
                    </h2>
                  </div>
                  <div className="input-group mb-4" style={{ width: '90%' }}>
                    <h2
                      type="divAca"
                      className="form-control form-control-lg"
                      aria-describedby="basic-addon2"
                      required
                    >
                      <FaCircle style={{ color: '#012258', marginRight: '15px' }} />
                      División: {division}
                    </h2>
                  </div>
                  <div className="input-group mb-4" style={{ width: '90%' }}>
                    <h2
                      type="email"
                      className="form-control form-control-lg"
                      aria-describedby="basic-addon2"
                      required
                    >
                      <FaCircle style={{ color: '#012258', marginRight: '15px' }} />
                      Rol: {rol.toUpperCase()}
                    </h2>
                  </div>
                </div>
              </Col>

            </Row>
          </Container>
        </Row>
      </Container>
    </>
  );
};


export default ProfileStudent
