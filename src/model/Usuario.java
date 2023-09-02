package model;

import dao.DAO;
import erros.objetos.ObjetoInvalido;

import java.util.List;

public abstract class Usuario {
    private int ID;
    private String nome;
    private String senha;
    private TipoUsuario tipoUsuario;

    public Usuario(String nome, String senha, TipoUsuario tipoUsuario) {
        this.nome = nome.toLowerCase();
        this.senha = senha.toLowerCase();
        this.tipoUsuario = tipoUsuario;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
