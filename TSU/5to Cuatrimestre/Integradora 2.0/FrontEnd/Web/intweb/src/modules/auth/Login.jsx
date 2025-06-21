import React, { useEffect, useState } from "react";
import {
    Card,
    Container,
    Form,
    Row,
    Col,
    Button, InputGroup
} from "react-bootstrap";
import FeatherIcon from "feather-icons-react";
import fondo from "../../assets/fondo.png";

import Alert from '../../shared/plugins/alert';
import { AuthContext } from './AuthContext'

import { Navigate, useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import * as yup from 'yup';
import axios from "axios";
import { FaUser, FaLock, FaEyeSlash, FaEye } from "react-icons/fa";
import { localhost } from '../../../src/shared/plugins/axios'
import { show_alerta } from '../../shared/components/functions';


const Login = () => {

    const baseUrl = `http://${localhost}:8080/redre/`

    const navigate = useNavigate();

    const [estudiante, setEstudiante] = useState([]);
    const [asesor, setAsesor] = useState([]);
    const [responsable, setResponsable] = useState([]);
    const [departamento, setDepartamento] = useState([]);
    const [userData, setUserData] = useState({});

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('')

    const [show, setShow] = useState(false);

    let found = false;

    useEffect(() => {
        validateSession();
    }, []);

    const validateSession = () => {
        if (localStorage.getItem('sesionId')) {
            switch (localStorage.getItem('rol')) {
                case 'estudiante':
                    navigate('/studentDashboard')
                    break;
                case 'departamento':
                    navigate('/adminDashboard')
                    break;
                case 'responsable':
                    navigate('/responsableDashboard')
                    break;
                case 'asesor':
                    navigate('/asesorDashboard')
                    break;
                default:
                    break;
            }
        }
    }


    const buscarPersona = async () => {
        if (validate()) {
            let usuario = null;
            let rol = '';

            fetch(`${baseUrl}estudiante/`).then((resp) => {
                return resp.json();
            }).then((resp) => {

                for (let index = 0; index < resp.obj.length; index++) {
                    const element = resp.obj[index];
                    if (element.correo === email && element.password === password) {
                        usuario = element;
                        rol = 'estudiante'
                        found = true;
                    }
                }

                fetch(`${baseUrl}asesor/`).then((resp) => {
                    return resp.json();
                }).then((resp) => {
                    for (let index = 0; index < resp.obj.length; index++) {
                        const element = resp.obj[index];
                        if (element.correo === email && element.password === password) {
                            usuario = element;
                            rol = 'asesor'
                            found = true;
                        }
                    }

                    fetch(`${baseUrl}responsable/`).then((resp) => {
                        return resp.json();
                    }).then((resp) => {
                        for (let index = 0; index < resp.obj.length; index++) {
                            const element = resp.obj[index];
                            if (element.correo === email && element.password === password) {
                                usuario = element;
                                rol = 'responsable'
                                found = true;
                            }
                        }
                        fetch(`${baseUrl}departamento/`).then((resp) => {
                            return resp.json();
                        }).then((resp) => {
                            for (let index = 0; index < resp.obj.length; index++) {
                                const element = resp.obj[index];
                                if (element.correo === email && element.password === password) {
                                    usuario = element;
                                    rol = 'departamento'
                                    found = true;
                                }
                            }
                            if (usuario === null) {
                                show_alerta("Usuario no encontrado\nRevise los datos insertados", "warning")
                            } else {
                                localStorage.setItem("rol", rol)
                                localStorage.setItem("correo", usuario.correo)
                                localStorage.setItem("nombre", usuario.nombre)
                                localStorage.setItem("apellidos", usuario.apellidos)
                                localStorage.setItem("divisionAcademica", usuario.divisionAcademica)
                                localStorage.setItem("carrera", usuario.carrera)
                                localStorage.setItem("correo", usuario.correo)
                                localStorage.setItem("sesionId", usuario.id)
                                localStorage.setItem("matricula", usuario.matricula)
                                localStorage.setItem("grado", usuario.grado)
                                localStorage.setItem("grupo", usuario.grupo)
                                localStorage.setItem("reportStatus", usuario.reportStatus)

                                let dato = localStorage.getItem("sesionUser")

                                switch (rol) {
                                    case 'estudiante':
                                        navigate('/studentDashboard')
                                        break;
                                    case 'departamento':
                                        navigate('/adminDashboard')
                                        break;
                                    case 'responsable':
                                        navigate('/responsableDashboard')
                                        break;
                                    case 'asesor':
                                        navigate('/asesorDashboard')
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                    })

                })

            })
        } else {
            // Modal de error
            show_alerta("Llene todos los datos", "warning")
        }
    }

    /*const navigation = useNavigate();
    const { user, dispatch } = useContext(AuthContext);
    const formik = useFormik({
        initialValues: {
            username: '',
            password: '',
        },
        validationSchema: yup.object().shape({
            username: yup.string().required('Campo obligatorio'),
            password: yup.string().required('Campo obligatorio'),
        }),
        onSubmit: async (values) => {
            try {
                const response = await axios({
                    url: '/auth/login',
                    method: 'POST',
                    data: JSON.stringify(values),
                });
                if (!response.error) {
                    const action = {
                        type: 'LOGIN',
                        payload: response.data,
                    };
                    dispatch(action);
                    navigation('/products', { replace: true });
                } else {
                    throw Error()
                }
            } catch (err) {
                Alert.fire({
                    title: 'Verificar datos',
                    text: 'Usuario y/o contraseña incorrectos',
                    icon: 'error',
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'Aceptar',
                });
            }
        },
    });*/

    const [showPassword, setShowPassword] = useState(false);

    const togglePassword = () => {
        setShowPassword(!showPassword);
    };

    const validate = () => {
        let result = true;
        if (email === ' ' || email === null || email === '') {
            result = false;
        }
        if (password === ' ' || password === null || password === '') {
            result = false;
        }
        return result;
    }

    return (
        <div style={{ backgroundImage: `url(${fondo})` }}>
            <section className="vh-100">
                <Container className="py-5 h-100">
                    <Row
                        className="d-flex justify-content-start align-items-center h-100"
                    >
                        <Col className="col-xl-10">
                            <Row className="g-0">
                                <Col className="col-12 col-md-8 col-lg-6 col-xl-5">
                                    <h1 style={{ color: 'white' }} className="title d-flex justify-content-center">
                                        REDRE
                                    </h1>
                                    <Card style={{ background: '#ccc' }} className="shadow-2-strong">
                                        <Card.Body className="p-5 text-center cardInitial">
                                            <Form>
                                                <InputGroup className="mb-3" >
                                                    <InputGroup.Text id="basic-addon1" style={{ backgroundColor: "white", borderRight: "none" }}>
                                                        <FaUser color="gray" />
                                                    </InputGroup.Text>
                                                    <Form.Control
                                                        type="text"
                                                        onChange={(e) => setEmail(e.target.value)}
                                                        placeholder="Correo institucional"
                                                        aria-label="Username"
                                                        aria-describedby="basic-addon1"
                                                        id="email"
                                                    />
                                                </InputGroup>
                                                <InputGroup className="mb-3">
                                                    <InputGroup.Text id="basic-addon1" style={{ backgroundColor: "white", borderRight: "none" }}>
                                                        <FaLock color="gray" />
                                                    </InputGroup.Text>
                                                    <Form.Control
                                                        type={showPassword ? 'text' : 'password'}
                                                        placeholder="*********"
                                                        id="password"
                                                        autoComplete="off"
                                                        name="password"
                                                        value={password}
                                                        onChange={(e) => setPassword(e.target.value)}
                                                    />
                                                    <Button className="btn border border-1" style={{ backgroundColor: "white", borderLeft: "none" }} onClick={togglePassword}>
                                                        <FeatherIcon style={{ stroke: 'gray' }} icon={showPassword ? 'eye-off' : 'eye'} />
                                                    </Button>
                                                </InputGroup>
                                                { /*<Form.Group className="form-outline mb-4">
                                                    <div className="input-group mb-3">
                                                        <span
                                                            className="input-group-text bg-white"
                                                            id="basic-addon1"
                                                        >
                                                            <FeatherIcon icon={"user"} />
                                                        </span>
                                                        <Form.Control
                                                            placeholder="Correo institucional"
                                                            id="username"
                                                            autoComplete="off"
                                                            name="username"
                                                            //value={"siu"}
                                                            onChange={() => console.log("HOLA XD")}
                                                        />
                                                    </div>
    </Form.Group>
                                                <Form.Group className="form-outline mb-4">
                                                    <div className="input-group mb-3">
                                                        <span
                                                            className="input-group-text bg-white"
                                                            id="basic-addon1"
                                                        >
                                                            <FeatherIcon icon={"user"} />
                                                        </span>
                                                        <Form.Control
                                                            type={showPassword ? 'text' : 'password'}
                                                            placeholder="*********"
                                                            id="password"
                                                            autoComplete="off"
                                                            name="password"
                                                            value={password}
                                                            onChange={(e) => setPassword(e.target.value)}
                                                        />
                                                        <Button variant="outline-secondary" onClick={togglePassword}>
                                                            <i className={`bi bi-eye${showPassword ? '-slash' : ''}`}></i>
                                                        </Button>
                                                    </div>
                                                </Form.Group>*/}
                                                <Form.Group className="form-outline mb-4">
                                                    <div className="text-center pt-1 pb-1">
                                                        <Button
                                                            variant="secondary"
                                                            className=" gradient-custom-2"
                                                            style={{ background: '#3D9982' }}
                                                            type="button"
                                                            onClick={buscarPersona}
                                                        //disabled={!(formik.isValid && formik.dirty)}
                                                        >
                                                            Iniciar sesión
                                                        </Button>
                                                    </div>
                                                </Form.Group>
                                            </Form>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                </Container>
            </section>
        </div>
    );
};

export default Login;