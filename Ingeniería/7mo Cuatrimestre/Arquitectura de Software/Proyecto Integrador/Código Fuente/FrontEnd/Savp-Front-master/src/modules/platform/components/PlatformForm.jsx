import React from 'react';
import { useFormik } from 'formik';
import * as yup from "yup";
import AxiosClient from '../../../shared/plugins/axios';
import { Button, Col, FormGroup, Modal, Row, Form, Alert } from 'react-bootstrap';

const PlatformForm = ({ isOpen, data, onClose, token }) => {
    const form = useFormik({
        initialValues: {
            plataforma: "",
            status: true
        },
        validationSchema: yup.object().shape({
            plataforma: yup.string().required("Campo obligatorio")
        }),
        onSubmit: async (values) => {
            try {
                const response = await AxiosClient({
                    method: "POST",
                    url: "/plataforma/",
                    data: JSON.stringify(values),
                    headers: { Authorization: `Bearer ${token}` }
                })
                Alert.fire({
                    title: "REGISTRO EXITOSO",
                    text: "",
                    icon: "check",
                    confirmButtonColor: "#3085d6",
                    confirmButtonText: "Aceptar",
                });

            } catch (error) {
                console.log(error);
                Alert.fire({
                    title: "REGISTRO FALLIDO",
                    text: "",
                    icon: "x",
                    confirmButtonColor: "#3085d6",
                    confirmButtonText: "Aceptar",
                });
            } finally {
                data();
                handleClose();
            }
        }
    })

    const handleClose = () => {
        form.resetForm();
        onClose();
    }

    return (
        <Modal backdrop="static" show={isOpen} onHide={handleClose} keyboard={false}>
            <Modal.Header>
                <Modal.Title>Registrar Plataforma</Modal.Title>

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
                        {form.errors.name && (
                            <span className="error-text">{form.errors.name}</span>
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
}

export default PlatformForm;
