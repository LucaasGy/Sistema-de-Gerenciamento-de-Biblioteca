package erros.leitor;

/**
 * Exceção lançada quando um leitor não possuir um empréstimo ativo (impossibilita renovações).
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorNaoPossuiEmprestimo extends Exception{

    /**
     * Cria uma nova instância de LeitorNaoPossuiEmprestimo com a mensagem de erro especificada.
     */

    public LeitorNaoPossuiEmprestimo() {
        super("LEITOR NÃO POSSUI EMPRÉSTIMO ATIVO");
    }
}
