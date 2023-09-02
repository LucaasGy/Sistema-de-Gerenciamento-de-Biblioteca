package erros.livro;

public class LivroLimiteDeReservas extends Exception{
    public LivroLimiteDeReservas() {
        super("LIVRO ATINGIU LIMITE DE RESERVA -> (4)");
    }
}
