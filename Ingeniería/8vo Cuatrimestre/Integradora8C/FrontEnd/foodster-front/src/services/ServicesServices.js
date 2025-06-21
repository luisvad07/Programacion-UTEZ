import {axiosClient} from "@/utils/axios-client";
import {showNotification} from "@/utils/notification";

const getServicios = async () => {
    try {
        const response = await axiosClient.get("/servicios/");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios");

    }
};

const getAllServiciosPaginado = async (page, size) => {
    try {

        const response = await axiosClient.get(`/servicios/paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener Servicios")
    }
};

const getServiciosByCategoria = async (idCategoria) => {
    try {
        const response = await axiosClient.get("/servicios/categoria/" + idCategoria);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios por categoría");

    }
};
const getServiciosByCategoriaPaginado = async (idCategoria, page, size) => {
    try {
        const response = await axiosClient.get(`/servicios/categoria/${idCategoria}/paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios por categoría");

    }
};



const updateServicio = async (servicio) => {
    try {
        const response = await axiosClient.put("/servicios/", servicio);
        response.data ? showNotification("success", "Servicio actualizado") : showNotification("error", "Error al actualizar servicio");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al actualizar servicio");

    }
};

const createServicio = async (servicio) => {
    try {
        const response = await axiosClient.post("/servicios/", servicio);
        response.data ? showNotification("success", "Servicio creado") : showNotification("error", "Error al crear servicio");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al crear servicio");

    }
};

const getServiciosByStatus = async (status) => {
    try {
        const response = await axiosClient.get(`/servicios/status/${status}`);

        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios por estado");

    }
};

const deleteServicio = async (uid) => {
    try {
        const response = await axiosClient.delete(`/servicios/${uid}`);
        !response.data ? showNotification("success", "Servicio eliminado") : showNotification("error", "Error al eliminar servicio");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar servicio");

    }
};

const deleteServicioByStatus = async (uid) => {
    try {
        const response = await axiosClient.delete(`/servicios/status/${uid}`);
        response.data ? showNotification("success", "Servicio eliminado") : showNotification("error", "Error al eliminar servicio");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar servicio");

    }
};

const getServiciosByPaquete = async (idPaquete) => {
    try {
        const response = await axiosClient.get(`/servicios-paquete/paquete/${idPaquete}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de paquete");

    }
};

const getServiciosPaquetePaginado = async (page, size) => {
    try {
        const response = await axiosClient.get(`/servicios-paquete/paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de paquete")
    }

}

const updateServicioPaquete = async (servicioPaquete) => {
    try {
        const response = await axiosClient.put("/servicios-paquete/", servicioPaquete);
        response.data ? showNotification("success", "Servicio de paquete actualizado") : showNotification("error", "Error al actualizar servicio de paquete");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al actualizar servicio de paquete");

    }
};

const createServicioPaquete = async (servicioPaquete) => {
    try {
        const response = await axiosClient.post("/servicios-paquete/", servicioPaquete);
        response.data ? showNotification("success", "Servicio de paquete creado") : showNotification("error", "Error al crear servicio de paquete");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al crear servicio de paquete");

    }
};

const getServiciosPaqueteByStatus = async (status) => {
    try {
        const response = await axiosClient.get(`/servicios-paquete/status/${status}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de paquete por estado");

    }
};

const deleteServicioPaquete = async (id) => {
    try {
        const response = await axiosClient.delete(`/servicios-paquete/${id}`);
        !response.data ? showNotification("success", "Servicio de paquete eliminado") : showNotification("error", "Error al eliminar servicio de paquete");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar servicio de paquete");

    }
};

const deleteServicioPaqueteByStatus = async (id) => {
    try {
        const response = await axiosClient.delete(`/servicios-paquete/status/${id}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar servicio de paquete por estado");

    }
};

// Exportamos todas las funciones
export {
    getServicios,
    getAllServiciosPaginado,
    getServiciosByCategoria,
    getServiciosByCategoriaPaginado,
    updateServicio,
    createServicio,
    getServiciosByStatus,
    deleteServicio,
    deleteServicioByStatus,
    getServiciosByPaquete,
    getServiciosPaquetePaginado,
    updateServicioPaquete,
    createServicioPaquete,
    getServiciosPaqueteByStatus,
    deleteServicioPaquete,
    deleteServicioPaqueteByStatus,
};
