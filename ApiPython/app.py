from flask import Flask, request, jsonify
from flask_cors import CORS
import pandas as pd
import re

app = Flask(__name__)
CORS(app)

def carrega_csv():
    try:
        df = pd.read_csv('Relatorio_cadop.csv', sep=';', encoding='utf-8')
        df.columns = [
            'registro_ans', 'cnpj', 'razao_social', 'nome_fantasia',
            'modalidade', 'logradouro', 'numero', 'complemento',
            'bairro', 'cidade', 'uf', 'cep', 'ddd', 'telefone',
            'fax', 'email', 'representante', 'cargo_representante', 'data_registro'
        ]
        return df
    except Exception as e:
        print(f"Erro ao carregar dados: {e}")
        return pd.DataFrame()

def define_relevancia_registro(row, search_term):
    relevancia = 0
    search_regex = re.compile(search_term, re.IGNORECASE)

    fields = {
        'razao_social': 10,
        'nome_fantasia': 8,
        'registro_ans': 7,
        'cnpj': 6,
        'modalidade': 5,
        'cidade': 4,
        'uf': 3,
        'email': 2
    }

    for field, i in fields.items():
        if isinstance(row[field], str):
            if search_regex.search(row[field]):
                relevancia += i

                if row[field].lower().startswith(search_term.lower()):
                    relevancia += i * 0.5
    return relevancia

@app.route('/')
def procura_Operadores():
    search_term = request.args.get('q', '')

    if not search_term or len(search_term) < 3:
        return jsonify({"error": "O termo de busca deve ter pelo menos 3 caracteres"}, 400)
    df = carrega_csv()

    if df.empty:
        return jsonify({"error": "Nenhum registro encontrado!"}), 500

    df['relevancia'] = df.apply(lambda row: define_relevancia_registro(row, search_term), axis=1)
    resultado = df[df['relevancia'] > 0].sort_values('relevancia', ascending=False)
    limite_resultados = resultado.head(20)
    response = limite_resultados.drop(columns=['relevancia']).to_dict('records')
    return jsonify({
        "search_term": search_term,
        "count": len(response),
        "results": response
    })

if __name__ == '__main__':
    app.run(debug=True, port=5000)
