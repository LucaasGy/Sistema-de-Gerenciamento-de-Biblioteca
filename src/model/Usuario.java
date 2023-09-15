package model;

import dao.DAO;
import erros.objetos.ObjetoInvalido;

import java.util.List;

/**
 * Superclasse Usuário, que representa possíveis utilizadores do sistema.
 * Contém um id, nome, senha e o tipo de usuário que irá herdar a classe.
 * Como Adm, Bibliotecario e Leitor a herdam e todos eles podem realizar
 * pesquisa de livros, os métodos para pesquisa foram implementados aqui.
 *
 * @author Lucas Gabriel.
 */

public abstract class Usuario {
    private int ID;
    private String nome;
    private String senha;
    private TipoUsuario tipoUsuario;

    /**
     * Construtor de um Usuário do sistema.
     *
     * Contém os parâmetros que tanto ADM, Bibliotecario e Leitor devem ter.
     *
     * @param nome nome do Usuário
     * @param senha senha do Usuário
     * @param tipoUsuario tipo do Usuário ( ADM, Bibliotecario ou Leitor )
     */

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

    /**
     * Método que busca um Livro pelo seu Titulo.
     *
     * @param titulo titulo do livro
     * @return retorna uma lista de livros com o titulo em questão
     * @throws ObjetoInvalido caso não seja encontrado um livro com o titulo informado,
     * é retornada uma exceção informando o ocorrido
     */

    public List<Livro> pesquisarLivroPorTitulo(String titulo) throws ObjetoInvalido {
        if(DAO.getLivro().encontrarPorTitulo(titulo)==null)
            throw new ObjetoInvalido("LIVRO NÃO ENCONTRADO");

        return DAO.getLivro().encontrarPorTitulo(titulo);
    }

    /**
     *  Método que busca um Livro pelo seu Autor.
     *
     * @param autor autor do livro
     * @return retorna uma lista de livros com o autor em questão
     * @throws ObjetoInvalido caso não seja encontrado um livro com o autor informado,
     * é retornada uma exceção informando o ocorrido
     */

    public List<Livro> pesquisarLivroPorAutor(String autor) throws ObjetoInvalido{
        if(DAO.getLivro().encontrarPorTitulo(autor)==null)
            throw new ObjetoInvalido("LIVRO NÃO ENCONTRADO");

        return DAO.getLivro().encontrarPorAutor(autor);
    }

    /**
     * Método que busca um Livro pela sua Categoria.
     *
     * @param categoria categoria do livro
     * @return retorna uma lista de livros com a categoria em questão
     * @throws ObjetoInvalido caso não seja encontrado um livro com a categoria informada,
     * é retornada uma exceção informando o ocorrido
     */

    public List<Livro> pesquisarLivroPorCategoria(String categoria) throws ObjetoInvalido{
        if(DAO.getLivro().encontrarPorTitulo(categoria)==null)
            throw new ObjetoInvalido("LIVRO NÃO ENCONTRADO");

        return DAO.getLivro().encontrarPorCategoria(categoria);
    }

    /**
     * Método que busca um Livro pelo seu Isbn.
     *
     * @param isbn isbn do livro
     * @return como isbn é único, retorna o objeto livro com o isbn em questão
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * é retornada uma exceção informando o ocorrido
     */

    public Livro pesquisarLivroPorISBN(double isbn) throws ObjetoInvalido{
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NÃO ENCONTRADO");

        return DAO.getLivro().encontrarPorISBN(isbn);
    }
}
