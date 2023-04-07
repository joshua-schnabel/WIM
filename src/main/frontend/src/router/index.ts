import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import GuestView from '../views/GuestView.vue'
import AdministrationView from '../views/AdministrationView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    }, {
      path: '/guests',
      name: 'guests',
      component: GuestView
    }, {
      path: '/admin',
      name: 'admin',
      component: AdministrationView
    }
  ]
})

export default router
