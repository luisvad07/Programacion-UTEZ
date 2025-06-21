import React, { useContext } from "react";
import { Navbar, Nav, Button } from "react-bootstrap";
import FeatherIcon from "feather-icons-react/build/FeatherIcon";
import { AuthContext } from "../../services/auth/context/AuthContext";

const CustomNavbar = () => {
  const { logout } = useContext(AuthContext);

  const handleLogout = () => {
    logout();
    window.location.href = "/";
  };

  return (
    <Navbar bg="primary" variant="dark" expand="md" className="shadow">
      <Navbar.Toggle aria-controls="basic-navbar-nav" className="m-2" />
      <Navbar.Collapse id="basic-navbar-nav" className="m-2">
        <Nav className="mx-auto">
          <Nav.Link className="text-white m-1" href="/">
            <FeatherIcon icon="home" size={18} /> Inicio
          </Nav.Link>
        </Nav>
        <Button
          variant="outline-light"
          className="m-1"
          onClick={handleLogout}
        >
          <FeatherIcon icon="log-out" size={18} /> Cerrar sesión
        </Button>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default CustomNavbar;

