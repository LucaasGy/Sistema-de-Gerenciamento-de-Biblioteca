package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe Empréstimo que representa um empréstimo de um Livro por um Leitor.
 * É definida pelo ID do Leitor e ISBN do Livro,
 * dois atributos da classe LocalDate para armazenar a data que foi realizada
 * o empréstimo e a data prevista para devolução ( 7 dias ).
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class Emprestimo implements Serializable {
    private double ISBNlivro;
    private int IDleitor;
    private LocalDate dataPegou;
    private LocalDate dataPrevista;

    /**
     * Construtor de um Empréstimo de Livro.
     *
     * Recebe como parâmetro o ID do leitor e o ISBN do livro para inseri-los diretamente.
     * A data que pegou e a data prevista para devolução são inseridas automaticamente
     * na criação do empréstimo.
     * dataPegou é inicializada com a data atual da criação do empréstimo.
     * dataPrevista é inicializada com a data atual da criação do empréstimo adicionando 7 dias.
     *
     * @param ISBNlivro o livro que está sendo emprestado
     * @param IDleitor o leitor que está fazendo o empréstimo
     */
    public Emprestimo(double ISBNlivro, int IDleitor) {
        this.ISBNlivro = ISBNlivro;
        this.IDleitor = IDleitor;

        this.dataPegou = LocalDate.now();
        this.dataPrevista = this.dataPegou.plusDays(7);
    }

    public double getISBNlivro() {
        return ISBNlivro;
    }

    public void setISBNlivro(double ISBNlivro) {
        this.ISBNlivro = ISBNlivro;
    }

    public int getIDleitor() {
        return IDleitor;
    }

    public void setIDleitor(int IDleitor) {
        this.IDleitor = IDleitor;
    }

    public LocalDate getDataPegou() {
        return dataPegou;
    }

    public void setDataPegou(LocalDate dataPegou) {
        this.dataPegou = dataPegou;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
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

    public double getLivro() {
        return ISBNlivro;
    }

    public void setLivro(double ISBNlivro) {
        this.ISBNlivro = ISBNlivro;
    }

    public int getLeitor() {
        return IDleitor;
    }

    public void setLeitor(int IDleitor) {
        this.IDleitor = IDleitor;
    }
}
