package model;

import java.time.LocalDate;

/**
 * Classe Prazos que representa um prazo para o primeiro da fila de reserva ir realizar o empréstimo do Livro.
 * Classe criada pois um leitor pode realizar até 3 reservas ativas, logo pode precisar de 3 prazos ativos,
 * mesmo não podendo fazer empréstimo dos 3.
 *
 * É definida pelo ID do Leitor e ISBN do Livro,
 * além de um atributo da classe LocalDate para armazenar a data limite para
 * o leitor ir realizar o empréstimo ( 2 dias após o livro ser devolvido ).
 *
 * @author Lucas Gabriel.
 */

public class Prazos {
    private int IDleitor;
    private double ISBNlivro;
    private LocalDate dataLimite;

    /**
     * Construtor de um prazo para empréstimo.
     *
     * Recebe como parâmetros os atributos da classe para inseri-los diretamente.
     * A dataLimite é definida como a data atual da criação do prazo mais 2 dias,
     * referente o limite de dias para ir realizar o empréstimo do livro.
     *
     * @param IDleitor o Leitor do prazo
     * @param ISBNlivro o Livro do prazo
     */

    public Prazos(int IDleitor, double ISBNlivro) {

        this.IDleitor = IDleitor;
        this.ISBNlivro = ISBNlivro;
        this.dataLimite = LocalDate.now().plusDays(2);
    }

    public double getLivro() {
        return ISBNlivro;
    }

    public void setLivro(double ISBNlivro) {
        this.ISBNlivro = ISBNlivro;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public int getLeitor() {
        return IDleitor;
    }

    public void setLeitor(int IDleitor) {
        this.IDleitor = IDleitor;
    }
}
