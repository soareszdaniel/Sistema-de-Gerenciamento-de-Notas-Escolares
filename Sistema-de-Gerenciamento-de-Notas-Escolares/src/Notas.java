import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Notas extends ConsultaSQL {
    private Alunos aluno;
    private Disciplinas disciplina;


    public static void consultarNotas(Alunos aluno){
        try {
            if (conexao == null) {
                estabelecerConexao();
            } else {
                PreparedStatement stmt = conexao.prepareStatement("select Alunos.nome, disciplinas.nome, nota from alunos " +
                        "join notas on alunos.matricula = notas.matricula " +
                        "join disciplinas on disciplinas.codigo = notas.codigo " +
                        "where Alunos.nome = ?");
                stmt.setString(1, aluno.getNome());

                //while (rs.next()) {
                    //System.out.println(rs.getString("alunos.nome"));
                  //  System.out.println(rs.getString("disciplinas.nome"));
                //    System.out.println(rs.getInt("nota"));
                //}
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
