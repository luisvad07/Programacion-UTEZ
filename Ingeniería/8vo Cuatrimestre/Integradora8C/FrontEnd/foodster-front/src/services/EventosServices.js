import {axiosClient} from "@/utils/axios-client";
import {showNotification} from "@/utils/notification";

const baseUrl = `/eventos`;

const getEventos = async () => {
    try {
        const response = await axiosClient.get(`${baseUrl}/`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener eventos")
    }
}

const getAllPaginado = async (page, size) => {
    try {
        // /paginado/{page}/{size}
        const response = await axiosClient.get(`${baseUrl}/paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener elementos del paginado")
    }
};

const updateEvento = async (evento) => {
    try {
        const response = await axiosClient.put(`${baseUrl}/`, evento);
        response.data ? showNotification("success", "Evento actualizado") : showNotification("error", "Error al actualizar evento")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al actualizar evento")
    }
}

const createEvento = async (evento) => {
    try {
        const response = await axiosClient.post(`${baseUrl}/`, evento);
        response.data ? showNotification("success", "Evento creado") : showNotification("error", "Error al crear evento")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al crear evento")
    }
}

const setFinalizarEvento = async (uid) => {
    try {
        const response = await axiosClient.put(`${baseUrl}/finalizar/${uid}`);
        response.data ? showNotification("success", "Evento finalizado") : showNotification("error", "Error al finalizar evento")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al finalizar evento")
    }
}

const setCancelarEvento = async (uid) => {
    try {
        const response = await axiosClient.put(`${baseUrl}/cancelar/${uid}`);
        response.data ? showNotification("success", "Evento cancelado") : showNotification("error", "Error al cancelar evento")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al cancelar evento")
    }

}

const getEventosByStatus = async (status) => {
    try {
        const response = await axiosClient.get(`${baseUrl}/status/${status}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener eventos por estado")
    }
}
const getEventosByIdUsuario = async (uid) => {
    try {
        const response = await axiosClient.get(`${baseUrl}/usuario/${uid}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener eventos por usuario")
    }
}

// este se puede acceder sin mandar id de usuario
const getEventosByUsuario = async () => {
    try {
        const response = await axiosClient.get(`${baseUrl}/usuario/`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener eventos por usuario")
    }
}

const getEventosByPersonalIdUsuario = async () => {
    try {
        const response = await axiosClient.get(`${baseUrl}/personal/`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener eventos por usuario")
    }
}

const deleteEvento = async (uid) => {
    try {
        const response = await axiosClient.delete(`${baseUrl}/${uid}`);
        !response.data ? showNotification("success", "Evento eliminado") : showNotification("error", "Error al eliminar evento")
        return response.data;
    } catch (error) {
        showNotification("error", "Error al eliminar evento")
    }
}

export {
    getEventos,
    getEventosByIdUsuario,
    getEventosByPersonalIdUsuario,
    updateEvento,
    setFinalizarEvento,
    setCancelarEvento,
    createEvento,
    getEventosByStatus,
    deleteEvento,
    getEventosByUsuario,
    getAllPaginado

}