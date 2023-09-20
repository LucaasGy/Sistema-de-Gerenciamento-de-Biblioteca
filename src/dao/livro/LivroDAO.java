package dao.livro;

import dao.CRUD;

import model.Livro;

import java.util.List;

/**
 * Interface para as implementações de armazenamento dos objetos da classe Livro. É uma
 * extensão da interface CRUD.
 *
 * @author Lucas Gabriel.
 */

public interface LivroDAO extends CRUD<Livro> {

    /**
     * Método que encontra objetos Livro por meio do titulo.
     *
     * @param titulo o titulo sobre os quais os livros devem ser encontrados
     * @return retorna lista de livros encontrados
     */

    public List<Livro> encontrarPorTitulo(String titulo);

    /**
     * Método que encontra objetos Livro por meio do autor.
     *
     * @param autor o autor sobre os quais os livros devem ser encontrados
     * @return retorna lista de livros encontrados
     */

    public List<Livro> encontrarPorAutor(String autor);

    /**
     * Método que encontra objetos Livro por meio da categoria.
     *
     * @param categoria a categoria sobre os quais os livros devem ser encontrados
     * @return retorna lista de livros encontrados
     */

    public List<Livro> encontrarPorCategoria(String categoria);

    /**
     * Método que encontra objeto Livro por meio do ISBN.
     *
     * @param isbn o ISBN sobre o qual o livro deve ser encontrado
     * @return retorna objeto livro encontrado
     */

    public Livro encontrarPorISBN(double isbn);

    /**
     * Método que remove objeto Livro por meio do ISBN.
     *
     * @param isbn o ISBN sobre o qual o livro deve ser encontrado e removido
     */

    public void removerPorISBN(double isbn);
}
