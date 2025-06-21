import Vue from "vue";
import VueRouter from "vue-router";
import { jwtDecode } from "jwt-decode";

Vue.use(VueRouter);

import Login from "../components/Login.vue";
import Admin from "../components/Admin.vue"
import Editor from "../components/Editor.vue"
import Viewer from "../components/Viewer.vue"
import Unauthorized from "../components/Unathorized.vue"
import NotFound from "../components/NotFound.vue"

const routes = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    name: "login",
    path: "/login",
    component: Login
  },
  {
    name: "admin",
    path: "/admin",
    component: Admin,
    meta: { role: 'admin'}
  },
  {
    name: "editor",
    path: "/editor",
    component: Editor,
    meta: { role: 'editor'}
  },
  {
    name: "viewer",
    path: "/viewer",
    component: Viewer,
    meta: { role: 'viewer'}
  },
  {
    name:"unauthorized",
    path:'/unauthorized',
    component: Unauthorized
  },
  {
    path:'/:catchAll(.*)',
    name:'NotFound',
    component: NotFound
  }
];
const router = new VueRouter({
  routes
})

router.beforeEach((to,from, next) => {
  const publicPages = ['/login', '/', '/unauthorized'];
  //se añaden rutas que consideren publicas
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem('token');
  if(authRequired && !loggedIn){
    return next('/login');
  }
  if(loggedIn) { 
    const decoded = jwtDecode(loggedIn);
    const role = decoded.role;
    if(to.meta.role && to.meta.role !== role){
      return next('/unauthorized');
    }
  }
  next();
});

export default router;
