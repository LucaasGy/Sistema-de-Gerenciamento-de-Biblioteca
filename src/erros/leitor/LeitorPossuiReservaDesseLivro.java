package erros.leitor;

/**
 * Exceção lançada quando um leitor estiver tentando reservar um livro que ele já reservou.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorPossuiReservaDesseLivro extends Exception{

    /**
     * Cria uma nova instância de LeitorPossuiReservaDesseLivro com a mensagem de erro especificada.
     */

    public LeitorPossuiReservaDesseLivro() {
        super("LEITOR JÁ POSSUI RESERVA DESSE LIVRO");
    }
}
