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
     * Método que remove todos os prazos ativos de um livro.
     *
     * @param isbn isbn do livro que recebe o prazo
     */

    public void removerPrazosDeUmLivro(double isbn);

    /**
     * Método que remove um determinado prazo ativo.
     *
     * @param id identificação do leitor que recebe o prazo
     * @param isbn isbn do livro que recebe o prazo
     */

    public void removerUmPrazo(int id, double isbn);
}
