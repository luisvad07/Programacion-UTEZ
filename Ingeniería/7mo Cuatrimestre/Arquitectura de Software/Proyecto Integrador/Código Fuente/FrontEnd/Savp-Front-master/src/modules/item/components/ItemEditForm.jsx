import { useFormik } from 'formik';
import React, { useEffect, useState } from 'react';
import * as yup from "yup";
import AxiosClient from '../../../shared/plugins/axios';
import { Button, Col, Form, Modal, Row } from 'react-bootstrap';
import Swal from 'sweetalert2';
import Alert from '../../../shared/plugins/alerts';


const ItemEditForm = ({ isOpen, data, onClose, token, itemId }) => {
    const [platforms, setPlatforms] = useState([]);
    const [products, setProducts] = useState([]);
    const [existingItemId, setExistingItemId] = useState(itemId !== null ? itemId : null);



    const form = useFormik({
        initialValues: {
            id: existingItemId,
            descripcion: "",
            productoId: '',
            plataformaId: '',
        },
        validationSchema: yup.object().shape({
            descripcion: yup.string().required("Campo obligatorio"),
            productoId: yup.number().required("Seleccion obligatoria"),
            plataformaId: yup.number().required("Seleccion obligatoria"),
        }),
        onSubmit: async (values) => {
            try {
              console.log("existingItemId:", existingItemId); // Verifica el valor aquí
              if (existingItemId !== null) { // Verifica si existingItemId no es null antes de realizar la solicitud
                const response = await AxiosClient({
                  method: "PUT",
                  url: `/item/${existingItemId}`,
                  data: JSON.stringify(values),
                  headers: { Authorization: `Bearer ${token}` },
                });
          
                if (!response.error) {
                  data();
                  handleClose();
                  Alert.fire({
                    icon: 'success',
                    title: 'Éxito',
                    text: 'El item se ha actualizado correctamente.'
                  });
                } else {
                  Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: response.data.message
                  });
                }
              } else {
                console.error("existingItemId es null o no está definido.");
              }
            } catch (error) {
              console.log(error);
              Alert.fire({
                icon: 'error',
                title: 'Error',
                text: 'Ha ocurrido un error al actualizar el item.'
              });
            }finally{
                handleClose();
            }
          },
          
                  
    })
const handleClose = () => {
        form.resetForm();
        onClose();
    }
    useEffect(() => {
        if (existingItemId) {
          AxiosClient({
            url: `/item/${existingItemId}`,
            method: "GET",
            headers: { Authorization: `Bearer ${token}` },
          })
            .then((response) => {
            console.table(response)
              form.setValues({
                id: response[0].id,
                descripcion: response[0].descripcion,
                productoId: response[0].producto_fk,
                plataformaId: response[0].plataforma_fk,
              });
            })
            .catch((error) => {
              console.log(error);
            });
        }
    
        getAllPlatform();
        getAllProducts();
      }, [existingItemId]);
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
                <Modal.Title>Editar Item</Modal.Title>

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

export default ItemEditForm;
