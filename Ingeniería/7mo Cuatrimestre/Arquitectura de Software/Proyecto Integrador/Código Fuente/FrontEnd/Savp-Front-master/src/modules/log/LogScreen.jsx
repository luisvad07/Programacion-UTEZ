import React, { useEffect, useState } from 'react';
import AxiosClient from '../../shared/plugins/axios';
import { useTheme } from "../../shared/components/ThemeContext";
import DataTable from 'react-data-table-component';
import FeatherIcon from 'feather-icons-react/build/FeatherIcon';

const LogScreen = () => {
    const [logs, setLogs] = useState([]);
    const { darkMode } = useTheme();
    const [selectedDate, setSelectedDate] = useState(null);
    const [usernameFilter, setUsernameFilter] = useState("");

    const columns = React.useMemo(() => [
        // ... Otras columnas
        {
            name: "USUARIO",
            selector: (row) => row.username,
            sortable: true,
            fixed: true,
            grow: 2,
            cell: (row) => (
                <div>
                    {row.username}
                </div>
            ),
        },
        {
            name: "TITULO",
            selector: (row) => row.titulo,
            sortable: true,
            fixed: true,
        },
        {
            name: "FECHA CREACION",
            selector: (row) => row.fecha,
            sortable: true,
            fixed: true,
            grow: 2,
            cell: (row) => (
                <div>
                    {row.fecha}
                </div>
            ),
        },
        {
            name: "FECHA DEVOLUCION",
            selector: (row) => row.entrega,
            sortable: true,
            fixed: true,
        },
    ]);

    const getLogs = async () => {
        try {
            const response = await AxiosClient({
                url: "/renta/log/",
                method: "GET",
            });
    
            // Aplicar filtros
            const filteredLogs = response.filter((log) => {
                const dateCondition = !selectedDate || log.fecha.includes(selectedDate);
                const usernameCondition = !usernameFilter || log.username.toLowerCase().includes(usernameFilter.toLowerCase());
    
                return dateCondition && usernameCondition;
            });
    
            setLogs(filteredLogs);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        getLogs();
    }, [selectedDate, usernameFilter]);

    return (
        <>
            <div
                className={`CrudContainer ${darkMode ? 'dark-mode' : 'light-mode'}`}
                style={{
                    justifyContent: "center",
                    alignItems: "center",
                    height: "92vh",
                    padding: 20,
                    overflowY: "auto"
                }}
            >
                <div>
                    <div className="App">
                        <div style={{ display: "flex", flexDirection: "row", marginBottom: "10px" }}>
                            <input
                                type="text"
                                value={usernameFilter}
                                onChange={(e) => setUsernameFilter(e.target.value)}
                                placeholder="Buscar usuario"
                                style={{ marginRight: "10px" }}
                            />
                            <input
                                type="date"
                                value={selectedDate}
                                onChange={(e) => setSelectedDate(e.target.value)}
                            />
                        </div>
                        <DataTable
                            title="Logs"
                            columns={columns}
                            data={logs}
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

export default LogScreen;
