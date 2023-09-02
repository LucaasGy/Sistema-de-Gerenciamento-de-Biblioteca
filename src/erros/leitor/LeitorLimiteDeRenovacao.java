package erros.leitor;

public class LeitorLimiteDeRenovacao extends Exception{
    public LeitorLimiteDeRenovacao( ) {
        super("LEITOR ATINGIU LIMITE DE RENOVACAO -> (1)");
    }
}
