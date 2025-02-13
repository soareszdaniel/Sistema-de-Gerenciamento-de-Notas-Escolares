import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebServer {
    public static void main(String[] args) throws IOException {
        // Cria o servidor na porta 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/", exchange -> {
            Path path = Paths.get("web", "index.html");
            byte[] bytes = Files.readAllBytes(path);
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
            String response = "Aluno cadastrado com sucesso!";
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        server.createContext("/cadastro.html", exchange -> {
            Path path = Paths.get("web", "cadastro.html");
            byte[] bytes = Files.readAllBytes(path);
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
            System.out.println("Requisição recebida: " + exchange.getRequestURI());
        });

        server.createContext("/listar.html", exchange -> {
            Path path = Paths.get("web", "listar.html");
            byte[] bytes = Files.readAllBytes(path);
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        });

        // Inicia o servidor
        server.start();
        System.out.println("Servidor rodando em http://localhost:8081");

        server.createContext("/api/alunos", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Lê os dados do formulário (exemplo simplificado)
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                String nome = requestBody.split("&")[0].split("=")[1];
                int matricula = Integer.parseInt(requestBody.split("&")[1].split("=")[1]);
                int turma = Integer.parseInt(requestBody.split("&")[2].split("=")[1]);

                // Insere no banco de dados (use sua classe AlunoDAO)
                AlunosDAO alunoDAO = new AlunosDAO();
                alunoDAO.inserirAluno(new Alunos(nome, matricula, turma));

                // Resposta
                String response = "Aluno cadastrado com sucesso!";
                exchange.getResponseHeaders().add("Content-Type", "text/plain");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());
            }
            exchange.close();
        });

    }

}
