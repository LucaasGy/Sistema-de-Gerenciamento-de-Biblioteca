package erros.livro;

/**
 * Exceção lançada quando um livro estiver emprestado.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LivroEmprestado extends Exception{

    /**
     * Cria uma nova instância de LivroEmprestado com a mensagem de erro especificada.
     */

    public LivroEmprestado() {
        super("LIVRO POSSUI EMPRÉSTIMO ATIVO");
    }
}
