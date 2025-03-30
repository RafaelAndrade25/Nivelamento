import { createRouter, createWebHistory } from 'vue-router'
import buscaOperadores from '../views/BuscaOperadores.vue';

const routes = [
  {
    path: '/',
    name: 'BuscaOperadores',
    component: buscaOperadores
  }
];

export default createRouter({  // Remova a variável `router` e exporte diretamente
  history: createWebHistory(process.env.BASE_URL),
  routes
});
