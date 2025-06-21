import React, { useEffect, useState } from "react";
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  Chip,
  Spinner,
  Pagination,
} from "@nextui-org/react";
import DeleteIcon from "@mui/icons-material/Delete";
import { Button, IconButton } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import "../../css/admin/MoviesTable.css";
import AddIcon from "@mui/icons-material/Add";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import endpoints from "../../utils/endpoints";
import api from "../../config/axios/client-gateway";
import DoneIcon from "@mui/icons-material/Done";
import columsTable from "../../utils/colums";

export const MoviesTable = () => {
  const navigate = useNavigate();
  const [movies, setMovies] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [page, setPage] = React.useState(1);
  const rowsPerPage = 7;
  const pages = Math.ceil(movies.length / rowsPerPage);
  const columns = columsTable;

  const items = React.useMemo(() => {
    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;

    return movies.slice(start, end);
  }, [page, movies]);

  useEffect(() => {
    getMovies();
  }, []);

  const getMovies = async () => {
    setIsLoading(true);
    try {
      const response = await api.doGet(endpoints.GetAllMoviesFunction);
      if (response && response.status === 200) {
        setMovies(response.data.Peliculas);
      }
    } catch (error) {
    } finally {
      setIsLoading(false);
    }
  };

  const onAddMovie = () => {
    navigate("/admin/create");
  };

  const onEditMovie = (id) => {
    navigate("/admin/edit");
    localStorage.setItem("idMovie", id);
  };

  const onDeleteMovie = (id) => {
    Swal.fire({
      title: "¿Estás seguro?",
      text: "vas a desactivar la película",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#72557C",
      cancelButtonColor: "#3A2A42",
      confirmButtonText: "Sí, eliminar",
      cancelButtonText: "Cancelar",
    }).then((result) => {
      if (result.isConfirmed) {
        api
          .doPatch(endpoints.ChangeStatusMovieFunction + id)
          .then((response) => {
            if (response && response.status === 200) {
              Swal.fire(
                "Desactivada",
                "La película ha sido desactivada",
                "success"
              );
              getMovies();
            } else {
              Swal.fire("Error", "No se pudo desactivar la película", "error");
            }
          })
          .catch((error) => {
            console.error("Error desactivando la película:", error);
            Swal.fire(
              "Error",
              "Ocurrió un error al desactivar la película",
              "error"
            );
          });
      }
    });
  };

  const onActivateMovie = (id) => {
    Swal.fire({
      title: "¿Estás seguro?",
      text: "Vas a activar la película",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#72557C",
      cancelButtonColor: "#3A2A42",
      confirmButtonText: "Sí, activar",
      cancelButtonText: "Cancelar",
    }).then((result) => {
      if (result.isConfirmed) {
        api
          .doPatch(endpoints.ChangeStatusMovieFunction + id)
          .then((response) => {
            if (response && response.status === 200) {
              Swal.fire("Activada", "La película ha sido activada", "success");
              getMovies();
            } else {
              Swal.fire("Error", "No se pudo activar la película", "error");
            }
          })
          .catch((error) => {
            console.error("Error activando la película:", error);
            Swal.fire(
              "Error",
              "Ocurrió un error al activar la película",
              "error"
            );
          });
      }
    });
  };

  const getKeyValue = (item, key) => {
    if (key === "image") {
      return <img src={item[key]} alt={item.title} width="50" height="75" />;
    }
    if (key === "status") {
      return (
        <Chip variant="flat" color={item[key] === 1 ? "success" : "danger"}>
          {item[key] === 1 ? "Activa" : "Inactiva"}
        </Chip>
      );
    }
    if (key === "actions") {
      return (
        <>
          {item.status === 1 ? (
            <IconButton aria-label="edit" onClick={() => onEditMovie(item.id)}>
              <EditIcon className="edit-icon" />
            </IconButton>
          ) : (
            <IconButton
              aria-label="edit"
              onClick={() => onEditMovie(item.id)}
              disabled
            >
              <EditIcon className="edit-icon" />
            </IconButton>
          )}
          {item.status === 1 ? (
            <IconButton
              aria-label="delete"
              onClick={() => onDeleteMovie(item.id)}
            >
              <DeleteIcon className="delete-icon" color="error" />
            </IconButton>
          ) : (
            <IconButton
              aria-label="activate"
              onClick={() => onActivateMovie(item.id)}
            >
              <DoneIcon className="add-icon" />
            </IconButton>
          )}
        </>
      );
    }
    if (key === "title") {
      return <p className="title-movie">{item[key]}</p>;
    }

    return item[key];
  };

  return (
    <>
      <h1 className="title">Lista de películas</h1>
      <div className="header">
        <Button variant="contained" endIcon={<AddIcon />} onClick={onAddMovie}>
          Agregar película
        </Button>
      </div>

      <Table
        aria-label="Tabla de películas"
        bottomContent={
          <div className="flex w-full justify-center">
            <Pagination
              isCompact
              showControls
              showShadow
              color="secondary"
              page={page}
              total={pages}
              onChange={(page) => setPage(page)}
            />
          </div>
        }
      >
        <TableHeader columns={columns}>
          {(column) => (
            <TableColumn key={column.key}>{column.label}</TableColumn>
          )}
        </TableHeader>

        <TableBody
          items={items}
          isLoading={isLoading}
          loadingContent={
            <Spinner color="secondary" label="Cargando películas..." />
          }
        >
          {(item) => (
            <TableRow key={item.id}>
              {(columnKey) => (
                <TableCell>{getKeyValue(item, columnKey)}</TableCell>
              )}
            </TableRow>
          )}
        </TableBody>
      </Table>
    </>
  );
};
