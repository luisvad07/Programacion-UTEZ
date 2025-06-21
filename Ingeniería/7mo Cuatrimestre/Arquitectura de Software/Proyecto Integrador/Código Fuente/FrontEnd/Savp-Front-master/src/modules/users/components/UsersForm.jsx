import React, { useEffect, useState } from 'react';
import AxiosClient from '../../../shared/plugins/axios';
import Alert from '../../../shared/plugins/alerts';
import { Button, Col, Form, Modal, Row } from 'react-bootstrap';
import { useFormik } from 'formik';
import * as yup from 'yup';

const UsersForm = ({isOpen, onClose, data, token}) => {
    const [roles, setRoles] = useState([]);
    
    const form = useFormik({
        initialValues:{
            name:"",
            birthday:"",
            address:"",
            username:"",
            password:"",
            roleId:'',
            status:1
        },
        validationSchema: yup.object().shape({
            name: yup.string().required("Campo requerido"),
            birthday: yup.string().required("Campo requerido"),
            address: yup.string().required("Campo requerido"),
            password: yup.string().required("Campo obligatorio"),
            roleId: yup.number().required("Campo requerido")
        }),
        onSubmit: async (values) => {
            try {
                const response = await AxiosClient({
                    method:"POST",
                    url:"/user/insert/",
                    data:JSON.stringify(values),
                    headers:{Authorization:`Bearer ${token}`}
                })
            } catch (error) {
                Alert.fire({
                    text:"El usuario no se pudo registrar, intentalo de nuevo",
                    title:"Fallido!",
                    icon:"x",
                    confirmButtonText:"Aceptar"
                })
            }finally{
                data();
                handleClose();

            }
        }
    })

    const getRoles = async () => {
        try {
            const response = await AxiosClient({
                url:"/rol/",
                method:"GET"
            })
            setRoles(response);
        } catch (error) {
            Alert.fire({
                title:"Error",
                text: "Se produjo un error, intente de nuevo",
                icon:"x",
                confirmButtonText:"Aceptar"
            })
        }
    }
    useEffect(()=> {
        getRoles();
    },[]);
    const handleClose = () => {
        form.resetForm();
        onClose();
    }
    
    return (
        <Modal show={isOpen} backdrop='static'>
            <Modal.Header>
                <Modal.Title>Agregar usuarios</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={form.handleSubmit}>
                <Form.Group className="mb-3">
                        <Form.Label htmlFor="name">Nombre</Form.Label>
                        <Form.Control
                            name="name"
                            placeholder=""
                            value={form.values.name}
                            onChange={form.handleChange}
                            type="text"
                        />
                        {form.errors.name && (
                            <span className="error-text">{form.errors.name}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="birthday">Cumpleaños</Form.Label>
                        <Form.Control
                            name="birthday"
                            placeholder=""
                            value={form.values.birthday}
                            onChange={form.handleChange}
                            type="text"
                        />
                        {form.errors.birthday && (
                            <span className="error-text">{form.errors.birthday}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="address">Direccion</Form.Label>
                        <Form.Control
                            name="address"
                            placeholder=""
                            value={form.values.address}
                            onChange={form.handleChange}
                            type="text"
                        />
                        {form.errors.address && (
                            <span className="error-text">{form.errors.address}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="username">Email/username</Form.Label>
                        <Form.Control
                            name="username"
                            placeholder=""
                            value={form.values.username}
                            onChange={form.handleChange}
                            type="text"
                        />
                        {form.errors.username && (
                            <span className="error-text">{form.errors.username}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="password">Contraseña</Form.Label>
                        <Form.Control
                            name="password"
                            placeholder=""
                            value={form.values.password}
                            onChange={form.handleChange}
                            type="text"
                        />
                        {form.errors.password && (
                            <span className="error-text">{form.errors.password}</span>
                        )}
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label htmlFor="roleId">Role</Form.Label>
                        <Form.Control
                            as="select"
                            name="roleId"
                            value={form.values.roleId}
                            onChange={form.handleChange}
                        >
                            <option value="" disabled>
                                Seleccione una plataforma
                            </option>
                            {roles.map((role) => (
                                <option key={role.id} value={role.id}>
                                    {role.rol}
                                </option>
                            ))}
                        </Form.Control>
                        {form.errors.roleId && (
                            <span className="error-text">{form.errors.roleId}</span>
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

export default UsersForm;
