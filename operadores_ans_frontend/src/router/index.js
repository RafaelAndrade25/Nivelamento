import { createRouter, createWebHistory } from 'vue-router'
import buscaOperadores from '../views/BuscaOperadores.vue';

const routes = [
  {
    path: '/',
    name: 'BuscaOperadores',
    component: buscaOperadores
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
