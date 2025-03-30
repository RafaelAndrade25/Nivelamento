// src/services/api.js
import axios from 'axios';

// Cria uma instância do Axios com configurações base
const apiClient = axios.create({
  baseURL: 'http://localhost:5000/api',
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  },
  timeout: 10000 // 10 segundos
});

// Serviço de busca de operadoras
export default {
  // Método para buscar operadoras pelo termo de busca
  buscaOperadores(searchTerm) {
    return apiClient.get('/search', {
      params: {
        q: searchTerm
      }
    });
  }
};