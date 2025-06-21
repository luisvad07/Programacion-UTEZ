import React, { useState } from "react";
import { Container, Form } from "react-bootstrap";
import { Spinner } from "@nextui-org/react";
import { emailRegex } from "../../utils/regex";
import Swal from "sweetalert2";
import api from "../../config/axios/client-gateway";
import endpoints from "../../utils/endpoints";
import { Input } from "@nextui-org/react";
import { Button } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
export default function SignUpForm() {
  const [email, setEmail] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};
    if (!emailRegex.test(email)) newErrors.email = "Email no válido";
    return newErrors;
  };

  const handleChange = (setter, field) => (event) => {
    setter(event.target.value);
    setErrors((prevErrors) => ({ ...prevErrors, [field]: "" }));
  };

  const handleSubmitForm = async (evt) => {
    evt.preventDefault();
    setIsSubmitted(true);

    const newErrors = validate();
    setErrors(newErrors);

    if (!isLoading && Object.keys(newErrors).length === 0) {
      setIsLoading(true);

      try {
        const response = await api.doPost(endpoints.CreateUserFunction, {
          username: email,
          user_name: email,
        });

        if (response.status === 200) {
          Swal.fire({
            title: "¡Éxito!",
            text: "Se ha enviado un email con una contraseña temporal, por favor inicia sesión para completar el registro.",
            icon: "success",
            confirmButtonText: "Ir a inicio de sesión",
            allowEscapeKey: false,
            allowOutsideClick: false,
            showConfirmButton: true,
            focusConfirm: true,
          }).then(() => {
            window.location.href = `/login?email=${email}`;
          });
        }
      } catch (e) {
        setErrors({ form: "Error en el registro" });
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
          <h4 className="subtitle">Registra tu cuenta</h4>
          <Container className="login-container">
            <Form onSubmit={handleSubmitForm} className="form-group">
              <Form.Group className="mb-3" controlId="emailFormCreate">
                <Input
                  type="email"
                  label="Correo"
                  placeholder="Ingresa tu correo electrónico"
                  value={email}
                  onChange={handleChange(setEmail, "email")}
                  errorMessage={errors.email}
                  isInvalid={!!errors.email}
                  color="secondary"
                />
                {errors.email && (
                  <div className="error-message">{errors.email}</div>
                )}
              </Form.Group>

              <Button
                variant="contained"
                disabled={isLoading}
                className="button-submit"
                type="submit"
                endIcon={!isLoading && <SendIcon />}
              >
                {!isLoading ? "Registrarse" : <Spinner color="secondary" />}
              </Button>
            </Form>
            <div className="create-account">
              <p className="text-account">
                ¿Ya tienes una cuenta? <a href="/login">Inicia sesión aquí</a>
              </p>
            </div>
          </Container>
        </div>
      </div>
    </>
  );
}
