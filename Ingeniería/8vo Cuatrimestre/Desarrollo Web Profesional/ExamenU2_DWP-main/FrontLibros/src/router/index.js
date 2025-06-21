import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: '/',
      redirect: "/books"
    },
    {
      path: "/books",
      name: "books",
      component: () => import("../views/Books.vue")
    },
  ]
})

export default router
