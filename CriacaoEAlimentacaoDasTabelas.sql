-- Criação da tabela para os Dados Cadastrais das Operadoras Ativas
CREATE TABLE IF NOT EXISTS operadoras_ativas (
    registro_ans VARCHAR(20),
    cnpj VARCHAR(20),
    razao_social VARCHAR(255),
    nome_fantasia VARCHAR(255),
    modalidade VARCHAR(100),
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf CHAR(2),
    cep VARCHAR(10),
    ddd VARCHAR(5),
    telefone VARCHAR(20),
    fax VARCHAR(20),
    email VARCHAR(100),
    representante VARCHAR(255),
    cargo_representante VARCHAR(100),
    registro VARCHAR(20),
    data_atualizacao DATE
);

-- Criação da tabela para as Demonstrações Contábeis
CREATE TABLE IF NOT EXISTS demonstracoes_contabeis (
	data_reg VARCHAR(20),
    registro_ans VARCHAR(20),
    cd_conta_contabil VARCHAR(20),
    descricao VARCHAR(255),
    vl_saldo_inicial VARCHAR(20),
    vl_saldo_final VARCHAR(20)
);

-- Query para importar dados dos Dados Cadastrais das Operadoras Ativas
-- Ajuste o caminho do arquivo conforme necessário
COPY operadoras_ativas 
FROM 'C:\estudo\OperacoesAtivas\Relatorio_cadop.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';

select * from operadoras_ativas

-- Exemplo para um arquivo específico:
COPY demonstracoes_contabeis 
FROM 'C:\estudo\DemonstrativosContabeis\1T2023.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';

COPY demonstracoes_contabeis 
FROM 'C:\estudo\DemonstrativosContabeis\1T2024.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';

COPY demonstracoes_contabeis 
FROM 'C:\estudo\DemonstrativosContabeis\2T2023.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';

COPY demonstracoes_contabeis 
FROM 'C:\estudo\DemonstrativosContabeis\2T2024.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';

COPY demonstracoes_contabeis 
FROM 'C:\estudo\DemonstrativosContabeis\3T2023.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';

COPY demonstracoes_contabeis 
FROM 'C:\estudo\DemonstrativosContabeis\3T2024.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';

COPY demonstracoes_contabeis 
FROM 'C:\estudo\DemonstrativosContabeis\4T2023.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';

COPY demonstracoes_contabeis 
FROM 'C:\estudo\DemonstrativosContabeis\4T2024.csv' 
DELIMITER ';' 
CSV HEADER
ENCODING 'LATIN1';



select * from demonstracoes_contabeis

--Query 1
SELECT 
    dc.registro_ans,
    op.razao_social,
    op.modalidade,
    ABS(CAST(REPLACE(REPLACE(dc.vl_saldo_final, '.', ''), ',', '.') AS DECIMAL(15,2))) AS valor_despesa_ultimo_trimestre
FROM 
    demonstracoes_contabeis dc
JOIN 
    operadoras_ativas op ON dc.registro_ans = op.registro_ans
WHERE 
        dc.descricao LIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
        OR dc.registro_ans = '411112' 
ORDER BY 
    valor_despesa_ultimo_trimestre DESC
LIMIT 10;

--Query 2
SELECT 
    da.registro_ans,
    op.razao_social,
    op.modalidade,
    da.vl_saldo_final,
    (
        SELECT dc.descricao
        FROM demonstracoes_contabeis dc
        WHERE dc.registro_ans = da.registro_ans
        AND dc.descricao LIKE '%EVENTOS%SINISTROS%'
        LIMIT 1
    ) AS exemplo_descricao_conta
FROM 
    demonstracoes_contabeis da
JOIN 
    operadoras_ativas op ON da.registro_ans = op.registro_ans
ORDER BY 
    da.vl_saldo_final DESC
LIMIT 10;




