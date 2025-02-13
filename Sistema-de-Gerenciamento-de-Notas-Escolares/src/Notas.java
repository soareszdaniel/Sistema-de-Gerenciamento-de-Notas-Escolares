public class Notas {
    private Alunos aluno;
    private Disciplinas disciplina;
    private double nota;

    // Construtor
    public Notas(Alunos aluno, Disciplinas disciplina, double nota) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
    }

    // Getters e Setters
    public Alunos getAluno() {
        return aluno;
    }

    public void setAluno(Alunos aluno) {
        this.aluno = aluno;
    }

    public Disciplinas getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplinas disciplina) {
        this.disciplina = disciplina;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "aluno=" + aluno.getNome() +
                ", disciplina=" + disciplina.getNome() +
                ", nota=" + nota +
                '}';
    }
}