import React, { useContext, useEffect, useState } from "react";
import AxiosClient from "../../shared/plugins/axios";
import { AuthContext } from "../auth/authContext";
import { Badge, Button, Col, Row } from "react-bootstrap";
import DataTable from "react-data-table-component";
import PlatformForm from "./components/PlatformForm";
import PlatformEditForm from "./components/PlatformEditForm";
import FeatherIcon from "feather-icons-react/build/FeatherIcon";
import Swal from 'sweetalert2';
import '../../utils/styles/AdminStyle.css'
import { useTheme } from "../../shared/components/ThemeContext";
const PlatformScreen = () => {
  const user = useContext(AuthContext);
  const { token } = user;
  const [platforms, setPlatforms] = useState([]);
  const [showModalPlatform, setShowModalPlatform] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [selectedPlatform, setSelectedPlatform] = useState(null);
  const { darkMode } = useTheme();


  const getAllPlatform = async () => {
    
    try {
      const response = await AxiosClient({
        url: "/plataforma/",
        method: "GET",
        headers: { Authorization: `Bearer ${token}` },
      });
      setPlatforms(response);
    } catch (error) {
      console.log(error);
    }
  };

  const changeStatus = async (id) => {
    try {
      await AxiosClient({
        url: `/plataforma/status/${id}`,
        method: "PUT",
        headers: { Authorization: `Bearer ${token}` },
      });
      Swal.fire({
        icon: 'success',
        title: 'Status cambiado',
        text: 'El status de la plataforma ha sido cambiado exitosamente.',
      });
    } catch (error) {
      console.log(error);
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Ha ocurrido un error al cambiar el status de la plataforma.',
      });
    } finally {
      getAllPlatform();
    }
  };

  useEffect(() => {
    document.title = "Plataformas";
    getAllPlatform();
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
              setIsEditing(true);
              setSelectedPlatform(row);
            }}
          >
            <FeatherIcon icon={"edit"} />
          </Button>
          {row.status ? (
            <Button
              variant="danger"
              size={15}
              onClick={() => changeStatus(row.id)}
            >
              <FeatherIcon icon={"trash"} />
            </Button>
          ) : (
            <Button
              variant="success"
              size={15}
              onClick={() => changeStatus(row.id)}
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
        className={`CrudContainer ${darkMode ? 'dark-mode': 'light-mode'}`}
        style={{
          justifyContent: "center",
          alignItems: "center",
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
                  <div style={{ width: "95%", paddingTop: 3 }}>Plataforma</div>

                  <div>
                    <FeatherIcon
                      className="DataIcon"
                      icon={"plus"}
                      onClick={() => setShowModalPlatform(true)}
                      style={{ height: 40, width: 40 }}
                    />
                  </div>
                </div>
              }
              columns={columns}
              data={platforms}
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

      <PlatformForm
        isOpen={showModalPlatform}
        data={getAllPlatform}
        token={token}
        onClose={() => setShowModalPlatform(false)}
      />

      {isEditing && (
        <PlatformEditForm
          isOpen={isEditing}
          data={getAllPlatform}
          token={token}
          onClose={() => setIsEditing(false)}
          existingPlatformData={selectedPlatform}
        />
      )}
    </>
  );
};

export default PlatformScreen;
