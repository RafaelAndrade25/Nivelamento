<!-- src/components/search/OperatorDetails.vue -->
<template>
    <div class="modal fade" id="operatorDetailsModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Detalhes da Operadora</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
          </div>
          <div class="modal-body" v-if="operator">
            <div class="card mb-3">
              <div class="card-header bg-primary text-white">
                Informações Gerais
              </div>
              <div class="card-body">
                <h3 class="card-title">{{ operator.razao_social }}</h3>
                <h6 class="card-subtitle text-muted" v-if="operator.nome_fantasia">
                  {{ operator.nome_fantasia }}
                </h6>
                
                <div class="row mt-4">
                  <div class="col-md-6">
                    <p><strong>Registro ANS:</strong> {{ operator.registro_ans }}</p>
                    <p><strong>CNPJ:</strong> {{ formatCNPJ(operator.cnpj) }}</p>
                    <p><strong>Modalidade:</strong> {{ operator.modalidade }}</p>
                    <p><strong>Data de Registro:</strong> {{ operator.data_registro }}</p>
                  </div>
                  <div class="col-md-6">
                    <p><strong>Representante:</strong> {{ operator.representante }}</p>
                    <p><strong>Cargo:</strong> {{ operator.cargo_representante }}</p>
                    <p><strong>Email:</strong> {{ operator.email }}</p>
                    <p><strong>Telefone:</strong> {{ formatPhone(operator.ddd, operator.telefone) }}</p>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="card">
              <div class="card-header bg-secondary text-white">
                Endereço
              </div>
              <div class="card-body">
                <p>
                  {{ operator.logradouro }}, {{ operator.numero }}
                  <span v-if="operator.complemento"> - {{ operator.complemento }}</span>
                </p>
                <p>{{ operator.bairro }}</p>
                <p>{{ operator.cidade }} - {{ operator.uf }}</p>
                <p>CEP: {{ formatCEP(operator.cep) }}</p>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'OperatorDetails',
    props: {
      operator: {
        type: Object,
        required: true
      }
    },
    methods: {
      formatCNPJ(cnpj) {
        if (!cnpj) return '';
        return cnpj.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, '$1.$2.$3/$4-$5');
      },
      formatPhone(ddd, phone) {
        if (!ddd || !phone) return '';
        return `(${ddd}) ${phone}`;
      },
      formatCEP(cep) {
        if (!cep) return '';
        return cep.replace(/^(\d{5})(\d{3})$/, '$1-$2');
      }
    }
  };
  </script>