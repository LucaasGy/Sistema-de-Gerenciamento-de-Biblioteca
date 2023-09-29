package dao;

import java.util.List;

/**
 * Interface para um DAO genérico que contém as operações básicas de persistência de um objeto.
 *
 * @param <T> o tipo de objeto que será salvo
 *
 * @author Lucas Gabriel.
 */

public interface CRUD<T> {

    /**
     * Método de inserção do objeto na estrutura de armazenamento da implementação.
     *
     * @param obj objeto que deve ser inserido
     * @return retorna o objeto que foi criado
     */
    T criar(T obj);

    /**
     * Método para remover determinado objeto da estrutura de armazenamento.
     *
     * @param id id do objeto a ser deletado
     */
    void remover(int id);

    /**
     * Deleta todos os objetos do tipo T do banco de dados.
     */
    void removerTodos();

    /**
     * Método de busca do objeto através da busca por ID
     *
     * @param id identificação do objeto a ser encontrado
     * @return retorna o objeto que foi encontrado
     */
    T encontrarPorId(int id);

    /**
     * Método de retorno de todos os objetos do tipo T do banco de dados.
     *
     * @return retorna todos os objetos do tipo T
     */
    List<T> encontrarTodos();

    /**
     * Método que atualiza um objeto do tipo T.
     *
     * @param obj objeto a ser atualizado
     * @return retorna objeto que foi atualizado
     */
    T atualizar (T obj);

    /**
     * Método que altera o caminho do arquivo para realizar testes unitários e de integração.
     */
    void alteraParaPastaTeste();

    /**
     * Método que retorna o caminho do arquivo após realizar testes unitários e de integração.
     */
    void alteraParaPastaPrincipal();
}