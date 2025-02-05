import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        ConsultaSQL consulta = new ConsultaSQL();
        consulta.estabeleregarConexao();

        consulta.adicionarAluno("Alfedro", 30, 1250);
        consulta.consultarNome();


    }
}