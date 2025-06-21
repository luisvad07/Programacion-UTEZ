import FeatherIcon from 'feather-icons-react/build/FeatherIcon';
import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import {
    FaHouseUser,
    FaBars,
    FaUserAlt,
    FaTasks,
    FaUserTie,
    FaCommentAlt,
    FaShoppingBag,
    FaThList
}from "react-icons/fa";
import { NavLink, useNavigate } from 'react-router-dom';


const RespSidebar = ({children}) => {

    const navigate = useNavigate();

    const logOut = () => {
        localStorage.clear();
        navigate('/login')
    }
    const[isOpen ,setIsOpen] = useState(false);
    const toggle = () => setIsOpen (!isOpen);
    const menuItem=[
        {
            path:"profileResp",
            name:"Perfil",
            icon:<FaUserAlt/>
        },
        {
            path:"asesor",
            name:"Asesores",
            icon:<FaUserTie/>
        },
        {
            path:"depRep",
            name:"Reportes",
            icon:<FaTasks/>
        },
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

export default RespSidebar;