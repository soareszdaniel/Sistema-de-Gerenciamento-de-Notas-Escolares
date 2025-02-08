import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Disciplinas extends ConsultaSQL {
    private String nome;
    private int codigo;
    private int cargaHoraria;

    public Disciplinas(String nome, int codigo, int cargaHoraria) {
        try {
            if (conexao == null){
                estabelecerConexao();
            } else {
                PreparedStatement stmt = conexao.prepareStatement("INSERT INTO disciplinas (nome, codigo, cargaHoraria) VALUES (?, ?, ?)");
                stmt.setString(1, nome);
                stmt.setInt(2, codigo);
                stmt.setInt(3, cargaHoraria);
                stmt.executeUpdate();
            }
        } catch (SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
