import React from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { save } from "../../services/usuarios/usuarioService";

export default function FormUser(props) {
  const { usuario, fetchData, openClose } = props;
  const initialValues = {
    nombre: "",
    primerApellido: "",
    segundoApellido: "",
    correo: "",
    contrasena: "",
    confirmContrasena: "",
    rol: "",
  };

  const roles = [
    { id: 1, nombre: "admin" },
    { id: 2, nombre: "cliente" },
  ];

  const validationSchema = Yup.object({
    nombre: Yup.string().required("Requerido"),
    primerApellido: Yup.string().required("Requerido"),
    segundoApellido: Yup.string().required("Requerido"),
    correo: Yup.string().email("Invalid email address").required("Requerido"),
    contrasena: Yup.string()
      .required("Requerido")
      .min(6, "Minimo 6 caracteres"),
    confirmContrasena: Yup.string().oneOf(
      [Yup.ref("contrasena"), null],
      "Las contraseñas deben coincidir"
    ),
    rol: Yup.string().required("Requerido"),
  });

  const handleSubmit = async (values) => {
    console.log(values);
    // {
    //     "id_usuario": 1,
    //     "nombre": "Cristian ",
    //     "primer_apellido": "Rodriguez",
    //     "segundo_apellido": "Rodriguez",
    //     "correo": "redalphasiete1@gmail.com",
    //     "contrasena": "$2a$10$a.akbMmP6GqjVK6bpA1ECu0q9pwj0onCVLLlhHUkC6trTkW7eaHhS",
    //     "estatus": true,
    //     "rol": {
    //         "id_rol": 1,
    //         "nombre": "admin"
    //     }
    // }
    const preRol = roles.find((rol) => rol.id === parseInt(values.rol));

    const usuario = {
      nombre: values.nombre,
      primer_apellido: values.primerApellido,
      segundo_apellido: values.segundoApellido,
      correo: values.correo,
      contrasena: values.contrasena,
      estatus: true,
      rol: {
        id_rol: preRol.id,
        nombre: preRol.nombre,
        },
    };
    const response = await save(usuario);

    if (response !== null) {
      fetchData();
      openClose();
      alert("Usuario creado");
    }
  };

  return (
    <div>
      <h1>Crear Usuario</h1>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        <Form>
          <div className="mb-3">
            <label htmlFor="nombre" className="form-label">
              Nombre
            </label>
            <Field
              type="text"
              id="nombre"
              name="nombre"
              className="form-control"
            />
            <ErrorMessage
              name="nombre"
              component="div"
              className="text-danger"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="primerApellido" className="form-label">
              Primer Apellido
            </label>
            <Field
              type="text"
              id="primerApellido"
              name="primerApellido"
              className="form-control"
            />
            <ErrorMessage
              name="primerApellido"
              component="div"
              className="text-danger"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="segundoApellido" className="form-label">
              Segundo Apellido
            </label>
            <Field
              type="text"
              id="segundoApellido"
              name="segundoApellido"
              className="form-control"
            />
            <ErrorMessage
              name="segundoApellido"
              component="div"
              className="text-danger"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="correo" className="form-label">
              Correo
            </label>
            <Field
              type="email"
              id="correo"
              name="correo"
              className="form-control"
            />
            <ErrorMessage
              name="correo"
              component="div"
              className="text-danger"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="contrasena" className="form-label">
              Contraseña
            </label>
            <Field
              type="password"
              id="contrasena"
              name="contrasena"
              className="form-control"
            />
            <ErrorMessage
              name="contrasena"
              component="div"
              className="text-danger"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="confirmContrasena" className="form-label">
              Confirmar Contraseña
            </label>
            <Field
              type="password"
              id="confirmContrasena"
              name="confirmContrasena"
              className="form-control"
            />
            <ErrorMessage
              name="confirmContrasena"
              component="div"
              className="text-danger"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="rol" className="form-label">
              Rol
            </label>
            <Field as="select" id="rol" name="rol" className="form-select">
              <option value="">Selecciona un rol</option>
              {roles.map((rol) => (
                <option key={rol.id} value={rol.id}>
                  {rol.nombre}
                </option>
              ))}
            </Field>
            <ErrorMessage name="rol" component="div" className="text-danger" />
          </div>
          <button type="submit" className="btn btn-primary">
            Enviar
          </button>
        </Form>
      </Formik>
    </div>
  );
}
