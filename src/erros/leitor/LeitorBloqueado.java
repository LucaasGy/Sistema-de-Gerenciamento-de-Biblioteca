package erros.leitor;

/**
 * Exceção lançada quando um leitor estiver bloqueado.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorBloqueado extends Exception{

    /**
     * Cria uma nova instância de LeitorBloqueado com a mensagem de erro especificada.
     */

    public LeitorBloqueado() {
        super("LEITOR ESTÁ BLOQUEADO");
    }
}
