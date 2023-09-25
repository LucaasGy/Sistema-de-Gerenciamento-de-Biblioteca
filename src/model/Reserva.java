package model;

/**
 * Classe Reserva que representa uma Reserva de um Livro por um Leitor.
 *
 * É definida pelo ID do Leitor e ISBN do Livro, contendo todos os dados de ambos.
 *
 * @author Lucas Gabriel.
 */

public class Reserva {
    private double ISBNlivro;
    private int IDleitor;

    /**
     * Construtor de uma Reserva de um Livro.
     *
     * Recebe como parâmetro o ID do leitor e o ISBN do livro para inseri-los diretamente.
     *
     * @param ISBNlivro o livro a ser reservado
     * @param IDleitor o leitor que está reservando o livro
     */

    public Reserva(double ISBNlivro, int IDleitor) {
        this.ISBNlivro = ISBNlivro;
        this.IDleitor = IDleitor;
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
