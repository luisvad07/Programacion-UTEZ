export const guardarCarrito = (carrito) => {
  try {
    localStorage.setItem("carrito", JSON.stringify(carrito));
  } catch (error) {
    console.error(error);
  }
};

export const obtenerCarrito = () => {
  try {
    const carrito = localStorage.getItem("carrito");
    return JSON.parse(carrito);
  } catch (error) {
    console.error(error);
    return null;
  }
};

export const agregarProductoAlCarrito = (elemento) => {
  try {
    const carrito = obtenerCarrito();
    const elementoExistente = carrito.find(
      (item) => item.servicio.idServicio === elemento.servicio.idServicio
    );

    if (elementoExistente) {
      const carritoActualizado = carrito.map((item) => {
        if (item.servicio.idServicio === elemento.servicio.idServicio) {
          const existencias = parseInt(item.servicio.existencias);
          const cantidad = parseInt(item.cantidad);
          const cantidadNueva = parseInt(elemento.cantidad);
          if (cantidad + cantidadNueva > existencias) {
            item.cantidad = existencias;
          } else {
            item.cantidad = cantidad + cantidadNueva;
          }
        }
        return item;
      });

      guardarCarrito(carritoActualizado);
      return carritoActualizado;
    } else {
      const carritoActualizado = [...carrito, elemento];
      guardarCarrito(carritoActualizado);
      return carritoActualizado;
    }
  } catch (error) {
    console.error(error);
    return null;
  }
};

export const eliminarElementoDelCarrito = (elementoId) => {
  try {
    const carrito = obtenerCarrito();
    const carritoActualizado = carrito.filter(
      (item) => item.servicio.idServicio !== elementoId
    );

    guardarCarrito(carritoActualizado);
    return carritoActualizado;
  } catch (error) {
    console.error(error);
    return null;
  }
};

export const obtenerTotalCarrito = () => {
  try {
    const carrito = obtenerCarrito();
    let total = 0;

    if (carrito) {
      carrito.forEach((item) => {
        const cantidad = parseInt(item.cantidad);
        const precio = parseFloat(item.precio)
        total += cantidad * precio;
      });
    }
    return parseFloat(total).toFixed(2);
  } catch (error) {
    console.error(error);
    return null;
  }
};
