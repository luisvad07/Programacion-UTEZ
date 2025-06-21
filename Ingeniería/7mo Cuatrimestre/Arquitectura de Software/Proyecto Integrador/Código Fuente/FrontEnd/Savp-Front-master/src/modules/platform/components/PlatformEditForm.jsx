import React from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import AxiosClient from '../../../shared/plugins/axios';
import { Button, Col, Modal, Row, Form } from 'react-bootstrap';

const PlatformEditForm = ({ isOpen, data, onClose, token, existingPlatformData }) => {
  const form = useFormik({
    initialValues: {
      plataforma: existingPlatformData ? existingPlatformData.plataforma : '',
      status: existingPlatformData ? existingPlatformData.status : true,
    },
    validationSchema: yup.object().shape({
      plataforma: yup.string().required('Campo obligatorio'),
    }),
    onSubmit: async (values) => {
      try {
        const response = await AxiosClient({
          method: 'PUT',
          url: `/plataforma/${existingPlatformData.id}`,
          data: JSON.stringify(values),
          headers: { Authorization: `Bearer ${token}` },
        });
        if (!response.error) {
          data();
          handleClose();
        }
      } catch (error) {
        console.log(error);
      }
    },
  });

  const handleClose = () => {
    form.resetForm({ values: existingPlatformData });
    onClose();
  };

  return (
    <Modal backdrop="static" show={isOpen} onHide={handleClose} keyboard={false}>
      <Modal.Header>
        <Modal.Title>Editar Plataforma</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={form.handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label htmlFor="plataforma">Plataforma</Form.Label>
            <Form.Control
              name="plataforma"
              placeholder=""
              value={form.values.plataforma}
              onChange={form.handleChange}
              type="text"
            />
            {form.errors.plataforma && (
              <span className="error-text">{form.errors.plataforma}</span>
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

export default PlatformEditForm;
