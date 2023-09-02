package model;

import java.time.LocalDate;

public class Emprestimo {
    private Livro livro;
    private Leitor leitor;
    private LocalDate dataPegou;
    private LocalDate dataPrevista;
    public Emprestimo(Livro livro, Leitor leitor) {
        this.livro = livro;
        this.leitor = leitor;

        this.dataPegou = LocalDate.now();
        this.dataPrevista = this.dataPegou.plusDays(7);
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
