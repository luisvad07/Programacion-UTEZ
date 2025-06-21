import {axiosClient} from "@/utils/axios-client";
import {showNotification} from "@/utils/notification";

const baseUrl = '/categoria-personal';

const getCategoriasPersonal = async () => {
    try {
        const response = await axiosClient.get(`${baseUrl}/`)
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener categorias personal");
    }
}
const getCategoriasPersonalByStatus = async (status) => {
    try {
        const response = await axiosClient.get(`${baseUrl}/status/${status}`)
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener categorias personal por estado");
    }
}


export default {
    getCategoriasPersonal,
    getCategoriasPersonalByStatus

}