import VueRouter from 'vue-router';
import Vue from 'vue';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        redirect: 'inicio'
    },
    {
        path:'*',
        component:()=> import('../views/ErrorPages/Error404.vue')
    },
    {
        path: '/inicio',
        name : 'inicio',
        component: ()=> import('../components/LandPage.vue')
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
                path: "/principal",
                name: "principal",
                component: () => import("../components/Inicio.vue"),
            },
            {
                path: '/formulario',
                name:'formulario',
                component: () => import('../components/Formulario.vue')
            },
            {
                path: '/paginacion',
                name: 'paginacion',
                component:()=> import('../components/Paginacion.vue')
            },
        ]
    },
]

const router = new VueRouter({routes, });
export default router;