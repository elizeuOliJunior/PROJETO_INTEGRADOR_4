#http://127.0.0.1:5000/

from flask import Flask, render_template, request, jsonify
import mysql.connector
import os

app = Flask(__name__)

# Configurações do banco de dados MySQL
db_config = {
    "host": "localhost",
    "user": "root",
    "password": "Eli210504.",
    "database": "PIDataBase"
}

# Obtenha o caminho do diretório do script atual
current_dir = os.path.dirname(os.path.abspath(__file__))

# Configure o caminho completo para a pasta de templates
template_folder = os.path.join(current_dir, 'templates')
app = Flask(__name__, template_folder=template_folder)


# Rota para receber dados do frontend
@app.route('/submit', methods=['POST'])
def submit():
    try:
        data = request.json

        # Extrair dados do JSON
        personal_info = data.get('personalInfo', {})
        address = data.get('address', {})
        appointment = data.get('appointment', {})

        # Lógica para salvar no banco de dados MySQL
        save_data_to_mysql(personal_info, address, appointment)

        return jsonify({"message": "Dados recebidos com sucesso!"})

    except Exception as e:
        print("Erro:", str(e))
        return jsonify({"error": "Ocorreu um erro ao processar a requisição"}), 500

def save_data_to_mysql(personal_info, address, appointment):
    try:
        connection = mysql.connector.connect(**db_config)
        cursor = connection.cursor()

        # Lógica para preparar e executar a consulta SQL
        sql = "INSERT INTO PersonalInfo (name, cpf, phone, email) VALUES (%s, %s, %s, %s)"
        values = (
            personal_info.get('name', ''),
            personal_info.get('cpf', ''),
            address.get('street', ''),
            appointment.get('specialty', '')
        )

        cursor.execute(sql, values)
        connection.commit()

    except mysql.connector.Error as err:
        print("Erro ao conectar ao MySQL:", err)

    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()

# Rota para a página inicial
@app.route('/')
def index():
    return render_template('agendamento.html')


if __name__ == '__main__':
    app.run(debug=True, port=5000)
