# Nivelamento

1. Teste de Web Scraping
    1. Usando a dependencia do jsoup para fazer a referencia correta a url
       Criada uma função para baixar o arquivo e outra pra criar o arquivo zip, e no try catch principal é iniciada a variavel de link da pagina e o caminho onde serão salvos os arquivos
2. Teste de Transformação de Dados
   1. Usando as dependencias apache.pdfbox e opencsv, foi possivel capturar a primeira linha da tabela de rol de procedimentos, obs: o ROL de procedimentos não estava com uma qualidade boa de texto, então mesmo fazendo com java ou python, a leitura ficou prejudicada
3. Teste de banco de dados
   1. Feita a criação de base de dados a partir dos arquivos CRV em anexo, usado o comando COPY
4. Construida a rota da API back end e a rota da APi front end, criado um simples design com bootstrap para busca e consulta de dados
