package erros.leitor;

/**
 * Exceção lançada quando um leitor possuir um empréstimo ativo com atraso na devolução.
 * Como a multa é aplicada na devolução de um livro, existe a possibilidade de um leitor
 * possuir um empréstimo ativo em atraso e não estar multado.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorTemEmprestimoEmAtraso extends Exception{

    /**
     * Cria uma nova instância de LeitorTemEmprestimoEmAtraso com a mensagem de erro especificada.
     */

    public LeitorTemEmprestimoEmAtraso() {
        super("LEITOR POSSUI EMPRÉSTIMO EM ATRASO");
    }
}
