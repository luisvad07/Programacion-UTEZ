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

const Login = () => {
  const [showPassword, setShowPassword] = useState(false);

  const handlePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  ///
  const baseUrl = `http://localhost:8080/api/`
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  let found = false;

  useEffect(() => {
    validateSession();
  }, []);

  const validateSession = () => {
    if (localStorage.getItem('sesionId')) {
      switch (localStorage.getItem('rol')) {
        case 'admin':
          navigate();
          break;
        case 'ventanilla':
          navigateToVentanilla();
          break;
        default:
          break;
      }
    }
  }

  const navigateToAdmin = () => {
    setIsLoading(true);
    // Simulate a loading delay for better user experience
    setTimeout(() => {
      setIsLoading(false);
      navigate('/adminDashboard');
    }, 1500);
  }

  const navigateToVentanilla = () => {
    setIsLoading(true);
    // Simulate a loading delay for better user experience
    setTimeout(() => {
      setIsLoading(false);
      navigate('/ventanillaDashBoard');
    }, 1500);
  }

  const buscarPersona = async () => {
    setIsLoading(true);
    let found = false;

    try {
      let usuario = null;
      let rol = '';

      const adminResp = await fetch(`${baseUrl}administrador/`);
      const adminData = await adminResp.json();

      for (let index = 0; index < adminData.data.length; index++) {
        const element = adminData.data[index];
        if (element.correoAdmin === email && element.pass === password) {
          usuario = element;
          rol = 'admin';
          found = true;
        }
      }

      if (!found) {
        const ventanillasResp = await fetch(`${baseUrl}ventanillas/`);
        const ventanillasData = await ventanillasResp.json();

        for (let index = 0; index < ventanillasData.data.length; index++) {
          const element = ventanillasData.data[index];
          if (element.correoElectronico === email && element.pass === password) {
            usuario = element;
            rol = 'ventanilla';
            found = true;
          }
        }
      }

      if (!found) {
        const solicitantesResp = await fetch(`${baseUrl}solicitantes/`);
        const solicitantesData = await solicitantesResp.json();

        for (let index = 0; index < solicitantesData.data.length; index++) {
          const element = solicitantesData.data[index];
          if (element.correoElectronico === email && element.pass === password) {
            found = true;
          }
        }
      }

      if (!found) {
        let errorMessage = "Usuario no encontrado";

        // Verificar si el correo electrónico es incorrecto
        const userWithEmail = adminData.data.find(element => element.correoAdmin === email);
        if (!userWithEmail) {
          errorMessage = "Correo electrónico incorrecto";
        }

        const userWithPassword = adminData.data.find(element => element.pass === email);
        if (!userWithPassword) {
          errorMessage = "Contraseña incorrecta";
        }

        Swal.fire({
          icon: 'error',
          iconColor: '#2A4172',
          title: errorMessage,
          showConfirmButton: false,
          timer: 1500
        });
      } else {
        if (rol === 'admin') {
          localStorage.setItem("rol", rol);
          localStorage.setItem("sesionId", usuario.id);
          navigateToAdmin();
        } else if (rol === 'ventanilla') {
          localStorage.setItem("rol", rol);
          localStorage.setItem("sesionId", usuario.id);
          navigateToVentanilla();
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Acceso denegado',
            text: 'Solicitantes no pueden entrar',
            showConfirmButton: false,
            timer: 1500
          });
        }
      }
    } catch (error) {
      console.error('Error en la solicitud de datos:', error);
      Swal.fire({
        icon: 'error',
        iconColor: '#2A4172',
        title: 'Ocurrió un error al buscar el usuario',
        text: 'Por favor, intenta nuevamente más tarde.',
        showConfirmButton: false,
        timer: 2000
      });
    } finally {
      setIsLoading(false); // Indicar que finalizó la carga
    }
  }



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
        <button title="Sign In" type="button" className="sign-in_btn"
          onClick={buscarPersona}
          disabled={isLoading || !email || !password}
        >
          {isLoading ? '. . .' : 'Iniciar sesión'}
        </button>
        {/*<a href="/changePassword" className="mb-4" style={{ color: '#264B99' }}>¿Olvidaste tu contraseña?</a>*/}
      </Form>
    </Container>
  )
}

export default Login;
