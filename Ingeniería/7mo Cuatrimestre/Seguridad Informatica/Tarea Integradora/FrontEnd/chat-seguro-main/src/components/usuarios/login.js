import React, { useContext } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { Eye, EyeSlash } from 'react-bootstrap-icons';
import { AuthContext } from '../../services/auth/context/AuthContext';
import { Button, Image } from 'react-bootstrap';
import { Send } from 'feather-icons-react/build/IconComponents';

export default function Login() {
  const {login, isLoading} = useContext(AuthContext);
  const initialValues = {
    username: '',
    password: '',
  };

  const validationSchema = Yup.object({
    username:  Yup.string().required('Este campo es requerido'),
    password: Yup.string().required('Este campo es requerido'),
  });
  const handleSubmit = (values) => {
    login(values.username, values.password);
  };

  const [passwordVisible, setPasswordVisible] = React.useState(false);

  return (
    <div className="container mt-2">
      <h3>Inicio de sesión</h3>
      <hr />
      <div className="text-center">
        <Image src={require('./logo.png')} width="200" />
      </div>
      <hr />
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        <Form>
          <div className="mb-3">
            <label htmlFor="username" className="form-label">
              Correo electrónico
            </label>
            <Field
              type="username"
              id="username"
              name="username"
              className="form-control"
            />
            <ErrorMessage name="username" component="div" className="text-danger" />
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">
              Contraseña
            </label>
            <div className="input-group">
              <Field
                type={passwordVisible ? 'text' : 'password'}
                id="password"
                name="password"
                className="form-control"
              />
              <button
                type="button"
                className="btn btn-light"
                onClick={() => setPasswordVisible((prev) => !prev)}
              >
                {passwordVisible ? <EyeSlash /> : <Eye />}
              </button>
            </div>
            <ErrorMessage name="password" component="div" className="text-danger" />
          </div>
          <Button type="submit" className="btn btn-primary" disabled={isLoading} >
            {isLoading ? 'Cargando...' : 'Iniciar sesión'} <Send />
          </Button>
        </Form>
      </Formik>
    </div>
  );
}
