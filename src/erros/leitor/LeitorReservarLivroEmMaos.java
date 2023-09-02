package erros.leitor;

public class LeitorReservarLivroEmMaos extends Exception{
    public LeitorReservarLivroEmMaos() {
        super("LEITOR NAO PODE RESERVAR LIVRO EM MAOS");
    }
}
