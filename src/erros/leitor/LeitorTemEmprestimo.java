package erros.leitor;

/**
 * Exceção lançada quando um leitor estiver com um livro em mãos.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorTemEmprestimo extends Exception{

    /**
     * Cria uma nova instância de LeitorTemEmprestimo com a mensagem de erro especificada.
     */

    public LeitorTemEmprestimo() {
        super("LEITOR POSSUI EMPRESTIMO ATIVO");
    }
}
