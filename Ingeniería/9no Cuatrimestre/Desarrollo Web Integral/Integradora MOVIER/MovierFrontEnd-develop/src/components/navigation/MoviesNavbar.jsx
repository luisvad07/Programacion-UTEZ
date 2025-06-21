import React, { useState } from "react";
import { Link, Outlet } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Button from "react-bootstrap/Button"; // Importa el componente Button
import SearchIcon from "@mui/icons-material/Search";
import "../../css/navigation/MovierNavbar.css";
import PersonIcon from "@mui/icons-material/Person";
import { IconButton } from "@mui/material";
import MovieFilterIcon from "@mui/icons-material/MovieFilter";
import LogoutIcon from "@mui/icons-material/Logout";
import SearchModal from "./SearchModal"; // Importa el modal

export default function MoviesNavbar() {
  const [showModal, setShowModal] = useState(false);

  const rol = localStorage.getItem("role");

  const handleSearchClick = () => {
    setShowModal(true);
  };

  const handleClose = () => {
    setShowModal(false);
  };

  return (
    <div>
      <Navbar className="nav" bg="light" expand="lg">
        <Container fluid className="nav">
          <Navbar href="/movies" className="brand">
            <a className="logo" href="/home">
              <p>MOVIER</p>
            </a>
          </Navbar>

          {rol !== "Administrador" && (
            <Button
              variant="light"
              onClick={handleSearchClick}
              style={{
                color: "purple",
                backgroundColor: "white",
                width: "300px",
              }}
            >
              Busca películas
            </Button>
          )}
          <Navbar className="user">
            {rol !== "Administrador" && (
              <IconButton aria-label="delete">
                <Link to="/movies/watched">
                  <MovieFilterIcon fontSize="large" />
                </Link>
              </IconButton>
            )}

            <IconButton aria-label="delete">
              <Link to="/logout">
                <LogoutIcon fontSize="large" />
              </Link>
            </IconButton>
          </Navbar>
        </Container>
      </Navbar>
      <SearchModal show={showModal} handleClose={handleClose} />
      <Outlet />
    </div>
  );
}
