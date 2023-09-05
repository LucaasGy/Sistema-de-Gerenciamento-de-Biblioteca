package erros.leitor;

/**
 * Exceção lançada quando um leitor não possuir um empréstimo ativo (impossibilita renovações).
 *
 * @author Lucas Gabriel.
 */

public class LeitorNaoPossuiEmprestimo extends Exception{

    /**
     * Cria uma nova instância de LeitorNaoPossuiEmprestimo com a mensagem de erro especificada.
     */

    public LeitorNaoPossuiEmprestimo() {
        super("LEITOR NAO POSSUI EMPRESTIMO ATIVO");
    }
}
