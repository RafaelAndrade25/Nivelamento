<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href="#">
          <i class="fas fa-hospital me-2"></i>
          ANS Operadoras
        </a>
      </div>
    </nav>
    
    <router-view/>
    
    <footer class="footer mt-5 py-3 bg-light">
      <div class="container text-center">
        <span class="text-muted">Sistema de Busca de Operadoras ANS © 2025</span>
      </div>
    </footer>
  </div>
</template>

<style lang="scss">
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.footer {
  margin-top: auto;
}
</style>

