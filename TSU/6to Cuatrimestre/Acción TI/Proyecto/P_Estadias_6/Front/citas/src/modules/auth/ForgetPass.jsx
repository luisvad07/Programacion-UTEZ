import React, { useEffect, useState } from "react";
import { Form, Container, Row, Col, Button, InputGroup } from 'react-bootstrap';
import FeatherIcon from "feather-icons-react";
import fondo from "../../assets/Fondo.jpeg";
import Logo from "../../assets/Logo.png";
import { Navigate, useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import * as yup from 'yup';
import axios from "axios";
import Swal from 'sweetalert2';
import Loading from "../../shared/plugins/Loading";

const ForgetPass = () => {
  const [showPassword, setShowPassword] = useState(false);

  const handlePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  return (
    <Container fluid style={{
      backgroundImage: `url(${fondo})`,
      backgroundSize: 'cover',
      minHeight: '100vh',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
    }}>

      <Form className="form_container">
        {/* Logo and title_container are unchanged */}
        <Col md={7} lg={5} className="mx-auto"> {/* Utiliza la clase "mx-auto" para centrar horizontalmente */}
          <div style={{ textAlign: 'center' }}> {/* Agrega "textAlign: 'center'" para centrar horizontalmente el contenido */}
            <img src={Logo} alt="Logo" className="logo mb-4" style={{ margin: '0 auto' }} /> {/* Aplica "margin: 0 auto" para centrar la imagen */}
          </div>
        </Col>
        {/* ... */}
        <Form.Group className="mb-1" controlId="email_field">
          {/*<Form.Label>Correo Institucional</Form.Label>*/}
          <div className="input_container">
            <FeatherIcon
              style={{ marginRight: '80px' }}
              icon="mail"
              className="icon"
              fill="none"
              height="24"
              width="24"
            />
            <Form.Control
              type="text"
              placeholder="Correo Institucional"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="input_field"
            />
          </div>
        </Form.Group>
        <Form.Group className="mb-3" controlId="password_field">
          {/*<Form.Label>Contraseña</Form.Label>*/}
          <div className="input_container">
            <FeatherIcon
              icon={showPassword ? 'eye-off' : 'eye'}
              className="icon"
              onClick={handlePasswordVisibility}
            />
            <Form.Control
              type={showPassword ? 'text' : 'password'}
              placeholder="******"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="input_field"
              
            />
          </div>
        </Form.Group>

        {/* Sign In Button */}
        <button title="Sign In" type="submit" className="sign-in_btn"
          >
          <span>Iniciar Sesión</span>
        </button>
        <a href="/changePassword" className="mb-4" style={{ color: '#264B99' }}>¿Olvidaste tu contraseña?</a>
      </Form>
    </Container>
  )
}

export default ForgetPass