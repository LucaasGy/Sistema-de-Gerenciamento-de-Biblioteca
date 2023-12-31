package erros.livro;

/**
 * Exceção lançada quando um livro possuir reserva.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LivroReservado extends Exception{

    /**
     * Cria uma nova instância de LivroReservado com a mensagem de erro especificada.
     */

    public LivroReservado( ) {
        super("LIVRO ESTÁ RESERVADO");
    }
}
