import React, { useContext, useEffect, useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import { useCarrito } from '../shoppingCart/CarritoContext'; // Ajusta la ruta según tu estructura de archivos
import FeatherIcon from 'feather-icons-react/build/FeatherIcon';
import AxiosClient from '../../shared/plugins/axios';
import { AuthContext } from '../auth/authContext';
import Alert from '../../shared/plugins/alerts';

export const ShoppingCartModal = ({ isOpen, onClose, items }) => {
  const user = useContext(AuthContext);

  const { carrito, clearCarrito, addToCarrito } = useCarrito();
  const [carritoNew , setCarritoNew] = useState([]);

  const clear = () => {
    setCarritoNew([]);
    clearCarrito();
  }

  const addToCart = async () => {
    try {
        const response = await AxiosClient({
            url: "/renta/",
            method: "POST",
            data: {
                userId: user.user.data.id,
                rentalist: carrito,
            }
        })
        if (!response.error) {
            Alert.fire({
                title: "ITEM AÑADIDO EXITOSAMENTE",
                text: "El item fue añadido con exito a tus productos",
                icon: "check",
                confirmButtonColor: "#3085d6",
                confirmButtonText: "Aceptar",
            });
            clearCarrito();
            onClose();
        };
    } catch (error) {
        Alert.fire({
            title: "ERROR AL AÑADIRO ITEM",
            text: "Hubo un error al añadir item, intente otra vez",
            icon: "x",
            confirmButtonColor: "#3085d6",
            confirmButtonText: "Aceptar",
        });
    } finally {
        clearCarrito();
    }
}
  

  useEffect(() => {
    addToCarritoAndCloseModal();
  }, []);
  
  const addToCarritoAndCloseModal = () => {
    setCarritoNew(items.filter(objeto => carrito.includes(objeto.id)));
  };

  return (
    <Modal show={isOpen} onHide={onClose} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>Carrito de Compras</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <ul>
          {carritoNew.map((item, index) => (
            <li key={index}>
              {item.titulo} - {item.plataforma}
            </li>
          ))}
        </ul>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="warning" onClick={clear}>
          <FeatherIcon icon="x" /> Limpiar Carrito
        </Button>

        <Button variant="primary" onClick={addToCart}>
          <FeatherIcon icon={'check'} /> Solicitar
        </Button>
      </Modal.Footer>
    </Modal>
  );
};
