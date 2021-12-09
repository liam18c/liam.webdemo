import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import Home from "@/views/Home";
const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/login",
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import("@/views/Home"),
      },
      {
        path: 'basketball',
        name: 'Basketball',
        component: () => import("@/views/Basketball"),
      },
      {
        path: 'football',
        name: 'Football',
        component: () => import("@/views/football"),
      },
      {
        path: 'badminton',
        name: 'Badminton',
        component: () => import("@/views/badminton"),
      },
      {
        path: '/person',
        name: 'Person',
        component: ()=>import("@/views/Person")
      },
      {
        path: '/cart',
        name: 'Cart',
        component: ()=>import("@/views/Cart")
      },
      {
        path: '/record',
        name: 'Record',
        component: ()=>import("@/views/Record")
      },
      {
        path: '/adminrecord',
        name: 'Adminrecord',
        component: ()=>import("@/views/adminRecord")
      },
    ]
},
  {
    path: '/login',
    name: 'Login',
    component: ()=>import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: ()=>import("@/views/Register")
  },


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
