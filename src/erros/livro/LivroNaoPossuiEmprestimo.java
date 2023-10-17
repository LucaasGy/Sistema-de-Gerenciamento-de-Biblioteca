package erros.livro;

/**
 * Exceção lançada quando um livro não tiver um empréstimo ativo e um bibliotecario tentar devolve-lo.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LivroNaoPossuiEmprestimo extends Exception{

    /**
     * Cria uma nova instância de LivroNaoPossuiEmprestimo com a mensagem de erro especificada.
     */

    public LivroNaoPossuiEmprestimo() {
        super("LIVRO NÃO POSSUI EMPRÉSTIMO ATIVO");
    }
}
