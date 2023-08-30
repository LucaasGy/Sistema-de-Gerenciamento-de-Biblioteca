import java.util.List;

public class Convidado {
    private TipoUsuario tipoUsuario;

    public Convidado(TipoUsuario tipoUsuario) {
        this.tipoUsuario = TipoUsuario.Convidado;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Livro pesquisarLivroPorISBN(double isbn){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarPorISBN(isbn);
    }

    public List<Livro> pesquisarLivroPorTitulo(String titulo){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarPorTitulo(titulo);
    }

    public List<Livro> pesquisarLivroPorAutor(String autor){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarPorAutor(autor);
    }

    public List<Livro> pesquisarLivroPorCategoria(String categoria){
        LivroImpl opera = new LivroImpl();

        return opera.encontrarPorCategoria(categoria);
    }
}
