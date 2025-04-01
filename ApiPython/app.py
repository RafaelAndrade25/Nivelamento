from flask import Flask, request, jsonify
from flask_cors import CORS
import pandas as pd
import re

app = Flask(__name__)
CORS(app)  # Habilita CORS para todas as rotas


# Carregar o CSV com as operadoras
def carregar_dados():
    try:
        # Carregando o arquivo CSV
        df = pd.read_csv('data/Relatorio_cadop.csv', encoding='latin1', sep=';')
        # Removendo valores nulos
        df = df.fillna('')
        return df
    except Exception as e:
        print(f"Erro ao carregar dados: {str(e)}")
        return pd.DataFrame()


# Função para calcular a relevância de um termo de busca
def calcular_relevancia(row, termo_busca):
    relevancia = 0

    valores = {col: str(val).lower() for col, val in row.items()}
    termo_busca = termo_busca.lower()

    for val in valores.values():
        if termo_busca == val:
            relevancia += 10

    for val in valores.values():
        if termo_busca in val:
            relevancia += 5

    palavras_busca = termo_busca.split()
    for palavra in palavras_busca:
        for val in valores.values():
            if palavra in val.split():
                relevancia += 3

    return relevancia


@app.route('/api/operadoras', methods=['GET'])
def buscar_operadoras():
    # Obtém o termo de busca da query string
    termo_busca = request.args.get('q', '')

    if not termo_busca:
        return jsonify({"erro": "Termo de busca não fornecido"}), 400

    # Carrega os dados
    df = carregar_dados()

    if df.empty:
        return jsonify({"erro": "Não foi possível carregar os dados"}), 500

    # Calcula relevância para cada registro
    df['relevancia'] = df.apply(lambda row: calcular_relevancia(row, termo_busca), axis=1)

    # Filtra resultados com alguma relevância e ordena
    resultados = df[df['relevancia'] > 0].sort_values('relevancia', ascending=False)

    # Limita a 10 resultados mais relevantes
    resultados = resultados.head(10)

    # Converte para formato JSON
    return jsonify({
        "termo_busca": termo_busca,
        "total_resultados": len(resultados),
        "resultados": resultados.drop('relevancia', axis=1).to_dict(orient='records')
    })


if __name__ == '__main__':
    app.run(debug=True, port=5000)