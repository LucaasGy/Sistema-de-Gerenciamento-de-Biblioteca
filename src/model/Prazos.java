package model;

import java.time.LocalDate;

/**
 * Classe Prazos que representa um prazo para o primeiro da fila de reserva ir realizar o empréstimo do Livro.
 * Classe criada pois um leitor pode realizar até 3 reservas ativas, logo pode precisar de 3 prazos ativos,
 * mesmo não podendo fazer empréstimo dos 3.
 *
 * É definida pelo objeto Leitor e Livro, contendo todos os dados de ambos,
 * além de um atributo da classe LocalDate para armazenar a data limite para
 * o leitor ir realizar o empréstimo ( 2 dias após o livro ficar disponível ).
 *
 * @author Lucas Gabriel.
 */

public class Prazos {
    private Leitor leitor;
    private Livro livro;
    private LocalDate dataLimite;

    /**
     * Construtor de um prazo para empréstimo.
     *
     * Recebe como parâmetros todos os atributos da classe para inseri-los diretamente.
     *
     * @param leitor o Leitor do prazo
     * @param livro o Livro do prazo
     * @param dataLimite a data limite para ir realizar o empréstimo
     */

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
