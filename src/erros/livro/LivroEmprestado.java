package erros.livro;

/**
 * Exceção lançada quando um livro estiver emprestado.
 *
 * @author Lucas Gabriel.
 */

public class LivroEmprestado extends Exception{

    /**
     * Cria uma nova instância de LivroEmprestado com a mensagem de erro especificada.
     */

    public LivroEmprestado() {
        super("LIVRO POSSUI EMPRESTIMO ATIVO");
    }
}
