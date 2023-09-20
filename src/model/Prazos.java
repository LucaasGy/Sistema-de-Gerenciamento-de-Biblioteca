package model;

import java.time.LocalDate;

/**
 * Classe Prazos que representa um prazo para o primeiro da fila de reserva ir realizar o empréstimo do Livro.
 * Classe criada pois um leitor pode realizar até 3 reservas ativas, logo pode precisar de 3 prazos ativos,
 * mesmo não podendo fazer empréstimo dos 3.
 *
 * É definida pelo objeto Leitor e Livro, contendo todos os dados de ambos,
 * além de um atributo da classe LocalDate para armazenar a data limite para
 * o leitor ir realizar o empréstimo ( 2 dias após o livro ser devolvido ).
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
     * Recebe como parâmetros os atributos da classe para inseri-los diretamente.
     * A dataLimite é definida como a data atual da criação do prazo mais 2 dias,
     * referente o limite de dias para ir realizar o empréstimo do livro.
     *
     * @param leitor o Leitor do prazo
     * @param livro o Livro do prazo
     */

    public Prazos(Leitor leitor, Livro livro) {

        this.leitor = leitor;
        this.livro = livro;
        this.dataLimite = LocalDate.now().plusDays(2);
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
