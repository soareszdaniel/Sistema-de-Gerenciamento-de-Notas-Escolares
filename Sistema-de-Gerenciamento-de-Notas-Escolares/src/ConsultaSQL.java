import java.sql.*;

public class ConsultaSQL {
    private static Connection conexao;

    public static void estabeleregarConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/" +
                    "SistemadeGerenciamentodeNotasEscolares?serverTimezone=UTC", "root", "");
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e){
            System.out.println("Erro: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void consultarNome(){
        if (conexao == null){
            estabeleregarConexao();
        } else {
            try {
                Statement stmt = conexao.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nome FROM alunos");
                while (rs.next()){
                    System.out.println(rs.getString("nome"));
                }
            } catch (SQLException e){
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
    public void adicionarAluno(String nome, int matricula, int turma){
        if (conexao == null){
            estabeleregarConexao();
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
}



