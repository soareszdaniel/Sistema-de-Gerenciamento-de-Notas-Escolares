import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {
    private Connection conexao;

    public DisciplinaDAO() {
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

    // Método para inserir uma disciplina
    public void inserirDisciplina(Disciplinas disciplina) {
        String sql = "INSERT INTO disciplinas (nome, codigo, carga_horaria) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNome());
            stmt.setInt(2, disciplina.getCodigo());
            stmt.setInt(3, disciplina.getCargaHoraria());
            stmt.executeUpdate();
            System.out.println("Disciplina inserida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir disciplina: " + e.getMessage());
        }
    }

    // Método para remover uma disciplina
    public void removerDisciplina(int codigo) {
        String sql = "DELETE FROM disciplinas WHERE codigo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Disciplina removida com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com o código " + codigo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover disciplina: " + e.getMessage());
        }
    }

    // Método para editar o nome de uma disciplina
    public void editarNome(String nome, int codigo) {
        String sql = "UPDATE disciplinas SET nome = ? WHERE codigo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, codigo);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Nome da disciplina atualizado com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com o código " + codigo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao editar nome da disciplina: " + e.getMessage());
        }
    }

    // Método para editar a carga horária de uma disciplina
    public void editarCargaHoraria(int cargaHoraria, int codigo) {
        String sql = "UPDATE disciplinas SET carga_horaria = ? WHERE codigo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, cargaHoraria);
            stmt.setInt(2, codigo);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Carga horária da disciplina atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma disciplina encontrada com o código " + codigo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao editar carga horária da disciplina: " + e.getMessage());
        }
    }

    // Método para listar todas as disciplinas
    public List<Disciplinas> listarDisciplinas() {
        List<Disciplinas> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM disciplinas";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nome = rs.getString("nome");
                int codigo = rs.getInt("codigo");
                int cargaHoraria = rs.getInt("carga_horaria");
                disciplinas.add(new Disciplinas(nome, codigo, cargaHoraria));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar disciplinas: " + e.getMessage());
        }
        return disciplinas;
    }
}
