package model;

import dao.DAO;
import erros.objetos.ObjetoInvalido;

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

    public List<Livro> pesquisarLivroPorTitulo(String titulo) throws ObjetoInvalido {
        if(DAO.getLivro().encontrarPorTitulo(titulo)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        return DAO.getLivro().encontrarPorTitulo(titulo);
    }

    public List<Livro> pesquisarLivroPorAutor(String autor) throws ObjetoInvalido{
        if(DAO.getLivro().encontrarPorTitulo(autor)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        return DAO.getLivro().encontrarPorAutor(autor);
    }

    public List<Livro> pesquisarLivroPorCategoria(String categoria) throws ObjetoInvalido{
        if(DAO.getLivro().encontrarPorTitulo(categoria)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        return DAO.getLivro().encontrarPorCategoria(categoria);
    }

    public Livro pesquisarLivroPorISBN(double isbn) throws ObjetoInvalido{
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        return DAO.getLivro().encontrarPorISBN(isbn);
    }
}
