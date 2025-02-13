public class Alunos {
    private String nome;
    private int matricula;
    private int turma;

    // Construtor
    public Alunos(String nome, int matricula, int turma) {
        this.nome = nome;
        this.matricula = matricula;
        this.turma = turma;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getTurma() {
        return turma;
    }

    public void setTurma(int turma) {
        this.turma = turma;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", matricula=" + matricula +
                ", turma=" + turma +
                '}';
    }
}