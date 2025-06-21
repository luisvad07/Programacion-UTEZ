import React, { useContext, useEffect, useState } from 'react';
import AxiosClient from '../../shared/plugins/axios';
import { AuthContext } from '../auth/authContext';
import Alert from '../../shared/plugins/alerts';
import { Badge, Button } from 'react-bootstrap';
import FeatherIcon from 'feather-icons-react/build/FeatherIcon';
import DataTable from 'react-data-table-component';
import UsersForm from './components/UsersForm';
import EditUserForm from './components/EditUserForm';
import { useTheme } from "../../shared/components/ThemeContext";

const UsersScreen = () => {
    const { user } = useContext(AuthContext);
    const { token } = user;
    const [users, setUsers] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [showModalEdit, setShowModalEdit] = useState(false);
    const [selectedPerson, setSelectedPerson] = useState({});
    const { darkMode } = useTheme();

    const getAllUsers = async () => {
        try {
            const response = await AxiosClient({
                url: "/personal/",
                method: "GET",
                headers: { Authorization: `Beaer ${token}` }
            })
            console.log(response)
            setUsers(response);
        } catch (error) {
            Alert.fire({
                title: "ERROR",
                icon: "x",
                text: "Error al obtener todos los usuarios",
                confirmButtonText: "Aceptar"
            })
        }
    }


    useEffect(() => {
        getAllUsers();
    }, []);
    const columns = React.useMemo(() => [
        {
            name: "ID",
            selector: (row) => row.id,
        },
        {
            name: "Nombre",
            selector: (row) => row.name,
            sortable: true,
            fixed: true,
        },
        {
            name: "Cumpleaños",
            selector: (row) => row.birthday,
            sortable: true,
            fixed: true,
        },
        {
            name: "Direccion",
            selector: (row) => row.address,
            sortable: true,
            fixed: true,
        },
        {
            name: "Username/Email",
            selector: (row) => row.username,
            sortable: true,
            fixed: true,
        },
        {
            name: "Estado de renta",
            selector: (row) => {
                switch (row.rol_fk) {
                    case 1:
                        return "ADMINISTRADOR";
                    case 2:
                        return "CAJERO";
                    case 3:
                        return "USUARIO";
                    default:
                        return "Desconocido";
                }
            },
            sortable: true,
            fixed: true,
        },
        {
            name: "Estatus",
            cell: (row) =>
                row.status ? (
                    <Badge bg="success"> ACTIVO </Badge>
                ) : (
                    <Badge bg="danger">INACTIVO</Badge>
                ),
        },
        {
            name: "ACCIONES",
            cell: (row) => (
                <>
                    <Button
                        variant="warning"
                        type="btn btn-outline-warning btn-circle me-1"
                        size={16}
                        onClick={() => {
                            setSelectedPerson(row);
                            setShowModalEdit(true);
                        }}
                    >
                        <FeatherIcon icon={"edit"} />
                    </Button>

                    {row.status ? (
                        <Button
                            variant="danger"
                            size={15}
                        //onClick={() => changeStatus(row.id)}
                        >
                            <FeatherIcon icon={"trash"} />
                        </Button>
                    ) : (
                        <Button
                            variant="success"
                            size={15}
                        //onClick={() => changeStatus(row.id)}
                        >
                            <FeatherIcon icon={"save"} />
                        </Button>
                    )}
                </>
            ),
        },
    ]);
    return (
        <>
            <div
                className={`CrudContainer ${darkMode ? 'dark-mode' : 'light-mode'}`}
                style={{
                    justifyContent: "ceneter",
                    alignItems: "center",
                    height: "92vh",
                    padding: 20,
                }}
            >
                <div>
                    <div className="App">
                        <DataTable
                            title={
                                <div style={{ display: "flex", flexDirection: "row" }}>
                                    <div style={{ width: "95%", paddingTop: 3 }}>Usuarios</div>
                                    <div>
                                        <FeatherIcon
                                            className="DataIcon"
                                            icon={"plus"}
                                            onClick={() => {
                                                setShowModal(true);
                                            }}
                                            style={{ height: 40, width: 40 }}
                                        />
                                    </div>
                                </div>
                            }
                            columns={columns}
                            data={users}
                            pagination
                            highlightOnHover
                            paginationPerPage={8}
                            paginationComponentOptions={{
                                rowsPerPageText: "",
                                noRowsPerPage: true,
                            }}
                        />
                    </div>
                </div>
                <UsersForm
                    isOpen={showModal}
                    onClose={() => setShowModal(false)}
                    data={getAllUsers}
                    token={token} />
                <EditUserForm
                    isOpen={showModalEdit}
                    onClose={() => setShowModalEdit(false)}
                    data={getAllUsers}
                    person={selectedPerson}
                    token={token} />
            </div>
        </>
    );
}

export default UsersScreen;
