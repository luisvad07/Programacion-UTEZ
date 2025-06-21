import {axiosClient} from "@/utils/axios-client";
import {showNotification} from "@/utils/notification";

const baseUrl = '/auth';


export const recuperarContra = async (cambioRequestDto) => {
    try {
        const response = await axiosClient.post(`${baseUrl}/restablecer`, cambioRequestDto)
        response.data ? showNotification("success", "Correo enviado") : showNotification("error", "Error al enviar correo");
        return response.data;
    } catch (error) {
        console.log(error);
        showNotification("error", "Error al recuperar contraseña");
    }
}

export const confirmarCambio = async (restablecerContraDto) => {
    try {
        const response = await axiosClient.post(`${baseUrl}/confirmar`, restablecerContraDto)
        response.data ? showNotification("success", "Contraseña cambiada") : showNotification("error", "Error al cambiar contraseña");
        return response.data;
    } catch (error) {
        console.log(error);
        showNotification("error", "Error al confirmar el cambio de contraseña");
    }
}