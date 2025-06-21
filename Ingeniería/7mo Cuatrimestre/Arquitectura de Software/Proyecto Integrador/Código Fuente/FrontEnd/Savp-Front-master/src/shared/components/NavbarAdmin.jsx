import React from 'react';
import '../../utils/styles/UserNavbar.css'
import { FaRegUserCircle, FaSearch } from 'react-icons/fa'
import { IoIosSettings } from 'react-icons/io'
import { BsStack } from 'react-icons/bs'
import FeatherIcon from 'feather-icons-react/build/FeatherIcon'
import { Button } from 'react-bootstrap'
import { useContext } from 'react'
import { AuthContext } from '../../modules/auth/authContext'
import { useNavigate } from 'react-router-dom'
import { useTheme } from '../../shared/components/ThemeContext';


const NavbarAdmin = () => {
    const { dispatch } = useContext(AuthContext);
    const navigation = useNavigate();
    const { darkMode, toggleDarkMode } = useTheme();

    const handleLogout = () => {
        dispatch({ type: "LOGOUT" });
        navigation("/auth", { replace: true });
        localStorage.removeItem("user");
    };

    return (
        <div className='UserNav'>
            <div className="UserIcon">
                <img src={require('../../utils/img/fgIcon.png')} alt="Icon" />
                ADMIN
            </div>
            <div className='UserData'>
                <IoIosSettings className='DataIcon' onClick={toggleDarkMode} style={{ height: 50, width: 32, marginBottom: 0 }}> {darkMode ? 'Modo Claro' : 'Modo Oscuro'}</IoIosSettings>

                <Button variant='none' href='/producto'>Productos</Button>
                <Button variant='none' href='/plataforma'>Plataforma</Button>
                <Button variant='none' href='/item'>Item</Button>
                <Button variant='none' href='/usuarios'>Usuarios</Button>
                <Button variant='none' href='/perfil'>Perfil</Button>
                <Button variant='none' href='/log'>Logs</Button>
                <Button variant='none' onClick={handleLogout}><FeatherIcon icon={'log-out'} /></Button>

            </div>
        </div>
    );
}

export default NavbarAdmin;
