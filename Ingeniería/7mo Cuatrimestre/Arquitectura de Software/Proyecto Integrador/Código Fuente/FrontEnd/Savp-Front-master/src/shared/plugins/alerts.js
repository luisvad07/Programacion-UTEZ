import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";

const Alert = withReactContent(Swal);

export const confirmMsj= 'Le solicitamos esperar un momento a que la solicitud termine';
export const confirmTitle = '¿Estás seguro de realizar la acción?';
export const succesMsj= 'La actividad solicitada, ha sido realizada exitosamente';
export const successTitle = 'Acción realizada correctamente';
export const errorMsj = "No se ha realizado la actividad por lo cual le pedimos intentar nuevamente, en caso contrario contactar a soporte técnico para solucionar el problema";
export const errorTitle = 'Error al realizar la acción';
export default Alert;