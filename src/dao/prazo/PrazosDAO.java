package dao.prazo;

import dao.CRUD;
import model.Prazos;

/**
 * Interface para as implementações de armazenamento dos objetos da classe Prazos. É uma
 * extensão da interface CRUD.
 *
 * @author Lucas Gabriel.
 */

public interface PrazosDAO extends CRUD<Prazos> {

    /**
     * Método que encontra o prazo ativo de um livro.
     *
     * @param isbn isbn do livro que recebe o prazo
     * @return retorna prazo encontrado
     */

    public Prazos encontrarPrazoDeUmLivro(double isbn);

    /**
     * Método que remove o prazo ativo de um livro.
     *
     * @param isbn isbn do livro que recebe o prazo
     */

    public void removerPrazoDeUmLivro(double isbn);
}
