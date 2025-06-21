import { useFormik } from 'formik';
import React, { useEffect, useState } from 'react';
import * as yup from "yup";
import AxiosClient from '../../../shared/plugins/axios';
import { Button, Col, Form, Modal, Row } from 'react-bootstrap';
import Alert from '../../../shared/plugins/alerts';

const ItemForm = ({ isOpen, data, onClose, token }) => {
    const [platforms, setPlatforms] = useState([]);
    const [products, setProducts] = useState([]);

    const form = useFormik({
        initialValues: {
            estado: 1,
            descripcion: "",
            productoId: '',
            plataformaId: '',
            status: true,
        },
        validationSchema: yup.object().shape({
            descripcion: yup.string().required("Campo obligatorio"),
            productoId: yup.number().required("Seleccion obligatoria"),
            plataformaId: yup.number().required("Seleccion obligatoria"),
        }),
        onSubmit: async (values) => {
            try {
                const response = await AxiosClient({
                    method: "POST",
                    url: "/item/",
                    data: JSON.stringify(values),
                    headers: { Authorization: `Bearer ${token}` }
                })
                if (!response.error) {
                    data();
                    handleClose();
                }
            } catch (error) {
                console.log(error);
                Alert.fire({
                    text: "No se pudo registar",
                    title:"Algo fallo",
                    icon:"x",
                    confirmButtonText: "Aceptar",
                    confirmButtonColor: "#3085d6"
                })
                handleClose();
            }finally{
                data();
            }
        }
    })
    const handleClose = () => {
        form.resetForm();
        onClose();
    }
    useEffect(() => {
        getAllPlatform();
        getAllProducts();

    }, []);
    const getAllPlatform = async () => {
        try {
            const response = await AxiosClient({
                url: "/plataforma/",
                method: "GET",
                headers: { Authorization: `Bearer ${token}` }
            })
            setPlatforms(response);
        } catch (error) {
            console.log(error);
        }
    }
    const getAllProducts = async () => {
        try {
            const response = await AxiosClient({
                url: "/producto/",
                method: "GET",
                headers: { Authorization: `Bearer ${token}` }
            })
            setProducts(response);
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <Modal backdrop="static" show={isOpen} onHide={handleClose} keyboard={false}>
            <Modal.Header>
                <Modal.Title>Registrar Item</Modal.Title>

            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={form.handleSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="productoId">Producto</Form.Label>
                        <Form.Control
                            as="select"
                            name="productoId"
                            value={form.values.productoId}
                            onChange={form.handleChange}
                        >
                            <option value="" disabled>
                                Seleccione una plataforma
                            </option>
                            {products.map((product) => (
                                <option key={product.id} value={product.id}>
                                    {product.titulo}
                                </option>
                            ))}
                        </Form.Control>
                        {form.errors.productoId && (
                            <span className="error-text">{form.errors.productoId}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="plataformaId">Plataforma</Form.Label>
                        <Form.Control
                            as="select"
                            name="plataformaId"
                            value={form.values.plataformaId}
                            onChange={form.handleChange}
                        >
                            <option value="" disabled>
                                Seleccione una plataforma
                            </option>
                            {platforms.map((platform) => (
                                <option key={platform.id} value={platform.id}>
                                    {platform.plataforma}
                                </option>
                            ))}
                        </Form.Control>
                        {form.errors.plataformaId && (
                            <span className="error-text">{form.errors.plataformaId}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="descripcion">Descripcion</Form.Label>
                        <Form.Control
                            name="descripcion"
                            placeholder=""
                            value={form.values.descripcion}
                            onChange={form.handleChange}
                            type="text"
                        />
                        {form.errors.descripcion && (
                            <span className="error-text">{form.errors.descripcion}</span>
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

export default ItemForm;
