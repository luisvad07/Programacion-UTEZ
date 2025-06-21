import FeatherIcon from 'feather-icons-react'
import React from 'react'
import { Nav, Navbar, Container } from 'react-bootstrap'
import { Link } from 'react-router-dom'

export const PublicNavbar = () => {
    return (
        <Navbar bg="dark" expand="lg">
            <Container fluid>
                <Navbar.Brand className='text-white' href="#"><FeatherIcon icon="shopping-bag" />&nbsp;<b>MARKET</b></Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll" />
                <Navbar.Collapse id="navbarScroll">
                    <Nav
                        className="me-auto my-2 my-lg-0"
                        style={{ maxHeight: '100px' }}
                        navbarScroll
                    >
                        <Nav.Link href="#action1" className='text-white'>Productos</Nav.Link>
                    </Nav>
                    <Link to={'auth'} className='btn btn-outline-primary bg-light'>
                        INICIAR SESIÓN
                    </Link>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}
