import java.sql.*;

public class Alunos extends ConsultaSQL{
    private String nome;
    private int matricula;
    private int turma;

    public Alunos(String nome, int matricula, int turma) {
        if (conexao == null){
            estabelecerConexao();
        } else {
            try {
                PreparedStatement stmt = conexao.prepareStatement("INSERT INTO alunos (nome, matricula, turma) VALUES (?, ?, ?)");
                stmt.setString(1, nome);
                stmt.setInt(2, matricula);
                stmt.setInt(3, turma);
                stmt.executeUpdate();
            } catch (SQLException e){
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
    public void removerAluno(int matricula){
        if (conexao == null){
            estabelecerConexao();
        } else {
            try {
                PreparedStatement stmt = conexao.prepareStatement("DELETE FROM alunos WHERE matricula = ?");
                stmt.setInt(1, matricula);
                stmt.executeUpdate();
            } catch (SQLException ignored){}
        }
    }
    public void editarNome(String nome, int matricula){
        if (conexao == null){
            estabelecerConexao();
        } else {
            try {
                PreparedStatement stmt = conexao.prepareStatement("UPDATE alunos SET nome = ? WHERE matricula = ?");
                stmt.setString(1, nome);
                stmt.setInt(2, matricula);
                stmt.executeUpdate();
            } catch (SQLException e){
                System.out.println("Erro: " + e.getMessage());
            }
        }

    }
    public void editarTurma(int turma, int matricula){
        try {
            if (conexao == null){
                estabelecerConexao();
            } else {
                PreparedStatement stmt = conexao.prepareStatement("UPDATE alunos SET turma = ? WHERE matricula = ?");
                stmt.setInt(1, turma);
                stmt.setInt(2, matricula);
                stmt.executeUpdate();
            }
        } catch (SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }

    }
    public void listarAlunos(){
        if (conexao == null){
            estabelecerConexao();
        } else {
            try (Statement stmt = conexao.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM alunos")) {
                while (rs.next()){
                    System.out.print(rs.getString("nome"));
                    this.nome = rs.getString("nome");
                    System.out.print(" ");
                    System.out.print(rs.getInt("matricula"));
                    this.matricula = rs.getInt("matricula");
                    System.out.print(" ");
                    System.out.print(rs.getInt("turma"));
                    this.turma = rs.getInt("turma");
                    System.out.println();
                }
                Alunos a1 = new Alunos(rs.getString("nome"), rs.getInt("matricula"), rs.getInt("turma"));
            } catch (SQLException e){
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    public String getNome() {
        listarAlunos();
        return nome;
    }
}
