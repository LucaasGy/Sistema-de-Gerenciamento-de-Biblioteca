package erros.leitor;

/**
 * Exceção lançada quando um leitor estiver tentando retirar reserva de um livro que não reservou.
 *
 * @author Lucas Gabriel.
 */

public class LeitorNaoPossuiReservaDesseLivro extends Exception{

    /**
     * Cria uma nova instância de LeitorNaoPossuiReservaDesseLivro com a mensagem de erro especificada.
     */

    public LeitorNaoPossuiReservaDesseLivro() {
        super("LEITOR NÃO POSSUI RESERVA DESSE LIVRO");
    }
}
