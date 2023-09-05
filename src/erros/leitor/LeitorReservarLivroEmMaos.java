package erros.leitor;

/**
 * Exceção lançada quando um leitor tentar reservar um livro que ele está em mãos.
 *
 * @author Lucas Gabriel.
 */

public class LeitorReservarLivroEmMaos extends Exception{

    /**
     * Cria uma nova instância de LeitorReservarLivroEmMaos com a mensagem de erro especificada.
     */

    public LeitorReservarLivroEmMaos() {
        super("LEITOR NAO PODE RESERVAR LIVRO EM MAOS");
    }
}
