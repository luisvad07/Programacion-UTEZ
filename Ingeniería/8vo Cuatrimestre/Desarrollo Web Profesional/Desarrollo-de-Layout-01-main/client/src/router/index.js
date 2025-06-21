import VueRouter from 'vue-router';
import Vue from 'vue';

Vue.use(VueRouter);

const routes = [
    //Ruta Base
    {
        path: '/',  // Inicio de la ruta
        redirect: 'inicio'
    },
    {
        path: '/',
        component: {
            render(c){
                return c('router-view');
            }
        },
        children : [
            {
                path: '/inicio', //Posterior a la ruta base
                name : 'inicio',
                component: ()=> import('../components/Inicio.vue')
            },
            {
                path: '/home', //Posterior a la ruta base
                name : 'home',
                component: ()=> import('../components/Home.vue')
            },
            {
                path: '/user', //Posterior a la ruta base
                name : 'user',
                component: ()=> import('../components/User.vue')
            }
        ]
    },
]

const router = new VueRouter({routes, });
export default router;