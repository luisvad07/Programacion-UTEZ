// CarritoContext.js
import React, { createContext, useContext, useState } from 'react';

const CarritoContext = createContext();

export const useCarrito = () => useContext(CarritoContext);

export const CarritoProvider = ({ children }) => {
  const [carrito, setCarrito] = useState([]);

  const addToCarrito = (item) => {
    if (carrito.length < 3) {
      setCarrito([...carrito, item]);
    }
  };

  const clearCarrito = () => {
    setCarrito([]);
  };

  return (
    <CarritoContext.Provider value={{ carrito, addToCarrito, clearCarrito }}>
      {children}
    </CarritoContext.Provider>
  );
};
