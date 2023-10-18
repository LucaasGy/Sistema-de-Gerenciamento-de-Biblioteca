package erros.leitor;

/**
 * Exceção lançada quando um leitor atingir o máximo de limite de renovações de empréstimos (1).
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class LeitorLimiteDeRenovacao extends Exception{

    /**
     * Cria uma nova instância de LeitorLimiteDeRenovacao com a mensagem de erro especificada.
     */

    public LeitorLimiteDeRenovacao( ) {
        super("LEITOR ATINGIU LIMITE DE RENOVAÇÃO -> (1)");
    }
}
