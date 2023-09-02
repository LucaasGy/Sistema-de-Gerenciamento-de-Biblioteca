package erros.leitor;

public class LeitorTemEmprestimo extends Exception{
    public LeitorTemEmprestimo() {
        super("LEITOR POSSUI EMPRESTIMO ATIVO");
    }
}
