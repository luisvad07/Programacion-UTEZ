import React, { useState } from 'react';
import {
    FaHouseUser,
    FaBars,
    FaUserAlt,
    FaTasks,
    FaUserFriends,
    FaCommentAlt,
    FaShoppingBag,
    FaThList
} from "react-icons/fa";
import FeatherIcon from "feather-icons-react";
import { NavLink,useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap';
/*import { useContext } from 'react';
import { AuthContext } from '../../modules/auth/AuthContext';*/


const AdminSidebar = ({ children }) => {

    const navigate = useNavigate();

    const logOut = () => {
        localStorage.clear();
        navigate('/login')
    }

    const [isOpen, setIsOpen] = useState(false);
    const toggle = () => setIsOpen(!isOpen);

    /*const handleLogout = () => {
        // Llamar a la API para cerrar la sesión
        console.log('Cerrar sesión');
    }*/

    /*const {dispatch} = useContext(AuthContext)
    const navigate = useNavigate()
    const handleLogout = () =>{
        dispatch({type: 'LOGOUT'})
        navigate('/auth',{replace: true})
    }*/

    const menuItem = [
        {
            path: "profile",
            name: "Perfil",
            icon: <FeatherIcon icon={"user"} />
        },
        {
            path: "admin",
            name: "Administradores",
            icon: <FeatherIcon icon={"users"} />
        },
        {
            path: "depRep",
            name: "Reportes",
            icon: <FeatherIcon icon={"file-text"} />
        },
        {
            path: "responsable",
            name: "Responsables",
            icon: <FeatherIcon icon={"zap"} />
        },
        {
            path: "consulta",
            name: "Consulta",
            icon: <FeatherIcon icon={"crosshair"} />
        }
    ]
    return (
        <>
            <div className='containerSide'>
                <div fluid="true" style={{ width: isOpen ? "200px" : "50px" }} className="sidebar">
                    <div className="top_section">
                        <h1 style={{ display: isOpen ? "block" : "none" }} className="logo">REDRE</h1>
                        <div style={{ marginLeft: isOpen ? "50px" : "0px" }} className="bars">
                            <FaBars onClick={toggle} />
                        </div>
                    </div>
                    {
                        menuItem.map((item, index) => (
                            <NavLink to={item.path} key={index} className="link" activeclassname="active">
                                <div className="icon">{item.icon}</div>
                                <div style={{ display: isOpen ? "block" : "none" }} className="link_text">{item.name}</div>
                            </NavLink>
                        ))
                    }
                <Button variant='blue' onClick={logOut}><FeatherIcon icon={"power"} style={{stroke: 'white'}} /></Button>

                </div>
                <main style={{ background: '#f2f2f2' }}> {children}</main>

                
            </div>
        </>

    );
};

export default AdminSidebar;