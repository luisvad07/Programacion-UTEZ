import { axiosClient } from "@/utils/axios-client";
import {showNotification} from "@/utils/notification";

const getCategoriasPersonales = async () => {
    try {
        const response = await axiosClient.get(`/categorias-personal/`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener categorías personales")
    }
}
const getAllCategoriasPersonalesPaginado = async (page, size) => {
    try {
        // /paginado/{page}/{size}
        const response = await axiosClient.get(`/categorias-personal/paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener elementos del paginado")
    }
};

const actualizarCategoriaPersonal = async (item) => {
    try {
        const response = await axiosClient.put(`/categorias-personal/`, item);
        response.data ? showNotification("success", "Categoría personal actualizada") : showNotification("error", "Error al actualizar categoría personal")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al actualizar categoría personal")
    }
}

const crearCategoriaPersonal = async (categoriaPersonal) => {
    try {
        const response = await axiosClient.post(`/categorias-personal/`, categoriaPersonal);
        response.data ? showNotification("success", "Categoría personal creada") : showNotification("error", "Error al crear categoría personal")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al crear categoría personal")
    }
}

const getCategoriasPersonalesPorEstado = async (status) => {
    try {
        const response = await axiosClient.get(`/categorias-personal/status/${status}`);
        return response.data;

    } catch (error) {
        showNotification("error", "Error al obtener categorías personales por estado")
    }
}

const eliminarCategoriaPersonal = async (uid) => {

    try {
        const response = await axiosClient.delete(`/categorias-personal/${uid}`);
        response.data ? showNotification("success", "Categoría personal eliminada") : showNotification("error", "Error al eliminar categoría personal")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar categoría personal")
    }
}
const getCategoriasServicios = async () => {
    try {
        const response = await axiosClient.get(`/categorias-servicios/`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener categorías de servicios")
    }
}

const getAllCategoriasServiciosPaginado = async (page, size) => {
    try {
        // /paginado/{page}/{size}
        const response = await axiosClient.get(`/categorias-servicios/paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener elementos del paginado")
    }

}


const actualizarCategoriaServicio = async (item) => {
    try {
        const response = await axiosClient.put(`/categorias-servicios/`, item);
        response.data ? showNotification("success", "Categoría de servicio actualizada") : showNotification("error", "Error al actualizar categoría de servicio")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al actualizar categoría de servicio")
    }
}

const crearCategoriaServicio = async (categoriaServicio) => {
    try {
        const response = await axiosClient.post(`/categorias-servicios/`, categoriaServicio);
        response.data ? showNotification("success", "Categoría de servicio creada") : showNotification("error", "Error al crear categoría de servicio")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al crear categoría de servicio")
    }
}

const getCategoriasServiciosPorEstado = async (status) => {
    try {
        const response = await axiosClient.get(`/categorias-servicios/status/${status}`);
        response.data ? showNotification("success", "Categorías de servicios obtenidas") : showNotification("error", "Error al obtener categorías de servicios por estado")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener categorías de servicios por estado")
    }
}

const eliminarCategoriaServicio = async (uid) => {
    try {
        const response = await axiosClient.delete(`/categorias-servicios/${uid}`);
        showNotification("success", "Categoría de servicio eliminada")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar categoría de servicio")
    }
}

const eliminarCategoriaServicioPorEstado = async (uid) => {
    try {
        const response = await axiosClient.delete(`/categorias-servicios/status/${uid}`);
        response.data ? showNotification("success", "Categoría de servicio eliminada") : showNotification("error", "Error al eliminar categoría de servicio")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar categoría de servicio")
    }
}

export {
    getCategoriasPersonales,
    actualizarCategoriaPersonal,
    crearCategoriaPersonal,
    getCategoriasPersonalesPorEstado,
    eliminarCategoriaPersonal,
    getCategoriasServicios,
    actualizarCategoriaServicio,
    crearCategoriaServicio,
    getCategoriasServiciosPorEstado,
    eliminarCategoriaServicio,
    eliminarCategoriaServicioPorEstado,
    getAllCategoriasPersonalesPaginado,
    getAllCategoriasServiciosPaginado
};
