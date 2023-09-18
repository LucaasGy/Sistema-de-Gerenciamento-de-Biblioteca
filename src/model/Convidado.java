package model;

import dao.DAO;
import erros.objetos.ObjetoInvalido;

import java.util.List;

/**
 * Classe Convidado que representa um Convidade do sistema,
 * podendo apenas realizar buscas de livro no sistema.
 * Como Convidado não precisa de nome e senha, ele não pode herdar
 * a Superclasse Usuário. Por isso, os métodos de busca de livrros
 * também foram implementados na classe.
 *
 * @author Lucas Gabriel.
 */

public class Convidado {
    private TipoUsuario tipoUsuario;

    /**
     * Construtor de um Convidado do sistema.
     * O tipo de usuário é definido como Convidado diretamente.
     */

    public Convidado() {
        this.tipoUsuario = TipoUsuario.Convidado;
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
        if(DAO.getLivro().encontrarPorTitulo(titulo).isEmpty())
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

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
        if(DAO.getLivro().encontrarPorAutor(autor).isEmpty())
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

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
        if(DAO.getLivro().encontrarPorCategoria(categoria).isEmpty())
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

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
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        return DAO.getLivro().encontrarPorISBN(isbn);
    }
}
