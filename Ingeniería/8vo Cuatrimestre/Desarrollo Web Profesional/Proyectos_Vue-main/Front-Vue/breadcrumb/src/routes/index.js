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
        path:'*',
        component:()=> import('../views/ErrorPages/Error404.vue')
    },
    {
        path: '/paginacion',
        name: 'paginacion',
        component:()=> import('../components/Persona.vue')
    },
    {
        path: '/inicio',
        name : 'inicio',
        component: ()=> import('../components/Inicio.vue')
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
                path: '/formulario',
                name:'formulario',
                component: () => import('../components/Formulario.vue')
            },
            {
                path: '/home',
                name : 'home',
                component: ()=> import('../components/Home.vue')
            },
            {
                path: '/user',
                name : 'user',
                component: ()=> import('../components/User.vue')
            },
            {
                path: '/inicio/electronicos',
                name : 'electronicos',
                component: ()=> import('../components/Electronicos.vue')
            },
            {
                path: '/inicio/electronicos/videojuegos',
                name : 'videojuegos',
                component: ()=> import('../components/Videojuegos.vue')
            },
            {
                path: '/home/cocina',
                name : 'cocina',
                component: ()=> import('../components/Cocina.vue')
            },
            {
                path: '/home/cocina/estufa',
                name : 'estufa',
                component: ()=> import('../components/Estufa.vue')
            },
            {
                path: '/user/hogar',
                name : 'hogar',
                component: ()=> import('../components/Hogar.vue')
            },
            {
                path: '/user/hogar/detergentes',
                name : 'detergentes',
                component: ()=> import('../components/Detergentes.vue')
            },

        ]
    },
]

const router = new VueRouter({routes, });
export default router;