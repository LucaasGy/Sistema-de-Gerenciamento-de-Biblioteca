package erros.leitor;

public class LeitorBloqueado extends Exception{
    public LeitorBloqueado() {
        super("LEITOR ESTA BLOQUEADO");
    }
}
