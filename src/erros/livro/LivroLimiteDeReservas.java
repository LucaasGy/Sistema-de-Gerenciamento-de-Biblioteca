package erros.livro;

/**
 * Exceção lançada quando um livro possuir o máximo de reservas (4).
 *
 * @author Lucas Gabriel.
 */

public class LivroLimiteDeReservas extends Exception{

    /**
     * Cria uma nova instância de LivroLimiteDeReservas com a mensagem de erro especificada.
     */

    public LivroLimiteDeReservas() {
        super("LIVRO ATINGIU LIMITE DE RESERVA -> (4)");
    }
}
