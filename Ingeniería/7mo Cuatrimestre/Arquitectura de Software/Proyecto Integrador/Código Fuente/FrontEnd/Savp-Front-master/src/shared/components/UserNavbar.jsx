import '../../utils/styles/UserNavbar.css'
import { FaRegUserCircle, FaSearch } from 'react-icons/fa'
import { IoIosSettings } from 'react-icons/io'
import { BsStack } from 'react-icons/bs'
import FeatherIcon from 'feather-icons-react/build/FeatherIcon'
import { Button } from 'react-bootstrap'
import { useContext, useEffect, useState } from 'react'
import { AuthContext } from '../../modules/auth/authContext'
import { useNavigate } from 'react-router-dom'
import { Link } from 'react-router-dom';
import AxiosClient from '../plugins/axios'
import Alert from '../plugins/alerts'
import SearchModal from '../../modules/user/SearchModal'
import { useTheme } from '../../shared/components/ThemeContext';
import { useFontSize } from '../../shared/components/FontSizeContext';
import { FaFont } from "react-icons/fa";
import { FaShoppingCart } from "react-icons/fa";
import { ShoppingCartModal } from '../../modules/shoppingCart/ShoppingCartModal'



const UserNavbar = () => {
    const { dispatch } = useContext(AuthContext);
    const navigation = useNavigate();
    const [products, setProducts] = useState([]);
    const [showSearchModal, setShowSearchModal] = useState(false);
    const { darkMode, toggleDarkMode } = useTheme();
    const { fontSize, changeFontSize } = useFontSize();
    const [cartModalOpen, setCartModalOpen] = useState(false);
    const [items, setItems] = useState([]);

    const getItems = async () => {
        try {
          const response = await AxiosClient({
            url: `/item/`,
            method: 'GET',
          });
          setItems(response);
        } catch (error) {
          console.log(error);
        }
      };
      

    const openCartModal = () => {
        setCartModalOpen(true);
    };

    const closeCartModal = () => {
        setCartModalOpen(false);
    };

    useEffect(() => {
        getItems();
        getAllProducts();
    }, [])
    const getAllProducts = async () => {
        try {
            const response = await AxiosClient({
                url: "/producto/",
                method: "GET",
            });
            setProducts(response);
        } catch (err) {
            Alert.fire({
                title: "VERIFICAR DATOS",
                text: "USUARIO Y/O CONTRASEÑA INCORRECTOS",
                icon: "error",
                confirmButtonColor: "#3085d6",
                confirmButtonText: "Aceptar",
            });
        }
    }
    const handleLogout = () => {
        dispatch({ type: "LOGOUT" });
        navigation("/auth", { replace: true });
        localStorage.removeItem("user");
    };
    const toggleFontSize = () => {
        if (fontSize === '16px') {
            changeFontSize('20px');
        } else if (fontSize === '20px') {
            changeFontSize('24px');
        } else if (fontSize === '24px') {
            changeFontSize('16px');
        }
    };

    return (
        <div className='UserNav'>
            <Link to="/">
                <div className="UserIcon">
                    <img src={require('../../utils/img/fgIcon.png')} alt="Icon" />
                </div>
            </Link>
            <div className='UserData'>
                <BsStack className='DataIcon NavIcon' onClick={() => {
                }} style={{ height: 25, width: 25, marginBottom: 0 }} />
                <FaSearch className='DataIcon NavIcon' onClick={() => {
                    setShowSearchModal(true)
                }} style={{ height: 25, width: 25, marginBottom: 0 }} />
                <FaShoppingCart className='DataIcon' onClick={openCartModal} />

                <FaFont className='DataIcon NavIcon' onClick={toggleFontSize} />
                <IoIosSettings className='DataIcon' onClick={toggleDarkMode} style={{ height: 50, width: 32, marginBottom: 0 }}> {darkMode ? 'Modo Claro' : 'Modo Oscuro'}</IoIosSettings>
                <Link to='/perfil'>
                    <FaRegUserCircle
                        className='DataIcon'
                        style={{ height: 28, width: 25, marginBottom: 0, color: 'white' }}
                    />
                </Link>                <Button variant='none' onClick={handleLogout}><FeatherIcon icon={'log-out'} /></Button>
            </div>
            <SearchModal
                isOpen={showSearchModal}
                onClose={() => setShowSearchModal(false)}
                products={products} />
            {cartModalOpen && <ShoppingCartModal
                isOpen={cartModalOpen}
                onClose={closeCartModal}
                items={items}
            />}

        </div>
    );
}

export default UserNavbar;
