import VueRouter from 'vue-router';
import Vue from 'vue';

Vue.use(VueRouter);

const routes = [
    //Ruta Base
    {
        path: '/',  // Inicio de la ruta
        redirect: 'formulario'
    },
    {
        path:'*',
        component:()=> import('../views/ErrorPages/Error404.vue')
    },
    {
        path: '/formulario',
        component: () => import('../components/Formulario.vue')
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
            }

        ]
    },
]

const router = new VueRouter({routes, });
export default router;