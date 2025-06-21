import {defineStore} from 'pinia';
import {showNotification} from "@/utils/notification";
import {getServiciosByPaquete} from "@/services/ServicesServices";


export const useCartStore = defineStore({
    id: 'cart',
    state: () => ({
        cart: JSON.parse(localStorage.getItem('cart')) || [],
        idPaquete: localStorage.getItem('idPaquete') || ''
    }),
    actions: {
        addStuff(nuevoServicio) {
            this.cart = [...this.cart, {...nuevoServicio, cantidad: 1}];
            localStorage.setItem('cart', JSON.stringify(this.cart));
            showNotification('success', 'Articulo añadido correctamente');
        },
        substractStuff(idServicio) {
            this.cart = this.cart.filter((item) => item.idServicio !== idServicio);
            localStorage.setItem('cart', JSON.stringify(this.cart));
            showNotification('success', 'Articulo sustraido correctamente');
            console.log(this.$state);
        },
        isInCart(idServicio) {
            return this.cart.some(item => item.idServicio === idServicio);
        },
        getTotal() {
            return this.cart.reduce((suma, item) => {
                let precio = item.precioDescuento > 0 ? item.precioDescuento : item.precio;
                return suma + (precio * item.cantidad)
            }, 0)
        },
        deleteAll() {
            this.cart = [];
            this.idPaquete = '';
            localStorage.removeItem('idPaquete');
            localStorage.setItem('cart', JSON.stringify(this.cart));
        },

        setIdPaquete(idPaquete) {
            this.idPaquete = idPaquete;
            localStorage.setItem('idPaquete', idPaquete);
        },

        async setPaquete(idPaquete) {
            this.deleteAll();
            this.setIdPaquete(idPaquete);
            const serviciosPaquete = await getServiciosByPaquete(idPaquete);
            serviciosPaquete.forEach(serviciosPaquete => {
                this.addStuff(serviciosPaquete.servicio);
            });
            showNotification('success', 'Paquete añadido correctamente')
        }
    }
});