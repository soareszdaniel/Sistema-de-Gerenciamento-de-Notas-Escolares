import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotasDAO {
    private Connection conexao;

    public NotasDAO() {
        estabelecerConexao();
    }

    private void estabelecerConexao() {
        try {
            String url = "jdbc:mysql://localhost:3306/sistemadegerenciamentodenotasescolares";
            String usuario = "root";
            String senha = "";
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Método para inserir uma nota
    public void inserirNota(Notas nota) {
        String sql = "INSERT INTO notas (matricula_aluno, codigo_disciplina, nota) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, nota.getAluno().getMatricula());
            stmt.setInt(2, nota.getDisciplina().getCodigo());
            stmt.setDouble(3, nota.getNota());
            stmt.executeUpdate();
            System.out.println("Nota inserida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir nota: " + e.getMessage());
        }
    }

    // Método para atualizar uma nota
    public void atualizarNota(int matricula, int codigoDisciplina, double novaNota) {
        String sql = "UPDATE notas SET nota = ? WHERE matricula_aluno = ? AND codigo_disciplina = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDouble(1, novaNota);
            stmt.setInt(2, matricula);
            stmt.setInt(3, codigoDisciplina);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Nota atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma nota encontrada para os critérios fornecidos.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar nota: " + e.getMessage());
        }
    }

    // Método para listar todas as notas
    public List<Notas> listarNotas() {
        List<Notas> notas = new ArrayList<>();
        String sql = "SELECT a.nome AS nome_aluno, d.nome AS nome_disciplina, n.nota " +
                "FROM notas n " +
                "INNER JOIN alunos a ON n.matricula_aluno = a.matricula " +
                "INNER JOIN disciplinas d ON n.codigo_disciplina = d.codigo";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Alunos aluno = new Alunos(rs.getString("nome_aluno"), 0, 0); // Matrícula e turma fictícias
                Disciplinas disciplina = new Disciplinas(rs.getString("nome_disciplina"), 0, 0); // Código e carga fictícios
                notas.add(new Notas(aluno, disciplina, rs.getDouble("nota")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar notas: " + e.getMessage());
        }
        return notas;
    }

    // Método para calcular a média de um aluno em uma disciplina
    public double calcularMediaAlunoDisciplina(int matricula, int codigoDisciplina) {
        String sql = "SELECT AVG(nota) AS media FROM notas WHERE matricula_aluno = ? AND codigo_disciplina = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            stmt.setInt(2, codigoDisciplina);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("media");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular média: " + e.getMessage());
        }
        return 0.0;
    }
}
