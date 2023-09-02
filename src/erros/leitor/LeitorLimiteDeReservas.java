package erros.leitor;

public class LeitorLimiteDeReservas extends Exception{
    public LeitorLimiteDeReservas() {
        super("LEITOR ATINGIU O LIMITE DE RESERVAS -> (3)");
    }
}
