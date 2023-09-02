package model;

import model.Leitor;
import model.Livro;

import java.time.LocalDate;

public class Prazos {

    //como um usuario pode reservar ate 3 livros, quando o dao.livro reservado for devolvido e o dao.leitor ser o top 1 da fila
    //cria-se um dao.prazo para o dao.livro em questao, como sao no max 3 livros, preciso ter 3 prazos na lista que conteria,
    //o dao.livro, o dao.leitor em questao e a data limite para o dao.leitor fazer o dao.emprestimo dele.
    //caso ao tentar fazer o dao.emprestimo de um dos 3 livros o dao.prazo tenha sido estrapolado, eu removo esse dao.prazo da
    //lista e removo o usuario da fila de dao.reserva do dao.livro em questao, nao interferindo na dao.reserva e dao.prazo dos outros 2
    //livros restantes.
    //Nao posso adicionar essa lista de prazos como atributo em letiro, porque caso eu remova algum dao.livro do acervo, ou ate
    //mesmo queira tirar todas as reservas de um dao.livro, eu tambem teria que tirar todos os prazos daquele dao.livro, e eu teria
    //que procurar na lista de prazos de cada objeto dao.leitor e ir removendo caso eu achasse o dao.livro em questao

    private Leitor leitor;
    private Livro livro;
    private LocalDate dataLimite;

    public Prazos(Leitor leitor, Livro livro, LocalDate dataLimite) {

        this.leitor = leitor;
        this.livro = livro;
        this.dataLimite = dataLimite;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }
}
