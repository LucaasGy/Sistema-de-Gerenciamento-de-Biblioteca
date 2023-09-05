package model;

/**
 * Classe Reserva que representa uma Reserva de um Livro por um Leitor.
 *
 * É definida pelo objeto Leitor e Livro, contendo todos os dados de ambos.
 *
 * @author Lucas Gabriel.
 */

public class Reserva {
    private Livro livro;
    private Leitor leitor;

    /**
     * Construtor de uma Reserva de um Livro.
     *
     * Recebe como parâmetro os dados do leitor e livro para inseri-los diretamente.
     *
     * @param livro o livro a ser reservado
     * @param leitor o leitor que está reservando o livro
     */

    public Reserva(Livro livro, Leitor leitor) {
        this.livro = livro;
        this.leitor = leitor;
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
