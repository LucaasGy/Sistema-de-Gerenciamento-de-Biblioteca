package erros.leitor;

public class LeitorTemEmprestimoEmAtraso extends Exception{
    public LeitorTemEmprestimoEmAtraso() {
        super("LEITOR POSSUI UM EMPRESTIMO EM ATRASO");
    }
}
