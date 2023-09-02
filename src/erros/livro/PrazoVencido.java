package erros.livro;

public class PrazoVencido extends Exception{
    public PrazoVencido() {
        super("PRAZO VENCIDO PARA RETIRAGEM DO LIVRO");
    }
}
