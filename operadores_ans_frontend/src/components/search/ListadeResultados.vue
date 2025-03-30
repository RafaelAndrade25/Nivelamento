
<template>
    <div class="results-list">
      <div v-if="loading" class="d-flex justify-content-center my-5">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Carregando...</span>
        </div>
      </div>
      <div v-else-if="results.length === 0 && searched" class="alert alert-info">
        Nenhuma operadora encontrada para o termo "{{ searchTerm }}".
      </div>
      <div v-else-if="results.length > 0" class="results-container">
        <h3>{{ results.length }} resultado(s) para "{{ searchTerm }}"</h3>
        <div class="table-responsive">
          <table class="table table-striped table-hover">
            <thead>
              <tr>
                <th>Registro ANS</th>
                <th>Nome</th>
                <th>CNPJ</th>
                <th>Modalidade</th>
                <th>Cidade/UF</th>
                <th>Contato</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="operator in results" :key="operator.registro_ans">
                <td>{{ operator.registro_ans }}</td>
                <td>
                  <div class="fw-bold">{{ operator.razao_social }}</div>
                  <div v-if="operator.nome_fantasia">{{ operator.nome_fantasia }}</div>
                </td>
                <td>{{ formatCNPJ(operator.cnpj) }}</td>
                <td>{{ operator.modalidade }}</td>
                <td>{{ operator.cidade }}/{{ operator.uf }}</td>
                <td>
                  <div v-if="operator.email">{{ operator.email }}</div>
                  <div v-if="operator.telefone">Tel: {{ formatPhone(operator.ddd, operator.telefone) }}</div>
                </td>
                <td>
                  <button 
                    class="btn btn-sm btn-outline-primary"
                    @click="showDetails(operator)">
                    <i class="fas fa-info-circle"></i> Detalhes
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ResultsList',
    props: {
      results: {
        type: Array,
        required: true
      },
      loading: {
        type: Boolean,
        default: false
      },
      searchTerm: {
        type: String,
        default: ''
      },
      searched: {
        type: Boolean,
        default: false
      }
    },
    methods: {
      formatCNPJ(cnpj) {
        if (!cnpj) return '';
        // Formata CNPJ: XX.XXX.XXX/XXXX-XX
        return cnpj.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, '$1.$2.$3/$4-$5');
      },
      formatPhone(ddd, phone) {
        if (!ddd || !phone) return '';
        return `(${ddd}) ${phone}`;
      },
      showDetails(operator) {
        this.$emit('show-details', operator);
      }
    }
  };
  </script>
  
  <style scoped lang="scss">
  .results-container {
    margin-top: 1rem;
  }
  
  h3 {
    margin-bottom: 1rem;
    font-size: 1.2rem;
    color: #555;
  }
  </style>