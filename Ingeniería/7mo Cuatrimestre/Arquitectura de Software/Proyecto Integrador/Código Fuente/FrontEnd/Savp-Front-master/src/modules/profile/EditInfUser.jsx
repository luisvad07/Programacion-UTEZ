import React, { useState, useEffect } from "react";
import { useFormik } from "formik";
import * as yup from "yup";
import { Button, Col, FormGroup, Modal, Row, Form, Alert } from "react-bootstrap";
import AxiosClient from "../../shared/plugins/axios";

const EditInfUser = ({ isOpen, userData, onClose, token, data }) => {
  const [alertMessage, setAlertMessage] = useState(null);

  useEffect(() => {
    form.setValues({
      user_fk: userData?.id,
      name: userData?.name || "",
      birthday: userData?.birthday || "",
      address: userData?.address || "",      
    });
  }, [isOpen, userData]);

  const form = useFormik({
    initialValues: {
      user_fk: userData?.id,
      name: userData?.name || "",
      birthday: userData?.birthday || "",
      address: userData?.address || "",
    },
    validationSchema: yup.object().shape({
      name: yup.string().required("Campo obligatorio"),
      birthday: yup.string().required("Campo obligatorio"),
      address: yup.string().required("Campo obligatorio"),
    }),
    onSubmit: async (values) => {
      try {
        const response = await AxiosClient({
          method: "PUT",
          url: "/personal/solo/",
          data: JSON.stringify(values),
          headers: { Authorization: `Bearer ${token}` },
        });

        if (!response.error) {
          setAlertMessage("Los datos se actualizaron correctamente.");
          onClose();
        } else {
          setAlertMessage("Error al actualizar los datos.");
        }
      } catch (error) {
        console.log(error);
        setAlertMessage("Error al actualizar los datos.");
      }finally{
        data()
      }

    },
  });

  const handleClose = () => {
    form.resetForm();
    setAlertMessage(null);
    onClose();
  };

  return (
    <Modal
      backdrop="static"
      show={isOpen}
      onHide={handleClose}
      keyboard={false}
    >
      <Modal.Header>
        <Modal.Title>Editar Datos de perfil</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {alertMessage && <Alert variant="info">{alertMessage}</Alert>}
        <Form onSubmit={form.handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label htmlFor="Nombre">Nombre</Form.Label>
            <Form.Control
              name="name"
              placeholder="Ingrese su nombre completo"
              value={form.values.name}
              onChange={form.handleChange}
              onBlur={form.handleBlur}
            />
            {form.errors.name && form.touched.name && (
              <span className="error-text">{form.errors.name}</span>
            )}
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label htmlFor="descripcion">Fecha de nacimiento</Form.Label>
            <Form.Control
              name="birthday"
              placeholder="02/03/2002"
              value={form.values.birthday}
              onChange={form.handleChange}
              onBlur={form.handleBlur}
            />
            {form.errors.birthday && form.touched.birthday && (
              <span className="error-text">{form.errors.birthday}</span>
            )}
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label htmlFor="descripcion">Direccion</Form.Label>
            <Form.Control
              name="address"
              placeholder="calle 5"
              value={form.values.address}
              onChange={form.handleChange}
              onBlur={form.handleBlur}
            />
            {form.errors.address && form.touched.address && (
              <span className="error-text">{form.errors.address}</span>
            )}
          </Form.Group>
          <Form.Group className="mb-3">
            <Row>
              <Col className="text-end">
                <Button
                  variant="outline-danger"
                  className="me-2"
                  onClick={handleClose}
                >
                  &nbsp;Cancelar
                </Button>
                <Button variant="outline-success" type="submit">
                  &nbsp;Guardar
                </Button>
              </Col>
            </Row>
          </Form.Group>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default EditInfUser;