package erros.objetos;

/**
 * Exceção lançada quando um usuário informar senha incorreta.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class UsuarioSenhaIncorreta extends Exception{

    /**
     * Cria uma nova instância de UsuarioSenhaIncorreta com a mensagem de erro especificada.
     */

    public UsuarioSenhaIncorreta() {
        super("SENHA INCORRETA");
    }
}
