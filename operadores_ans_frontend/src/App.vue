<template>
  <div id="app">
    <nav>
      <div class="nav-container">
        <h1>Sistema de Operadoras ANS</h1>
      </div>
    </nav>
    <router-view/>
  </div>
</template>

<style lang="scss">
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  background-color: #f5f5f5;
}

nav {
  background-color: #2c3e50;
  color: white;
  padding: 15px 0;
  
  .nav-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
    
    h1 {
      font-size: 24px;
      font-weight: normal;
    }
  }
}
</style>
