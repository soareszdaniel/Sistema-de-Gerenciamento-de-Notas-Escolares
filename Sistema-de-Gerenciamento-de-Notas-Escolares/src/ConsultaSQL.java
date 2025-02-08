import java.sql.*;

public abstract class ConsultaSQL {
    protected static Connection conexao;

    public static void estabelecerConexao() {
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


}



