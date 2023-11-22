import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import GuestView from '../views/GuestView.vue'
import AdministrationView from '../views/AdministrationView.vue'
import TokenService from '@/services/TokenService'
import UserService from '@/services/UserService'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },    {
      path: '/code/:code',
      name: 'homeCode',
      props: true,
      component: HomeView
    },
     {
      path: '/guests',
      name: 'guests',
      component: GuestView,
      beforeEnter: async function (to, from, next)  {
        if(await TokenService.localTokenSet()) {
          return next();
        }
        return next({name: "home"});
      },
    }, {
      path: '/admin',
      name: 'admin',
      component: AdministrationView,
      beforeEnter: async function (to, from, next)  {
        if(await UserService.isUserLoggedIn()) {
          return next();
        }
        window.location.replace("/api/auth/oauth2/authorization/authentik");
        return next();
      }
    }, {
      path: '/admin/redirect',
      name: 'adminRedirect',
      component: AdministrationView,
      beforeEnter: async function (to, from, next)  {
        await UserService.setUserLoggedIn(true)
        return next({name: "admin"});
      }
    }
  ]
})

export default router

