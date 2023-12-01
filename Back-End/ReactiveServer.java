import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReactiveServer {

    public static void main(String[] args) {
        int port = 8000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor reativo iniciado na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nova conexão recebida.");

                // Criar uma nova thread para lidar com a conexão
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream()) {

            // Lógica de manipulação da requisição aqui
            // Leitura e processamento dos dados do frontend

            // Exemplo: Lendo os dados enviados pelo frontend
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            String requestData = new String(buffer, 0, bytesRead);
            System.out.println("Dados recebidos do frontend: " + requestData);

            // Lógica de processamento (por exemplo, salvar no banco de dados MySQL)
            saveDataToMySQL(requestData);

            // Enviar resposta ao frontend
            String response = "Dados recebidos com sucesso!";
            output.write(response.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveDataToMySQL(String requestData) {
        try {
            // Extrair dados do JSON
            String[] dataArray = requestData.split("&");
            
            String name = null;
            String cpf = null;
            String phone = null;
            String email = null;
            
            // Iterar sobre os dados para extrair informações específicas
            for (String data : dataArray) {
                String[] keyValue = data.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];

                    // Verificar a chave e atribuir o valor correspondente
                    switch (key) {
                        case "name":
                            name = value;
                            break;
                        case "cpf":
                            cpf = value;
                            break;
                        case "phone":
                            phone = value;
                            break;
                        case "email":
                            email = value;
                            break;

                    }
                }
            }

            // Conectar ao banco de dados MySQL
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PIDataBase", "root", "Eli210504")) {

                // Lógica para preparar e executar a consulta SQL
                String sql = "INSERT INTO PersonalInfo (name, cpf, phone, email) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, cpf);
                    preparedStatement.setString(3, phone);
                    preparedStatement.setString(4, email);
                    // Adicione mais campos conforme necessário

                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
