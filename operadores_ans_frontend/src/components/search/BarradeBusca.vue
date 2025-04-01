
<template>
  <div class="busca-operadoras">
    <h1>Busca de Operadoras ANS</h1>
    
    <div class="search-container">
      <input 
        type="text" 
        v-model="termoBusca" 
        placeholder="Digite o nome ou registro da operadora..."
        @keyup.enter="buscarOperadoras"
      />
      <button @click="buscarOperadoras" :disabled="isLoading">
        {{ isLoading ? 'Buscando...' : 'Buscar' }}
      </button>
    </div>
    
    <div v-if="mensagemErro" class="error-message">
      {{ mensagemErro }}
    </div>
    
    <div v-if="resultados.length > 0" class="resultados">
      <h2>Resultados da Busca ({{ totalResultados }})</h2>
      <table>
        <thead>
          <tr>
            <th>Registro ANS</th>
            <th>CNPJ</th>
            <th>Razão Social</th>
            <th>Nome Fantasia</th>
            <th>Modalidade</th>
            <th>Situação</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(op, index) in resultados" :key="index">
            <td>{{ op.Registro_ANS }}</td>
            <td>{{ op.CNPJ }}</td>
            <td>{{ op.Razao_Social }}</td>
            <td>{{ op.Nome_Fantasia }}</td>
            <td>{{ op.Modalidade }}</td>
            <td>{{ op.Situacao }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-else-if="buscaRealizada && !isLoading" class="no-results">
      Nenhum resultado encontrado para "{{ ultimaBusca }}".
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'BuscaOperadoras',
  data() {
    return {
      termoBusca: '',
      resultados: [],
      totalResultados: 0,
      isLoading: false,
      buscaRealizada: false,
      ultimaBusca: '',
      mensagemErro: ''
    }
  },
  methods: {
    async buscarOperadoras() {
      if (!this.termoBusca.trim()) {
        this.mensagemErro = 'Por favor, digite um termo de busca.';
        return;
      }
      
      this.mensagemErro = '';
      this.isLoading = true;
      this.buscaRealizada = true;
      this.ultimaBusca = this.termoBusca;
      
      try {
        const response = await axios.get(`http://localhost:5000/api/operadoras`, {
          params: { q: this.termoBusca }
        });
        
        this.resultados = response.data.resultados;
        this.totalResultados = response.data.total_resultados;
      } catch (error) {
        console.error('Erro ao buscar operadoras:', error);
        this.mensagemErro = 'Ocorreu um erro ao buscar as operadoras. Tente novamente.';
        this.resultados = [];
      } finally {
        this.isLoading = false;
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.busca-operadoras {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  
  h1 {
    color: #2c3e50;
    margin-bottom: 30px;
    text-align: center;
  }
  
  .search-container {
    display: flex;
    margin-bottom: 20px;
    
    input {
      flex: 1;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px 0 0 4px;
      font-size: 16px;
    }
    
    button {
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 0 4px 4px 0;
      cursor: pointer;
      font-size: 16px;
      
      &:disabled {
        background-color: #cccccc;
      }
      
      &:hover:not(:disabled) {
        background-color: #45a049;
      }
    }
  }
  
  .error-message {
    color: #d9534f;
    margin-bottom: 15px;
  }
  
  .resultados {
    h2 {
      color: #2c3e50;
      margin-bottom: 15px;
    }
    
    table {
      width: 100%;
      border-collapse: collapse;
      
      th, td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: left;
      }
      
      th {
        background-color: #f2f2f2;
        font-weight: bold;
      }
      
      tr:nth-child(even) {
        background-color: #f9f9f9;
      }
      
      tr:hover {
        background-color: #f1f1f1;
      }
    }
  }
  
  .no-results {
    text-align: center;
    color: #777;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
}
</style>
