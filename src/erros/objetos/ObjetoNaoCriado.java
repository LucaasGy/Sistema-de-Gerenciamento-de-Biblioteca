package erros.objetos;

/**
 * Exceção lançada quando um usuario do sistema não for criado.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class ObjetoNaoCriado extends Exception{

    /**
     * Cria uma nova instância de UsuarioNaoCriado com a mensagem de erro especificada.
     *
     * @param message mensagem a ser exibida
     */

    public ObjetoNaoCriado(String message) {
        super(message);
    }
}
