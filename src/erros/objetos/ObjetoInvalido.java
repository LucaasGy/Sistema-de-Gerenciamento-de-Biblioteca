package erros.objetos;

/**
 * Exceção lançada quando um objeto do sistema não for encontrado.
 *
 * @author Lucas Gabriel.
 */

public class ObjetoInvalido extends Exception{

    /**
     * Cria uma nova instância de ObjetoInvalido com a mensagem de erro especificada.
     */

    public ObjetoInvalido(String message) {
        super(message);
    }
}
