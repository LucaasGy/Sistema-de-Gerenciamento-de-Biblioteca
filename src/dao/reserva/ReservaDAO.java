package dao.reserva;

import dao.CRUD;

import model.Reserva;

import java.util.List;

/**
 * Interface para as implementações de armazenamento dos objetos da classe Reserva. É uma
 * extensão da interface CRUD.
 *
 * @author Lucas Gabriel.
 */

public interface ReservaDAO extends CRUD<Reserva> {

    /**
     * Método que encontra todas as reserva ativas de um livro.
     *
     * @param isbn isbn do livro reservado
     * @return retorna lista de reservas ativas de um livro
     */

    public List<Reserva> encontrarReservasLivro(double isbn);

    /**
     * Método que encontra todas as reserva ativas de um leitor.
     *
     * @param id identificação do leitor
     * @return retorna lista de reservas ativas de um leitor
     */

    public List<Reserva> encontrarReservasLeitor(int id);

    /**
     * Método que remove todas as reservas ativas de um livro.
     *
     * @param isbn isbn do livro
     */

    public void removerReservasDeUmLivro(double isbn);

    /**
     * Método que remove uma determinada reserva ativa.
     *
     * @param id identificação do leitor
     * @param isbn isbn do livro
     */

    public void removerUmaReserva(int id, double isbn);

    /**
     * Método que remove a primeira reserva de um livro.
     *
     * @param isbn isbn do livro
     */

    public void removeTop1(double isbn);

    /**
     * Método que verifica se determinado livro possui alguma reserva ativa.
     *
     * @param isbn isbn do livro
     * @return retorna true caso encontre e false caso não encontre
     */

    public boolean livroTemReserva(double isbn);

    /**
     * Método que verifica se já possui uma reserva ativa de um livro por um leitor.
     *
     * @param id identificação do leitor
     * @param isbn isbn do livro
     * @return retorna true caso encontre e false caso não encontre
     */

    public boolean leitorJaReservouEsseLivro(int id, double isbn);

    /**
     * Método que encontra a primeira reserva de um livro.
     *
     * @param isbn isbn do livro
     * @return retorna a primeira reserva encontrada de um livro
     */

    public Reserva top1Reserva(double isbn);

}
