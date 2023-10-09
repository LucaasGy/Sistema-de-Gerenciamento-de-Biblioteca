package erros.livro;

/**
 * Exceção lançada quando um livro não estiver disponível.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LivroNaoDisponivel extends Exception{

    /**
     * Cria uma nova instância de LivroNaoDisponivel com a mensagem de erro especificada.
     */

    public LivroNaoDisponivel() {
        super("LIVRO NÃO ESTÁ DISPONÍVEL PARA USO");
    }
}
