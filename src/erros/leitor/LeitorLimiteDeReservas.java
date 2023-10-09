package erros.leitor;

/**
 * Exceção lançada quando um leitor possuir o máximo de reservas (3).
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorLimiteDeReservas extends Exception{

    /**
     * Cria uma nova instância de LeitorLimiteDeReservas com a mensagem de erro especificada.
     */

    public LeitorLimiteDeReservas() {
        super("LEITOR ATINGIU O LIMITE DE RESERVAS -> (3)");
    }
}
