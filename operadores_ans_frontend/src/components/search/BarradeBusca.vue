<template>
  <div class="search-bar">
    <div class="input-group mb-3">
      <span class="input-group-text">
        <i class="fas fa-search"></i>
      </span>
      <input
        type="text"
        class="form-control"
        placeholder="Buscar operadoras (mínimo 3 caracteres)"
        v-model="searchQuery"
        @input="onInput"
        @keyup.enter="search"
      />
      <button 
        class="btn btn-primary" 
        type="button" 
        @click="search"
        :disabled="!isValidSearch || isSearching">
        {{ isSearching ? 'Buscando...' : 'Buscar' }}
      </button>
    </div>
    <div v-if="errorMessage" class="alert alert-danger">
      {{ errorMessage }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'SearchBar',
  data() {
    return {
      searchQuery: '',
      errorMessage: '',
      isSearching: false,
      timeout: null
    };
  },
  computed: {
    isValidSearch() {
      return this.searchQuery.length >= 3;
    }
  },
  methods: {
    onInput() {
      this.errorMessage = '';
      
      // Debounce para evitar muitas chamadas
      clearTimeout(this.timeout);
      if (this.isValidSearch) {
        this.timeout = setTimeout(() => {
          this.search();
        }, 500);
      }
    },
    search() {
      if (!this.isValidSearch) {
        this.errorMessage = 'O termo de busca deve ter pelo menos 3 caracteres';
        return;
      }
      
      this.isSearching = true;
      this.$emit('search', this.searchQuery);
    },
    setSearching(status) {
      this.isSearching = status;
    }
  }
};
</script>

<style scoped lang="scss">
.search-bar {
  margin-bottom: 1.5rem;
}
</style>

