import java.time.LocalDate;

public class Prazos {

    //como um usuario pode reservar ate 3 livros, quando o livro reservado for devolvido e o leitor ser o top 1 da fila
    //cria-se um prazo para o livro em questao, como sao no max 3 livros, preciso ter 3 prazos na lista que conteria,
    //o livro, o leitor em questao e a data limite para o leitor fazer o emprestimo dele.
    //caso ao tentar fazer o emprestimo de um dos 3 livros o prazo tenha sido estrapolado, eu removo esse prazo da
    //lista e removo o usuario da fila de reserva do livro em questao, nao interferindo na reserva e prazo dos outros 2
    //livros restantes.
    //Nao posso adicionar essa lista de prazos como atributo em letiro, porque caso eu remova algum livro do acervo, ou ate
    //mesmo queira tirar todas as reservas de um livro, eu tambem teria que tirar todos os prazos daquele livro, e eu teria
    //que procurar na lista de prazos de cada objeto leitor e ir removendo caso eu achasse o livro em questao

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
