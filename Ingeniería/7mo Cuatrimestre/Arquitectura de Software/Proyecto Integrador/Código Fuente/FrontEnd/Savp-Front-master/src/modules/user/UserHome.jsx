import React, { useContext, useEffect, useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import '../../utils/styles/UserStyles.css';
import AxiosClient from "../../shared/plugins/axios";
import Alert from "../../shared/plugins/alerts";
import { AuthContext } from '../auth/authContext';
import { URL } from '../../utils/constans';
import { useTheme } from '../../shared/components/ThemeContext';
import { useFontSize } from '../../shared/components/FontSizeContext';
import { useCarrito } from '../shoppingCart/CarritoContext'; // Ajusta la ruta según tu estructura de archivos


const UserHome = () => {
    const user = useContext(AuthContext);
    const [products, setProducts] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [items, setItems] = useState([]);
    const [selectedItem, setSelectedItem] = useState();
    const { darkMode } = useTheme();
    const { fontSize, changeFontSize } = useFontSize();
    const { addToCarrito } = useCarrito();

    const checkRentas = async () => {
        try {
            const response = await AxiosClient({
                url:`/renta/demora/${user.user.data.id}/${user.user.data.username}`,
                method:"GET"
            })
            console.log(response)
            if(response.length !== 0){
                Alert.fire({
                    title:"IMPORTANTE",
                    icon:"x",
                    text:"REVISAR TUS RENTAS, TIENES PENDIENTES DE ENTREGA!! EVITA MULTAS"
                })
            }
        } catch (error) {
            Alert.fire({
                title:"ERROR",
                icon:"x",
                text:"SURGIO UN ERROR "
            })
        }
    }
    
    

    const handleAgregarAlCarrito = () => {
        console.log(selectedItem)
        addToCarrito(parseInt(selectedItem, 10));
        closeModal();
      };

    useEffect(() => {
        cargarDatos();
        checkRentas();
    }, []);
    const randomizarPosiciones = (lista) => {
        const listaRandomizada = [...lista];
        for (let i = listaRandomizada.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [listaRandomizada[i], listaRandomizada[j]] = [listaRandomizada[j], listaRandomizada[i]];
        }
        return listaRandomizada;
    };
    const cargarDatos = async () => {
        try {
            const response = await AxiosClient({
                url: "/producto/",
                method: "GET",
            });
            setProducts(randomizarPosiciones(response));
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
    const getItemsByProduct = async (productoId) => {
        try {
            const response = await AxiosClient({
                url: `/item/producto/${productoId}`,
                method: "GET"
            })
            setItems(response);
        } catch (error) {
            console.log(error)
        }
    }
    const openModal = (product) => {
        setSelectedProduct(product);
        setShowModal(true);
        getItemsByProduct(product.id)
    }

    const closeModal = () => {
        setSelectedItem({});
        setSelectedProduct({})
        setShowModal(false);
    }

   

    return (
        <div className={`UserMainContainer ${darkMode ? 'dark-mode' : 'light-mode'}`}>
            <div className='UserJuegos'>
                <div style={{
                    color: darkMode ? 'white' : 'black', fontSize/* Cambiar color basado en el estado */
                }}>Top juegos</div>
                <div className="UserCarrusel">
                    {products.slice(12, 17).map((product, index) => (
                        <div key={index} className="item" onClick={() => openModal(product)}>
                            <img
                                src={`${URL}:8080/uploads/${product.imagen}`}
                                alt={`Imagen de ${product.titulo}`}
                                style={{ width: '250px', height: '150px' }}
                            />
                        </div>
                    ))}
                </div>
            </div>
            <div className='UserJuegos'>
                <div style={{
                    color: darkMode ? 'white' : 'black',fontSize 
                }}>Recomendados</div>
                <div className="UserCarrusel">
                    {products.slice(7, 12).map((product, index) => (
                        <div key={index} className="item" onClick={() => openModal(product)}>
                            <img
                                src={`${URL}:8080/uploads/${product.imagen}`}
                                alt={`Imagen de ${product.titulo}`}
                                style={{ width: '250px', height: '150px' }}
                            />
                        </div>
                    ))}
                </div>
            </div>
            <div className='UserJuegos'>
                <div style={{
                    color: darkMode ? 'white' : 'black', fontSize/* Cambiar color basado en el estado */
                }}>Recien añadidos</div>
                <div className="UserCarrusel">
                    {products.slice(2, 7).map((product, index) => (
                        <div key={index} className="item" onClick={() => openModal(product)}>
                            <img
                                src={`${URL}:8080/uploads/${product.imagen}`}
                                alt={`Imagen de ${product.titulo}`}
                                style={{ width: '250px', height: '150px' }}
                            />
                        </div>
                    ))}
                </div>
            </div>

            {/* Modal para mostrar la información detallada del producto */}
            <Modal show={showModal} onHide={closeModal}>
                <Modal.Header closeButton>
                    <Modal.Title>{selectedProduct?.titulo}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <img
                        src={`${URL}:8080/uploads/${selectedProduct?.imagen}`}
                        alt={`Imagen de ${selectedProduct?.titulo}`}
                        style={{ width: '450px', height: '300px' }}
                    />
                    <p> <strong>Descripcion : </strong>{selectedProduct?.descripcion}</p>
                    {/* Select para plataformas */}
                    <label htmlFor="platform">Seleccionar plataforma:</label>
                    <select
                        id="platform"
                        name="platform"
                        value={selectedItem}
                        onChange={(e) => setSelectedItem(e.target.value)}
                    >
                        <option value="">-- Seleccionar --</option>
                        {items.map((item) => (
                            <option key={item.id} value={item.id}>
                                {item.plataforma}
                            </option>
                        ))}
                    </select>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeModal}>
                        Cerrar
                    </Button>
                    <Button variant="primary" onClick={handleAgregarAlCarrito}>
                        Solicitar
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default UserHome;
