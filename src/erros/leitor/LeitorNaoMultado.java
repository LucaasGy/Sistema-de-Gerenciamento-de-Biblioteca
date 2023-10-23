package erros.leitor;

/**
 * Exceção lançada quando um leitor não estiver multado.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorNaoMultado extends Exception{

    /**
     * Cria uma nova instância de LeitorNaoMultado com a mensagem de erro especificada.
     *
     * @param message mensagem a ser exibida
     */

    public LeitorNaoMultado(String message) {
        super(message);
    }
}
