import {axiosClient} from "@/utils/axios-client";
import {showNotification} from "@/utils/notification";

let baseUrl = `/roles`;

const getRoles = async () => {
    try {
        const response = await axiosClient.get(`${baseUrl}/`);
        return response.data;
    } catch (error) {
        showNotification("error", "Error al obtener roles")
    }
}


export default {
   getRoles,
}