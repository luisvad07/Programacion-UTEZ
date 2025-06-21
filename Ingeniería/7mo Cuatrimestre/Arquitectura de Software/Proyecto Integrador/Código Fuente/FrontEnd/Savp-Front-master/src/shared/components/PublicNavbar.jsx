import React from "react";
import { Navbar, Nav, Container, Button } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";

export const PublicNavbar = () => {
  const navigation = useNavigate();
  const handleLogin = () => {
    navigation("/auth");
  };
  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand href="#home">React-Bootstrap</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Link className="nav-link" to={"/products"}>
              PRODUCTOS
            </Link>
            <Link className="nav-link" to={"/contact"}>
              CONTACTO
            </Link>
          </Nav>
          <Button onClick={handleLogin} variant="outline-success">
            INICIAR SESION
          </Button>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};
