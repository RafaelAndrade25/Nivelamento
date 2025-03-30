<template>
    <div class="container mt-4">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header bg-primary text-white">
              <h2 class="mb-0">Busca de Operadoras ANS</h2>
            </div>
            <div class="card-body">
              <SearchBar 
                @search="performSearch" 
                ref="searchBar" 
              />
              
              <ResultsList 
                :results="searchResults" 
                :loading="isLoading"
                :search-term="currentSearchTerm"
                :searched="hasSearched"
                @show-details="showOperatorDetails"
              />
            </div>
          </div>
        </div>
      </div>
      
      <OperatorDetails 
        :operator="selectedOperator" 
      />
    </div>
  </template>
  
  <script>
  import SearchBar from '@/components/search/BarradeBusca.vue';
  import ResultsList from '@/components/search/ListadeResultados.vue';
  import OperatorDetails from '@/components/search/DetalhesOperadores.vue';
  import api from '@/services/api';
  import { Modal } from 'bootstrap';
  
  export default {
    name: 'OperatorSearch',
    components: {
      SearchBar,
      ResultsList,
      OperatorDetails
    },
    data() {
      return {
        searchResults: [],
        isLoading: false,
        currentSearchTerm: '',
        hasSearched: false,
        selectedOperator: null,
        detailsModal: null
      };
    },
    mounted() {
      // Inicializa o modal do Bootstrap quando o componente é montado
      this.detailsModal = new Modal(document.getElementById('operatorDetailsModal'));
    },
    methods: {
      performSearch(searchTerm) {
        this.isLoading = true;
        this.currentSearchTerm = searchTerm;
        this.hasSearched = true;
        
        api.searchOperators(searchTerm)
          .then(response => {
            this.searchResults = response.data.results;
          })
          .catch(error => {
            console.error('Erro ao buscar operadoras:', error);
            this.searchResults = [];
          })
          .finally(() => {
            this.isLoading = false;
            if (this.$refs.searchBar) {
              this.$refs.searchBar.setSearching(false);
            }
          });
      },
      showOperatorDetails(operator) {
        this.selectedOperator = operator;
        this.detailsModal.show();
      }
    }
  };
  </script>