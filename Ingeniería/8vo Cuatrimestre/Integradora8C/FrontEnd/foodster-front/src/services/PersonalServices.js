import {axiosClient} from "@/utils/axios-client";
import {showNotification} from "@/utils/notification";

let baseUrl = `/personal`;

const getPersonal = async () => {
    try {
        const response = await axiosClient.get(`${baseUrl}/`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener personal");
    }
};

const getAllPaginado = async (page, size) => {
    try {
        // /paginado/{page}/{size}
        const response = await axiosClient.get(`${baseUrl}/paginado/${page}/${size}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener elementos del paginado")
    }
};

const insert = async (personal) => {
    try {
        const response = await axiosClient.post(`${baseUrl}/`, {
            categoria: {
                idCategoria: personal.categoria.idCategoria,
            },
            active: true,
            usuarios: {
                nombres: personal.nombres,
                primerApellido: personal.primerApellido,
                segundoApellido: personal.segundoApellido,
                telefono: personal.telefono,
                correo: personal.correo,
                contrasena: personal.contrasena,
                active: true,
                roles: [{
                    idRol: personal.idRol,
                }],
            },
        });

        if (response.data) {
            showNotification("success", "Personal creado");
        } else {
            showNotification("error", "Error al crear personal");
        }
    } catch (error) {
        showNotification("error", "Error al crear personal")
    }
};
const getPersonalDisponible = async (fechaHoraInicio, fechaHoraFin) => {
    try {
        const response = await axiosClient.get(`${baseUrl}/disponibles/${fechaHoraInicio}/${fechaHoraFin}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener personal disponibles");
    }
};

const delete_ = async (id_personal) => {
    try {
        const response = await axiosClient.delete(`${baseUrl}/${id_personal}`);
        !response.data ? showNotification("success", "Personal eliminado") : showNotification("error", "Error al eliminar personal");
    } catch (error) {
        showNotification("error", "Error al eliminar personal");
    }
};

const deletePersonalEvento = async (uid) => {
    console.log("usuario a eliminar desde servicio: ", uid);
    try {
        const response = await axiosClient.delete(`${baseUrl}/${uid}`);
        !response.data ? showNotification("success", "Personal eliminado del evento") : showNotification("error", "Error al eliminar personal del evento");
    } catch (error) {
        showNotification("error", "Error al eliminar personal del evento");
    }
};

const changeStatus = async (id_personal) => {
    try {
        const response = await axiosClient.delete(`${baseUrl}/status/${id_personal}`);
        !response.data ? showNotification("success", "Personal eliminado") : showNotification("error", "Error al eliminar personal");
        return response.data;
    } catch (error) {
        console.log(error);
    }
};

const update = async (personal) => {
    try {
        const response = await axiosClient.put(`${baseUrl}/`, personal);
        if (response.data) {
            showNotification("success", "Personal actualizado");
        } else {
            showNotification("error", "Error al actualizar personal")
        }
    } catch (error) {
        showNotification("error", "Error al actualizar personal")
    }
};

const getPersonalEvento = async (idEvento) => {
    try {
        const response = await axiosClient.get(`${baseUrl}-evento/evento/${idEvento}`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener personal");
    }
};

const asignarPersonalEvento = async (personalEvento) => {
    try {
        const response = await axiosClient.post(`${baseUrl}-evento/`, personalEvento);
        if (response.data) {
            showNotification("success", "Personal creado en evento");
        } else {
            showNotification("error", "Error al crear personal en evento");
        }
    } catch (error) {
        showNotification("error", "Error al crear personal en evento")
    }
}

export default {
    getPersonal, insert, delete_, deletePersonalEvento, changeStatus, update, getAllPaginado, getPersonalEvento,getPersonalDisponible,asignarPersonalEvento
};