import { useFormik } from 'formik';
import React, { useEffect, useState } from 'react';
import * as yup from 'yup';
import Alert, { confirmMsj, confirmTitle, errorMsj, errorTitle, succesMsj, successTitle } from '../../../shared/plugins/alerts';
import { Button, Col, Form, Modal, Row } from 'react-bootstrap';
import AxiosClient from '../../../shared/plugins/axios';

const EditUserForm = ({isOpen, onClose, person, data, token}) => {
    const [roles, setRoles] = useState([]);
    const form = useFormik({
        initialValues:{
            id:"",
            name:"",
            birthday:"",
            address:"",
            roleId:'',
            username:"",
            status:1,
            user_id:""
        },
        validationSchema: yup.object().shape({
            name: yup.string().required("Campo requerido"),
            birthday: yup.string().required("Campo requerido"),
            address: yup.string().required("Campo requerido"),
            roleId: yup.number().required("Campo requerido")
        }),
        onSubmit: async (values) => {
            return Alert.fire({
              title: confirmTitle,
              text: confirmMsj,
              icon: "warning",
              confirmButtonColor: "#009574",
              confirmButtonText: "Aceptar",
              cancelButtonColor: "#DD6B55",
              cancelButtonText: "Cancelar",
              reverseButtons: true,
              backdrop: true,
              showCancelButton: true,
              showLoaderOnConfirm: true,
              allowOutsideClick: () => !Alert.isLoading,
              preConfirm: async () => {
                try {
                    console.log(values);
                  const response = await AxiosClient({
                    method: "PUT",
                    url: "/personal/",
                    data: JSON.stringify(values),
                    headers: {
                      Authorization: `Bearer ${token}`,
                    },
                  });
                  if (!response.error) {
                    Alert.fire({
                      title: successTitle,
                      text: succesMsj,
                      icon: "success",
                      confirmButtonColor: "#3085d6",
                      confirmButtonText: "Aceptar",
                    }).then((result) => {
                      if (result.isConfirmed) handleClose();
                    });
                  }
                  return response;
                } catch (error) {
                  Alert.fire({
                    title: errorTitle,
                    text: errorMsj,
                    icon: "error",
                    confirmButtonColor: "#3085d6",
                    confirmButtonText: "Aceptar",
                  }).then((result) => {
                    if (result.isConfirmed) handleClose();
                  });
                } finally {
                  handleClose();
                  data();
                }
              },
            });
          },
    });
    React.useMemo(() => {
        const {id, name, address, username, birthday, status, rol_fk , user_fk} = person;
        form.values.id = id;
        form.values.name = name;
        form.values.address = address;
        form.values.username = username;
        form.values.birthday = birthday;
        form.values.status = status;
        form.values.roleId = rol_fk;
        form.values.user_id = user_fk
    }, [person]);
    const handleClose = () => {
        form.resetForm();
        onClose();
    }
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
    useEffect(() => {
        getRoles();
    }, []);
    return (
        <Modal show={isOpen} backdrop='static'>
            <Modal.Header>
                <Modal.Title>Editar usuarios</Modal.Title>
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
                        <Form.Label htmlFor="roleId">Role</Form.Label>
                        <Form.Control
                            as="select"
                            name="roleId"
                            value={form.values.roleId}
                            onChange={form.handleChange}
                        >
                            <option value="" disabled>
                                Seleccione el rol
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

export default EditUserForm;
