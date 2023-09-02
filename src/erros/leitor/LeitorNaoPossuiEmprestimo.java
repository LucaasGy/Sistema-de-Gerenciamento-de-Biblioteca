package erros.leitor;

public class LeitorNaoPossuiEmprestimo extends Exception{
    public LeitorNaoPossuiEmprestimo() {
        super("LEITOR NAO POSSUI EMPRESTIMO ATIVO");
    }
}
