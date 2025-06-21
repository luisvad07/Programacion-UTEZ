import React, { useState } from 'react';
import { useFormik } from 'formik';
import * as yup from 'yup';
import AxiosClient from '../../../shared/plugins/axios';
import { Button, Col, FormGroup, Modal, Row, Form, Alert } from 'react-bootstrap';

const ProductForm = ({ isOpen, data, onClose, token }) => {
    const [image, setImage] = useState(null);

    const form = useFormik({
        initialValues: {
            titulo: '',
            descripcion: '',
            imagen: ''
        },
        validationSchema: yup.object().shape({
            titulo: yup.string().required('Campo obligatorio'),
            descripcion: yup.string().required('Campo obligatorio'),
        }),
        onSubmit: async (values) => {
            try {
                const base64Image = await convertImageToBase64(image);
                values.imagen = base64Image;
                const response = await AxiosClient({
                    method: 'POST',
                    url: '/producto/',
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

    const handleImageChange = (e) => {
        const file = e.target.files[0];

        if (file) {
            setImage(file);
        }
    };

    const convertImageToBase64 = (imageFile) => {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();

            reader.onloadend = () => {
                resolve(reader.result);
            };

            reader.onerror = (error) => {
                reject(error);
            };

            reader.readAsDataURL(imageFile);
        });
    };

    const handleClose = () => {
        form.resetForm();
        setImage(null);
        onClose();
    };

    return (
        <Modal backdrop="static" show={isOpen} onHide={handleClose} keyboard={false}>
            <Modal.Header>
                <Modal.Title>Registrar Producto</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={form.handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="titulo">Titulo</Form.Label>
                        <Form.Control
                            name="titulo"
                            placeholder=""
                            value={form.values.titulo}
                            onChange={form.handleChange}
                        />
                        {form.errors.titulo && (
                            <span className="error-text">{form.errors.titulo}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="descripcion">Descripcion</Form.Label>
                        <Form.Control
                            name="descripcion"
                            placeholder=""
                            value={form.values.descripcion}
                            onChange={form.handleChange}
                        />
                        {form.errors.descripcion && (
                            <span className="error-text">{form.errors.descripcion}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="imagen">Imagen</Form.Label>
                        <Form.Control type="file" name="imagen" onChange={handleImageChange} />
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

export default ProductForm;
