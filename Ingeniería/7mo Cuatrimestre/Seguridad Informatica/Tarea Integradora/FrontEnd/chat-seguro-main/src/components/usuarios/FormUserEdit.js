import React from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { save, update } from "../../services/usuarios/usuarioService";

export default function FormUserEdit(props) {
  const { usuario, fetchData, openClose } = props;
  const initialValues = {
    nombre: usuario ? usuario.nombre : "",
    primerApellido: usuario ? usuario.primer_apellido : "",
    segundoApellido: usuario ? usuario.segundo_apellido : "",
    rol: usuario ? usuario.rol.id_rol : "",
  };

  const roles = [
    { id: 1, nombre: "admin" },
    { id: 2, nombre: "cliente" },
  ];

  const validationSchema = Yup.object({
    nombre: Yup.string().required("Requerido"),
    primerApellido: Yup.string().required("Requerido"),
    segundoApellido: Yup.string().required("Requerido"),
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
    let userMod = usuario ? usuario : {};
    userMod.nombre = values.nombre;
    userMod.primer_apellido = values.primerApellido;
    userMod.segundo_apellido = values.segundoApellido;
    userMod.rol = {
        id_rol: preRol.id,
        nombre: preRol.nombre
    }
    console.log(JSON.stringify(usuario));
    const response = await update(userMod);
    if (response !== null) {
      fetchData();
      openClose();
      alert("Usuario modificado");
    }
  };

  return (
    <div>
      <h1>Editar Usuario</h1>
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
            // we need to call formik values to get the values from the f
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
            <label htmlFor="rol" className="form-label">
              Rol
            </label>
            <Field as="select" id="rol" name="rol" className="form-select">
              <option value="">Selecciona un rol</option>
              {roles.map((rol) => (
                <option selected={usuario ? usuario.rol.id_rol === rol.id : ""} key={rol.id} value={rol.id}>
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
