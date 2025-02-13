import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunosDAO {
    private Connection conexao;

    public AlunosDAO() {
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

    // Método para inserir um aluno
    public void inserirAluno(Alunos aluno) {
        String sql = "INSERT INTO alunos (nome, matricula, turma) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setInt(2, aluno.getMatricula());
            stmt.setInt(3, aluno.getTurma());
            stmt.executeUpdate();
            System.out.println("Aluno inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aluno: " + e.getMessage());
        }
    }

    // Método para remover um aluno
    public void removerAluno(int matricula) {
        String sqlExcluirNotas = "DELETE FROM notas WHERE matricula_aluno = ?";
        String sqlExcluirAluno = "DELETE FROM alunos WHERE matricula = ?";

        try (PreparedStatement stmtNotas = conexao.prepareStatement(sqlExcluirNotas);
             PreparedStatement stmtAluno = conexao.prepareStatement(sqlExcluirAluno)) {

            // Exclui as notas do aluno
            stmtNotas.setInt(1, matricula);
            stmtNotas.executeUpdate();

            // Exclui o aluno
            stmtAluno.setInt(1, matricula);
            int linhasAfetadas = stmtAluno.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno removido com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com a matrícula " + matricula);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao remover aluno: " + e.getMessage());
        }
    }


    // Método para editar o nome de um aluno
    public void editarNome(String nome, int matricula) {
        String sql = "UPDATE alunos SET nome = ? WHERE matricula = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, matricula);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Nome do aluno atualizado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com a matrícula " + matricula);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao editar nome do aluno: " + e.getMessage());
        }
    }

    // Método para editar a turma de um aluno
    public void editarTurma(int turma, int matricula) {
        String sql = "UPDATE alunos SET turma = ? WHERE matricula = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, turma);
            stmt.setInt(2, matricula);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Turma do aluno atualizada com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com a matrícula " + matricula);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao editar turma do aluno: " + e.getMessage());
        }
    }

    // Método para listar todos os alunos
    public List<Alunos> listarAlunos() {
        List<Alunos> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                int matricula = rs.getInt("matricula");
                int turma = rs.getInt("turma");
                alunos.add(new Alunos(nome, matricula, turma));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }


}