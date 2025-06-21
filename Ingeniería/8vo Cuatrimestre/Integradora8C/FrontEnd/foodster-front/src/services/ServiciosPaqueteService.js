import { axiosClient } from "@/utils/axios-client";
import { showNotification } from "@/utils/notification";

const baseUrl = "/servicios-paquete/";

const getServiciosPaquete = async () => {
    try {
        const response = await axiosClient.get(baseUrl);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de paquete")

    }
};
const createServicioPaquete = async (servicioPaquete) => {
    try {
        const response = await axiosClient.post(baseUrl, servicioPaquete);
        showNotification("success", "Servicio de paquete creado exitosamente");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al crear servicio de paquete");
    }
};

const updateServicioPaquete = async (id, servicioPaquete) => {
    try {
        const response = await axiosClient.put(`${baseUrl}${id}`, servicioPaquete);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al actualizar servicio de paquete");
    }
};

const deleteServicioPaquete = async (id) => {
    try {
        const response = await axiosClient.delete(`${baseUrl}${id}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar servicio de paquete");
    }
};

const getServicioPaqueteById = async (id) => {
    try {
        const response = await axiosClient.get(`${baseUrl}${id}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicio de paquete por ID");
    }
};

const getServiciosPaqueteByStatus = async (status) => {
    try {
        const response = await axiosClient.get(`${baseUrl}status/${status}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de paquete por estado");
    }
};

const getServiciosPaqueteByStatusPaginated = async (status, page, size) => {
    try {
        const response = await axiosClient.get(`${baseUrl}status/${status}/paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de paquete por estado paginados");
    }
};

const getServiciosPaqueteByPaqueteId = async (id) => {
    try {
        const response = await axiosClient.get(`${baseUrl}paquete/${id}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de paquete por ID de paquete");
    }
};

const getServiciosPaquetePaginated = async (page, size) => {
    try {
        const response = await axiosClient.get(`${baseUrl}paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de paquete paginados");
    }
};

const deleteServicioPaqueteByStatus = async (id) => {
    try {
        const response = await axiosClient.delete(`${baseUrl}status/${id}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar servicio de paquete por estado");
    }
};

export {
    getServiciosPaquete,
    createServicioPaquete,
    updateServicioPaquete,
    deleteServicioPaquete,
    getServicioPaqueteById,
    getServiciosPaqueteByStatus,
    getServiciosPaqueteByStatusPaginated,
    getServiciosPaqueteByPaqueteId,
    getServiciosPaquetePaginated,
    deleteServicioPaqueteByStatus
};