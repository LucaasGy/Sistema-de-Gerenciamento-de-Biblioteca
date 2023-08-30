import java.time.LocalDate;

public class Prazos {
    private Livro livro;
    private LocalDate dataLimite;

    public Prazos(Livro livro, LocalDate dataLimite) {

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
}
