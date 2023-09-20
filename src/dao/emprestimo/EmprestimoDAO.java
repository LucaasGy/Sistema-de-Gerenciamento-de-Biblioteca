package dao.emprestimo;

import dao.CRUD;

import model.Emprestimo;

import java.util.List;

/**
 * Interface para as implementações de armazenamento dos objetos da classe Empréstimo. É uma
 * extensão da interface CRUD.
 *
 * @author Lucas Gabriel.
 */

public interface EmprestimoDAO extends CRUD<Emprestimo> {

    /**
     * Método que encontra todos os empréstimos ativos.
     *
     * @return retorna lista de empréstimos ativos encontrados
     */
    public List<Emprestimo> encontrarTodosAtuais();

    /**
     * Método que encontra todos os empréstimos já feitos por um determinado leitor.
     *
     * @param id identificação do leitor que realizou o emprestimo
     * @return retorna lista de empréstimos de um leitor
     */

    public List<Emprestimo> encontrarHistoricoDeUmLeitor(int id);

    /**
     * Método que encontra todos os empréstimos já feitos de um determinado livro.
     *
     * @param isbn isbn do livro que foi emprestado
     * @return retorna lista de empréstimos de um livro
     */

    public List<Emprestimo> encontrarHistoricoDeUmLivro(double isbn);

    /**
     * Método que encontra um empréstimo pelo isbn do livro emprestado.
     *
     * @param isbn isbn do livro que foi emprestado
     * @return retorna o empréstimo do livro encontrado
     */

    public Emprestimo encontrarPorISBN(double isbn);

    /**
     * Método que remove todos os empréstimos já feitos de um livro deletado do sistema.
     *
     * @param isbn isbn do livro deletado
     */

    public void removerTodosEmprestimosDeUmLivro(double isbn);

    /**
     * Método que remove todos os empréstimos já feitos de um leitor deletado do sistema.
     *
     * @param id identificação do leitor deletado
     */

    public void removerTodosEmprestimoDeUmLeitor(int id);
}
