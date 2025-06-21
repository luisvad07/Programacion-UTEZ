import Vue from 'vue'
import VueRouter from 'vue-router'
import { authGuard } from '../auth/authGuard';

import Home from '../views/Home.vue'
import Profile from '../views/Profile.vue';

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    
    {
      path: '/profile',
      name: 'profile',
      component: Profile,
      beforeEnter: authGuard
    }
  ]
})

export default router
