import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Alunos.estabelecerConexao();
        Alunos a1 = new Alunos("Tito", 5, 320);
        a1.listarAlunos();

    }
}