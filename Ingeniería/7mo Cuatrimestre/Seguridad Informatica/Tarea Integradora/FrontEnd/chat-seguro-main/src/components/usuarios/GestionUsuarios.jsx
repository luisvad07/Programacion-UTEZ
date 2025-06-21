import React, { useContext, useEffect, useState } from "react";
import {
  Button,
  Card,
  Col,
  Container,
  FormControl,
  InputGroup,
  Modal,
  Row,
} from "react-bootstrap";
import { AuthContext } from "../../services/auth/context/AuthContext";
import { getUsuarios, remove } from "../../services/usuarios/usuarioService";
import FormUser from "./FormUser";
import FormUserEdit from "./FormUserEdit";

export const GestionUsuarios = () => {
  const { userInfo, isLoading, logout } = useContext(AuthContext);
  const [loading, setLoading] = useState(false);
  const [usuarios, setUsuarios] = useState([]);
  const [refreshing, setRefreshing] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [renderComponent, setRenderComponent] = useState(null);
  const openClose = () => setShowModal((prevState) => !prevState);

  const fetchUsuarios = async () => {
    setLoading(true);
    const usuarios = await getUsuarios();
    setUsuarios(usuarios);
    setLoading(false);
  };

  useEffect(() => {
    fetchUsuarios();
  }, []);

  const [searchText, setSearchText] = useState("");
  const handleSearchTextChange = (text) => {
    setSearchText(text);
  };

  const filteredUsuarios = usuarios.filter((usuarios) =>
    usuarios.nombre.toLowerCase().includes(searchText.toLowerCase())
  );

  const onRefresh = React.useCallback(() => {
    setRefreshing(true);
    fetchUsuarios().then(() => setRefreshing(false));
  }, []);

  return (
    <Container style={styles.container}>
      <div style={styles.searchBarContainer}>
        <InputGroup>
          <FormControl
            placeholder="Buscar por nombre..."
            onChange={(e) => handleSearchTextChange(e.target.value)}
            value={searchText}
          />
          <Button
            variant="success"
            onClick={() => {
              setRenderComponent(
                <FormUser openClose={openClose} fetchData={fetchUsuarios} />
              );
              openClose();
            }}
          >
            Agregar
          </Button>
        </InputGroup>
      </div>

      {filteredUsuarios.length > 0 ? (
        <div className="d-flex flex-wrap flex-row justify-content-center mt-2">
          {filteredUsuarios.map(
            (item) => (
              (
                <Card style={styles.usuariosContainer} key={item.id_usuario} className="m-2">
                    <Card.Body>
                      <div style={styles.usuariosInfo}>
                        <div>
                          <p style={styles.usuariosName}>
                            {item.nombre} {item.primer_apellido}{" "}
                            {item.segundo_apellido}
                          </p>
                          {item.estatus === true ? (
                            <p style={styles.usuariosText}>Activo</p>
                          ) : (
                            <p style={styles.usuariosText}>Inactivo</p>
                          )}
                          {item.rol.nombre === "admin" ? (
                            <p style={styles.usuariosText}>Administrador</p>
                          ) : (
                            <p style={styles.usuariosText}>Cliente</p>
                          )}
                        </div>
                      </div>
                      <div style={styles.multipleButtons}>
                        <Button
                          className="m-2"
                          variant="primary"
                          onClick={() => {
                            setRenderComponent(
                              <FormUserEdit
                                usuario={item}
                                openClose={openClose}
                                fetchData={fetchUsuarios}
                              />
                            );
                            openClose();
                          }}
                        >
                          Editar
                        </Button>
                        <Button
                          variant={item.estatus === true ? "danger" : "success"}
                          onClick={() => {
                            setRenderComponent(
                              <div>
                                {item.estatus === true ? (
                                  <p>
                                    ¿Estás seguro que deseas desactivar este
                                    usuario?
                                  </p>
                                ) : (
                                  <p>
                                    ¿Estás seguro que deseas activar este
                                    usuario?
                                  </p>
                                )}
                                <div style={styles.multipleButtons}>
                                  <Button
                                    variant="secondary"
                                    onClick={() => openClose()}
                                  >
                                    Cancelar
                                  </Button>
                                  <Button
                                    className="m-2"
                                    variant={
                                      item.estatus === true
                                        ? "danger"
                                        : "success"
                                    }
                                    onClick={async () => {
                                      await remove(item.id_usuario);
                                      onRefresh();
                                      openClose();
                                    }}
                                  >
                                    {item.estatus === true
                                      ? "Desactivar"
                                      : "Activar"}
                                  </Button>
                                </div>
                              </div>
                            );
                            openClose();
                          }}
                        >
                          {item.estatus === true ? "Desactivar" : "Activar"}
                        </Button>
                      </div>
                    </Card.Body>
                  </Card>
              )
            )
          )}
        </div>
      ) : (
        <div style={styles.body}>
          <p style={styles.noUsuariosText}>No hay usuarios para mostrar</p>
        </div>
      )}
      <Modal show={showModal} onHide={openClose}>
        <Modal.Body>{renderComponent}</Modal.Body>
      </Modal>
    </Container>
  );
};

const styles = {
  container: {
    flex: 1,
  },
  searchBarContainer: {
    backgroundColor: "#fff",
    flexDirection: "column",
    justifyContent: "space-between",
    padding: 10,
    borderBottom: "1px solid #ccc",
  },
  usuariosContainer: {
    padding: 10,
    marginBottom: "3%",
    borderBottom: "1px solid #ccc",
    borderRadius: 20,
  },
  usuariosName: {
    fontSize: 16,
  },
  usuariosInfo: {
    flexDirection: "column",
    justifyContent: "space-between",
    alignItems: "flex-start",
  },
  usuariosText: {
    fontSize: 14,
    color: "#000",
  },
  multipleButtons: {
    flexDirection: "column",
    justifyContent: "space-evenly",
    marginTop: 15,
  },
  noUsuariosText: {
    textAlign: "center",
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 16,
    color: "#000",
  },
  body: {
    marginTop: 20,
    padding: 24,
  },
};

export default GestionUsuarios;
