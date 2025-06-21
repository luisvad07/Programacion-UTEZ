import { Axios } from 'axios';
import { useFormik } from 'formik';
import React, { useState } from 'react';
import { Button, Col, Form, Modal, Row, Spinner, } from 'react-bootstrap';
import * as yup from "yup"
import AxiosClient from '../../shared/plugins/axios';
import Alert from '../../shared/plugins/alerts';


const RecoverPassword = ({ isOpen, onClose }) => {

    const [loading, setLoading] = useState(false);

    const form = useFormik({
        initialValues: {
            email: ""
        },
        validationSchema: yup.object().shape({
            email: yup.string().required("Campo obligatorio")
        }),
        onSubmit: async (values) => {
            try {
                setLoading(true);
                const response = await AxiosClient({
                    url: "/auth/reset/",
                    method: "POST",
                    data: JSON.stringify(values)
                })
                if (!response.error) {
                    Alert.fire({
                        text: "Se ha enviado un correo con tu nueva contraseña",
                        title: "Exitoso",
                        icon: "check",
                        confirmButtonText: "Aceptar",
                        confirmButtonColor: "#3085d6"
                    })
                }
            } catch (error) {
                Alert.fire({
                    text: "Error al recuperar tu contraseña, valida que tu correo este registrado",
                    title: "Error",
                    icon: "x",
                    confirmButtonText: "Aceptar",
                    confirmButtonColor: "#3085d6"
                })
            } finally {
                handleClose();
                setLoading(false);

            }
        }
    })
    const handleClose = () => {
        form.resetForm();
        onClose();
    };
    return (
        <Modal show={isOpen} backdrop="static" onHide={handleClose} >
            <Modal.Header>
                <Modal.Title>Recuperacion de contraseña</Modal.Title>

            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={form.handleSubmit}>
                    <Form.Group className='mb-2'>
                        <Form.Label htmlFor='email'>Email</Form.Label>
                        <Form.Control
                            name='email'
                            placeholder=''
                            value={form.values.email}
                            onChange={form.handleChange} />
                        {form.errors.email && (
                            <span className='error-text'>{form.errors.email}</span>
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
                                <Button
                                    variant="outline-success"
                                    disabled={loading}
                                    type='submit'
                                >
                                    {loading ? (
                                        <>
                                            <Spinner
                                                as="span"
                                                animation="border"
                                                size="sm"
                                                role="status"
                                                aria-hidden="true"
                                            />
                                            Cargando...
                                        </>
                                    ) : (
                                        'Enviar'
                                    )}
                                </Button>

                            </Col>
                        </Row>
                    </Form.Group>
                </Form>
            </Modal.Body>
        </Modal>
    );
}

export default RecoverPassword;
