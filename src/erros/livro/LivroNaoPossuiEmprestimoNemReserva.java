package erros.livro;

/**
 * Exceção lançada quando um livro não tiver um empréstimo e reserva ativa, e um leitor tentar reservá-lo.
 *
 * @author Lucas Gabriel.
 */

public class LivroNaoPossuiEmprestimoNemReserva extends Exception{

    /**
     * Cria uma nova instância de LivroNaoPossuiEmprestimoNemReserva com a mensagem de erro especificada.
     */

    public LivroNaoPossuiEmprestimoNemReserva() {
        super("LIVRO NÃO POSSUI EMPRÉSTIMO E NEM RESERVA ATIVA");
    }
}
