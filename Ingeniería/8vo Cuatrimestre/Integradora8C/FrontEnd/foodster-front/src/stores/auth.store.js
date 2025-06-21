import {defineStore} from 'pinia';
import {showNotification} from "@/utils/notification";
import {axiosClient} from "@/utils/axios-client";
import router from "@/router";

const baseUrl = `/auth`;

export const useAuthStore = defineStore({
    id: 'auth', state: () => ({
        user: JSON.parse(localStorage.getItem('user')), returnUrl: null,

    }), actions: {
        async login(correo, contrasenia) {
            try {
                const userInfo = await axiosClient.post(`${baseUrl}/`, {correo, contrasenia});
                userInfo.data ? showNotification('success', 'Bienvenido') : showNotification('error', 'Usuario o contraseña incorrectos');
                this.user = userInfo.data;
                localStorage.setItem('user', JSON.stringify(userInfo.data));
                let route;
                switch (this.user.usuarios.roles[0].nombre) {
                    case 'ADMIN':
                        route = '/admin'
                        break;
                    case 'CLIENTE':
                        route = '/home/inicio'
                        break;
                    case 'PERSONAL':
                        route = '/personal/'
                        break;
                    default:
                        route = '/'
                        break;
                }
                this.returnUrl = route;
                await router.push(route);
            } catch (e) {
                console.error(e);
                showNotification('error', 'Usuario o contraseña incorrectos');
            }
        }, logout() {
            this.user = null;
            localStorage.removeItem('user');
            router.push('/home/inicio');
            showNotification('success', 'Sesión cerrada');

        }
    }
});
