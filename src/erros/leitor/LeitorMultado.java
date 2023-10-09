package erros.leitor;

/**
 * Exceção lançada quando um leitor estiver multado.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorMultado extends Exception{

    /**
     * Cria uma nova instância de LeitorMultado com a mensagem de erro especificada.
     *
     * @param message mensagem a ser exibida
     */

    public LeitorMultado(String message) {
        super(message);
    }
}
