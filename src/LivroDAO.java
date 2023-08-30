import java.util.List;

public interface LivroDAO extends CRUD<Livro>{
    public List<Livro> encontrarPorTitulo(String titulo);

    public List<Livro> encontrarPorAutor(String autor);

    public List<Livro> encontrarPorCategoria(String categoria);

    public Livro encontrarPorISBN(double isbn);

    public void removerPorISBN(double isbn);
}
