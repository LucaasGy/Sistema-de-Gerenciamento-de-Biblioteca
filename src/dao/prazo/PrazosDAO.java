package dao.prazo;

import dao.CRUD;

import model.Prazos;

import java.util.List;

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

    /**
     * Método que encontra todos os prazos ativos de um leitor.
     *
     * @param id identificação do leitor
     * @return retorna lista de prazos ativos de um leitor
     */

    public List<Prazos> prazosDeUmLeitor(int id);

    /**
     * Método que atualiza a lista de prazos com a exclusão de prazos vencidos e adição de novos prazos.
     *
     * @param removePrazos lista de prazos a remover ( prazos vencidos excluidos )
     * @param adinionaPrazos lista de prazos a adicionar ( novos prazos )
     */

    public void atualizarPrazos(List<Prazos> removePrazos, List<Prazos> adinionaPrazos);
}
