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
        return DAO.getLivro().encontrarPorISBN(isbn);
    }

    public List<Livro> pesquisarLivroPorTitulo(String titulo){
        return DAO.getLivro().encontrarPorTitulo(titulo);
    }

    public List<Livro> pesquisarLivroPorAutor(String autor){
        return DAO.getLivro().encontrarPorAutor(autor);
    }

    public List<Livro> pesquisarLivroPorCategoria(String categoria){
        return DAO.getLivro().encontrarPorCategoria(categoria);
    }
}
