
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

// Importa Bootstrap CSS e JS
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

// Importa Font Awesome
import '@fortawesome/fontawesome-free/css/all.min.css';

// Importa estilos globais da aplicação
import './assets/styles/main.scss';

createApp(App)
  .use(router)
  .mount('#app');

export default router;

// src/App.vue
