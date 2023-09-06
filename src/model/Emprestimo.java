package model;

import java.time.LocalDate;

/**
 * Classe Empréstimo que representa um empréstimo de um Livro por um Leitor.
 *
 * É definida pelo objeto Leitor e Livro, contendo todos os dados de ambos,
 * dois atributos da classe LocalDate para armazenar a data que foi realizada
 * o empréstimo e a data prevista para devolução ( 7 dias ).
 *
 * @author Lucas Gabriel.
 */

public class Emprestimo {
    private Livro livro;
    private Leitor leitor;
    private LocalDate dataPegou;
    private LocalDate dataPrevista;

    /**
     * Construtor de um Empréstimo de Livro.
     *
     * Recebe como parâmetro os dados do leitor e livro para inseri-los diretamente.
     * A data que pegou e a data prevista para devolução são inseridas automaticamente
     * na criação do empréstimo.
     * dataPegou é inicializada com a data atual da criação do empréstimo.
     * dataPrevista é inicializada com a data atual da criação do empréstimo adicionando 7 dias.
     * Ao criar um empréstimo, o livro automaticamente fica indisponível.
     * Cada empréstimo criado de um livro é somado um a quantidade de vezes que o livro
     * foi emprestado.
     *
     * @param livro o Livro que está sendo emprestado
     * @param leitor o Leitor que está fazendo o empréstimo
     */
    public Emprestimo(Livro livro, Leitor leitor) {
        this.livro = livro;
        this.leitor = leitor;

        this.dataPegou = LocalDate.now();
        this.dataPrevista = this.dataPegou.plusDays(7);

        this.livro.setDisponivel(false);
        this.livro.setQtdEmprestimo(this.livro.getQtdEmprestimo()+1);
    }

    public LocalDate getdataPegou() {
        return dataPegou;
    }

    public void setdataPegou(LocalDate dataPegou) {
        this.dataPegou = dataPegou;
    }

    public LocalDate getdataPrevista() {
        return dataPrevista;
    }

    public void setdataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }
}
