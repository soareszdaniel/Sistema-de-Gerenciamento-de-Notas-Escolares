import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class WebServer {
    private static Connection conexaoDB;

    public static void main(String[] args) throws IOException, SQLException {
        // Configurar banco de dados
        conectarBanco();

        // Configurar servidor web
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        
        // Handler para arquivos estáticos
        server.createContext("/", exchange -> {
            adicionarCorsHeaders(exchange);
            
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            String path = exchange.getRequestURI().getPath();
            path = path.equals("/") ? "/index.html" : path;

            try {
                byte[] bytes = Files.readAllBytes(Paths.get("web", path));
                String contentType = getContentType(path);
                exchange.getResponseHeaders().add("Content-Type", contentType);
                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);
            } catch (IOException e) {
                enviarResposta(exchange, 404, "Arquivo não encontrado");
            }
            exchange.close();
        });

        // API para cadastrar alunos
        server.createContext("/api/alunos", exchange -> {
            adicionarCorsHeaders(exchange);
            
            try {
                if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    String corpo = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    Map<String, String> dados = parseFormData(corpo);
                    
                    if (!validarDados(dados)) {
                        enviarResposta(exchange, 400, "Dados inválidos");
                        return;
                    }
                    
                    inserirAluno(
                        dados.get("nome"),
                        Integer.parseInt(dados.get("matricula")),
                        Integer.parseInt(dados.get("turma"))
                    );
                    
                    enviarResposta(exchange, 200, "Aluno cadastrado!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                enviarResposta(exchange, 500, "Erro interno");
            }
            exchange.close();
        });

        server.start();
        System.out.println("Servidor rodando em http://localhost:8081");
    }

    // ================ Métodos Auxiliares ================
    private static void conectarBanco() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/escola";
        String user = "root";
        String pass = "sua_senha";
        conexaoDB = DriverManager.getConnection(url, user, pass);
        System.out.println("Conectado ao MySQL!");
    }

    private static void inserirAluno(String nome, int matricula, int turma) throws SQLException {
        String sql = "INSERT INTO alunos (nome, matricula, turma) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexaoDB.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, matricula);
            stmt.setInt(3, turma);
            stmt.executeUpdate();
        }
    }

    private static Map<String, String> parseFormData(String data) throws IOException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = data.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
            String value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
            map.put(key, value);
        }
        return map;
    }

    private static boolean validarDados(Map<String, String> dados) {
        return dados.containsKey("nome") 
            && dados.containsKey("matricula") 
            && dados.containsKey("turma");
    }

    private static String getContentType(String path) {
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        return "text/html";
    }

    private static void adicionarCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    private static void enviarResposta(HttpExchange exchange, int code, String response) throws IOException {
        exchange.sendResponseHeaders(code, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
    }
}