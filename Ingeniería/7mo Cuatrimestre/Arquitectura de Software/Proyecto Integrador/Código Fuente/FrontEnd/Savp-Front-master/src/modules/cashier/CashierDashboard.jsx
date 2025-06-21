import React, { useContext, useEffect, useState } from 'react';
import AxiosClient from '../../shared/plugins/axios';
import DataTable from 'react-data-table-component';
import { AuthContext } from '../auth/authContext';
import { Badge, Button } from 'react-bootstrap';
import FeatherIcon from 'feather-icons-react/build/FeatherIcon';


const CashierDashboard = () => {
    const [items, setItems] = useState([]);
    const user = useContext(AuthContext);
    const { token } = user;

    useEffect(() => {
        getItemsWithStatusTwo();
    }, []);
    const columns = React.useMemo(() => [
        {
            name: "ID",
            selector: (row) => row.id,
        },
        {
            name: "Plataforma",
            selector: (row) => row.plataforma,
            sortable: true,
            fixed: true,
        },
        {
            name: "Titulo",
            selector: (row) => row.titulo,
            sortable: true,
            fixed: true,
        },
        {
            name: "Descripción",
            selector: (row) => row.descripcion,
            sortable: true,
            fixed: true,
        },
        {
            name: "Estado de renta",
            selector: (row) => {
                switch (row.estado) {
                    case 1:
                        return "Disponible";
                    case 2:
                        return "PROCESO";
                    case 3:
                        return "RENTADO";
                    default:
                        return "Desconocido";
                }
            },
            sortable: true,
            fixed: true,
        },
        {
            name: "ACCIONES",
            cell: (row) => (
                <>
                    {row.estado === 1 ? (
                        <Badge bg='success'>Activo</Badge>
                    ) : row.estado === 2 ? (
                        <Button
                            variant="primary"
                            size={15}
                            onClick={() => changeStatusEntragado(row.id)}
                        >
                            <FeatherIcon icon={"check"} />
                        </Button>
                    ) : row.estado === 3 ? (
                        <Button
                        variant="warning"
                        size={15}
                        onClick={() => changeStatusDevuelto(row.id)}
                    >
                        <FeatherIcon icon={"corner-down-left"} />
                    </Button>
                    ) : null}
                </>
            ),
        },
    ]);
    const getItemsWithStatusTwo = async () => {
        try {
            const response = await AxiosClient({
                url: "/item/",
                method: "GET",
                headers: { Authorization: `Bearer ${token}` },
            })
            setItems(response);
            console.log(response)
        } catch (error) {
            console.log(error);
        }
    }
    const changeStatusEntragado = async (id) => {
        try {
            const response = await AxiosClient({
                url: `/item/status_change/${id}`,
                method: "PUT"
            })

        } catch (error) {
            console.log(error)
        }finally{
            getItemsWithStatusTwo();
        }
    }
    const changeStatusDevuelto = async (id) => {
        try {
            const response = await AxiosClient({
                url: `/item/status_devuelto/${id}`,
                method: "PUT"
            })

        } catch (error) {
            console.log(error)
        }finally{
            getItemsWithStatusTwo();
        }
    }
    return (
        <>
            <div
                style={{
                    justifyContent: "ceneter",
                    alignItems: "center",
                    backgroundColor: "transparent",
                    height: "92vh",
                    padding: 20,
                    overflowY:"auto"
                }}
            >
                <div>
                    <div className="App">
                        <DataTable
                            title={
                                <div style={{ display: "flex", flexDirection: "row" }}>
                                    <div style={{ width: "95%", paddingTop: 3 }}>Carrito de entregas</div>


                                </div>
                            }
                            columns={columns}
                            data={items}
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
            </div>


        </>
    );
}

export default CashierDashboard;
