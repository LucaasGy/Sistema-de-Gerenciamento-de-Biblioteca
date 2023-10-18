package erros.leitor;

/**
 * Exceção lançada quando um leitor não possuir reservas ativas.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorNaoPossuiReservas extends Exception{

    /**
     * Cria uma nova instância de LeitorNaoPossuiReservas com a mensagem de erro especificada.
     */

    public LeitorNaoPossuiReservas() {
        super("LEITOR NÃO POSSUI RESERVAS ATIVAS");
    }
}
