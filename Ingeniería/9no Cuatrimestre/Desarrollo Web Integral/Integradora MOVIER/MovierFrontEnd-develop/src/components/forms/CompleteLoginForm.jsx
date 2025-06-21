import React, { useEffect, useState } from "react";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { Container, Form, InputGroup } from "react-bootstrap";
import { Input, Spinner } from "@nextui-org/react";
import { passwordRegex } from "../../utils/regex";
import api from "../../config/axios/client-gateway";
import endpoints from "../../utils/endpoints";
import Swal from "sweetalert2";
import "../../css/auth/Login.css";
import { Button } from "@mui/material";
import LoginIcon from '@mui/icons-material/Login';
export default function CompleteLoginForm() {
  const [password, setPassword] = useState("");
  const [validPassword, setValidPassword] = useState(true);
  const [confirmPassword, setConfirmPassword] = useState("");
  const [validConfirmPassword, setValidConfirmPassword] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);

  useEffect(() => {
    if (isSubmitted) {
      setValidPassword(passwordRegex.test(password));
    }
  }, [password, isSubmitted]);

  useEffect(() => {
    if (isSubmitted) {
      setValidConfirmPassword(password && password === confirmPassword);
    }
  }, [confirmPassword, password, isSubmitted]);

  const handleSubmitForm = async (evt) => {
    evt.preventDefault();
    setIsSubmitted(true);

    const isPasswordValid = passwordRegex.test(password);
    const isConfirmPasswordValid = password && password === confirmPassword;

    setValidPassword(isPasswordValid);
    setValidConfirmPassword(isConfirmPasswordValid);

    if (isPasswordValid && isConfirmPasswordValid) {
      try {
        setIsLoading(true);
        const response = await api.doPost(endpoints.SetPasswordFunction, {
          username: localStorage.getItem("email"),
          temporary_password: localStorage.getItem("password"),
          new_password: password,
        });

        if (response.status === 200) {
          Swal.fire({
            title: "¡Éxito!",
            text: response.data.message,
            icon: "success",
            confirmButtonText: "Cerrar",
            allowEscapeKey: false,
          }).then(async () => {
            Swal.fire({
              html: '<h3>Iniciando sesión...</h3><span class="spinner-border spinner-border-sm"></span>',
              showConfirmButton: false,
              allowOutsideClick: false,
              allowEscapeKey: false,
            });

            const responseLogin = await api.doPost(endpoints.LoginFunction, {
              username: localStorage.getItem("email"),
              password: password,
            });
            if (responseLogin.status === 200) {
              localStorage.removeItem("email");
              localStorage.removeItem("password");
              localStorage.setItem(
                "accessToken",
                responseLogin.data.access_token
              );
              localStorage.setItem(
                "refreshToken",
                responseLogin.data.refresh_token
              );
              localStorage.setItem("idToken", responseLogin.data.id_token);
              localStorage.setItem("role", responseLogin.data.role);
              localStorage.setItem("userId", responseLogin.data.id);
              window.location.href = "/home";
            }
          });
        }
      } catch (error) {
      } finally {
        setIsLoading(false);
      }
    }
  };

  return (
    <>
      <div className="login">
        <div className="card-login">
          <h1 className="title movier">MOVIER</h1>
          <h4 className="subtitle">Completa tu registro</h4>
          <Container className="login-container">
            <Form onSubmit={handleSubmitForm}>
              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Input
                  type={showPassword ? "text" : "password"}
                  label="Contraseña"
                  placeholder="Ingresa tu contraseña"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  endContent={
                    <Button onClick={() => setShowPassword(!showPassword)}>
                      {showPassword ? <FaEyeSlash /> : <FaEye />}
                    </Button>
                  }
                  color="secondary"
                  errorMessage={validPassword ? "" : "Contraseña no válida."}
                  isInvalid={!validPassword}
                />
              </Form.Group>
              <Form.Group
                className="mb-3"
                controlId="formBasicConfirmedPassword"
              >
                <Input
                  type={showConfirmPassword ? "text" : "password"}
                  label="Confirmar contraseña"
                  placeholder="Confirma tu contraseña"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  endContent={
                    <Button
                      onClick={() =>
                        setShowConfirmPassword(!showConfirmPassword)
                      }
                    >
                      {showConfirmPassword ? <FaEyeSlash /> : <FaEye />}
                    </Button>
                  }
                  color="secondary"
                  errorMessage={
                    validConfirmPassword ? "" : "Las contraseñas no coinciden."
                  }
                  isInvalid={!validConfirmPassword}
                />
              </Form.Group>
              <Form.Text className="mb-3">
                Recuerda que tu contraseña debe contener mínimo 8 caracteres con
                al menos: <br />
                - Una letra mayúscula. <br />
                - Una letra minúscula. <br />
                - Un número. <br />
                - Un carácter especial. <br />
              </Form.Text>
              <Button
                className="mt-3"
                variant="contained"
                disabled={isLoading}
                style={{ width: "100%" }}
                type="submit"
                endIcon={!isLoading && <LoginIcon />}

              >
                {!isLoading ? (
                  "Completar registro"
                ) : (
                  <Spinner color="secondary" />
                )}
              </Button>
            </Form>
          </Container>
        </div>
      </div>
    </>
  );
}
