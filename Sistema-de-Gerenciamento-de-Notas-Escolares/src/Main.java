import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Instanciar DAOs
        AlunosDAO alunoDAO = new AlunosDAO();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
        NotasDAO notasDAO = new NotasDAO();

        // Testar cadastro de alunos
        Alunos aluno1 = new Alunos("João Silva", 12345, 101);
        alunoDAO.inserirAluno(aluno1);
        System.out.println("Aluno cadastrado: " + aluno1.getNome());

        // Testar cadastro de disciplinas
        Disciplinas disciplina1 = new Disciplinas("Matemática", 101, 60);
        disciplinaDAO.inserirDisciplina(disciplina1);
        System.out.println("Disciplina cadastrada: " + disciplina1.getNome());

        // Testar registro de notas
        Notas nota1 = new Notas(aluno1, disciplina1, 9.5);
        notasDAO.inserirNota(nota1);
        System.out.println("Nota registrada: " + nota1.getNota());

        // Testar listagem de alunos
        System.out.println("\nLista de Alunos:");
        List<Alunos> alunos = alunoDAO.listarAlunos();
        for (Alunos aluno : alunos) {
            System.out.println(aluno);
        }

        // Testar listagem de disciplinas
        System.out.println("\nLista de Disciplinas:");
        List<Disciplinas> disciplinas = disciplinaDAO.listarDisciplinas();
        for (Disciplinas disciplina : disciplinas) {
            System.out.println(disciplina);
        }

        // Testar listagem de notas
        System.out.println("\nLista de Notas:");
        List<Notas> notas = notasDAO.listarNotas();
        for (Notas nota : notas) {
            System.out.println(nota);
        }

        // Testar cálculo de média
        double media = notasDAO.calcularMediaAlunoDisciplina(12345, 101);
        System.out.println("\nMédia do aluno na disciplina: " + media);

        // Testar atualização de nota
        notasDAO.atualizarNota(12345, 101, 8.0);
        System.out.println("\nNota atualizada!");

        // Testar remoção de aluno
        alunoDAO.removerAluno(12345);
        System.out.println("\nAluno removido!");

        // Verificar se o aluno foi removido
        System.out.println("\nLista de Alunos após remoção:");
        alunos = alunoDAO.listarAlunos();
        for (Alunos aluno : alunos) {
            System.out.println(aluno);
        }
    }
}