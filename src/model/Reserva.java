package model;

import model.Leitor;
import model.Livro;

public class Reserva {
    private Livro livro;
    private Leitor leitor;

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
