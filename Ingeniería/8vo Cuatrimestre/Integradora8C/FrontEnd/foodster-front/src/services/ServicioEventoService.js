import { axiosClient } from "@/utils/axios-client";
import { showNotification } from "@/utils/notification";

const baseUrl = "/servicios-evento/";

const getServiciosEvento = async () => {
    try {
        const response = await axiosClient.get(baseUrl);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de evento")

    }
};

const updateServicioEvento = async (servicioEvento) => {
    try {
        const response = await axiosClient.put(baseUrl, servicioEvento);
        response.data ? showNotification("success", "Servicio de evento actualizado") : showNotification("error", "Error al actualizar servicio de evento");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al actualizar servicio de evento")

    }
};

const createServicioEvento = async (servicioEvento) => {
    try {
        const response = await axiosClient.post(baseUrl, servicioEvento);
        response.data ? showNotification("success", "Servicio de evento creado") : showNotification("error", "Error al crear servicio de evento");
        return response.data;
    } catch (error) {
        showNotification("error", "Error al crear servicio de evento")

    }
};

const getServicioEvento = async (uid) => {
    try {
        const response = await axiosClient.get(`${baseUrl}${uid}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicio de evento")

    }
};

const deleteServicioEvento = async (uid) => {
    try {
        const response = await axiosClient.delete(`${baseUrl}${uid}`);

        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar servicio de evento")

    }
};

const getServiciosEventoByStatus = async (status) => {
    try {
        const response = await axiosClient.get(`${baseUrl}status/${status}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de evento por estado")

    }
};

const getServiciosEventoByEvento = async (uid) => {
    try {
        const response = await axiosClient.get(`${baseUrl}evento/${uid}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener servicios de evento por evento")
    }
};

const deleteServiciosEventoByStatus = async (uid) => {
    try {
        const response = await axiosClient.delete(`${baseUrl}status/${uid}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar servicios de evento por estado")

    }
};

export default {
    getServiciosEvento,
    updateServicioEvento,
    createServicioEvento,
    getServicioEvento,
    deleteServicioEvento,
    getServiciosEventoByStatus,
    getServiciosEventoByEvento,
    deleteServiciosEventoByStatus,
};