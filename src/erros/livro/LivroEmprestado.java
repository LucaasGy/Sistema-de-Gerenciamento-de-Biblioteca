package erros.livro;

public class LivroEmprestado extends Exception{
    public LivroEmprestado() {
        super("LIVRO POSSUI EMPRESTIMO ATIVO");
    }
}
