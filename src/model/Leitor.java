package model;

import dao.DAO;
import erros.leitor.*;
import erros.livro.LivroLimiteDeReservas;
import erros.livro.LivroReservado;
import erros.objetos.ObjetoInvalido;

import java.time.LocalDate;

/**
 * Subclasse Leitor que extende a Superclasse Usuário.
 *
 * Representa um Leitor do sistema, podendo fazer reservas de livros,
 * renovações de empréstimos ativos, retirar reservas feitas e
 * também pesquisa de livros herdado da Superclasse Usuário.
 *
 * @author Lucas Gabriel.
 */

public class Leitor extends Usuario {
    private String endereco;
    private String telefone;
    private LocalDate dataMulta;
    private int limiteRenova;
    private boolean bloqueado;

    /**
     * Construtor de um Leitor do sistema.
     *
     * Recebe a maioria dos atributos da classe para inseri-las diretamente.
     * A inserção é feita chamando o construtor da Superclasse.
     * O tipo de usuário é definido a depender do usuário em questão.
     * O atributo bloqueado indicia se o leitor está bloqueado ou não
     * O atributo dataMulta indica a data que o leitor poderá voltar a realizar empréstimos, reservas e renovações
     * O atributo limiteRenova indica o limite de renovações de empréstimo ativo que o leitor pode realizar
     *
     * @param nome nome do leitor
     * @param endereco endereço do leitor
     * @param telefone telefone do leitor
     * @param senha senha do leitor
     */

    public Leitor(String nome, String endereco, String telefone, String senha) {
        super(nome,senha, TipoUsuario.Leitor);

        this.endereco = endereco.toLowerCase();
        this.telefone = telefone.toLowerCase();

        this.bloqueado = false;
        this.dataMulta = null;

        this.limiteRenova=0;
    }

    public LocalDate getDataMulta() {
        return dataMulta;
    }

    public void setDataMulta(LocalDate dataMulta) {
        this.dataMulta = dataMulta;
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getLimiteRenova() {
        return limiteRenova;
    }

    public void setLimiteRenova(int limiteRenova) {
        this.limiteRenova = limiteRenova;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Método que realiza uma Renovação de um empréstimo ativo do Leitor.
     *
     * Caso o leitor não possua um empréstimo ativo, esteja bloqueado, com multa ativa, já tenha
     * alcançado o limite de renovações de empréstimo ou o livro possua alguma reserva,
     * o leitor não poderá realizar a renovação.
     * Caso tudo esteja correto, o empréstimo atual é removido da lista de empréstimos ativos,
     * é criado um novo empréstimo com dataPegou atual e dataPrevista para daqui 7 dias e esse
     * novo empréstimo é registrado na lista de empréstimos ativos e na lista de todos os empréstimos
     * já feitos. Também é atualizado o limite de renovações.
     *
     * ps:
     * -> se a multa do leitor ja estiver vencido, a multa é retirada
     *
     * @throws LeitorNaoPossuiEmprestimo caso o leitor não possua um empréstimo ativo,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorBloqueado caso o leitor esteja bloqueado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorMultado caso o leitor esteja multado e a multa ainda estiver ativa,
     * retorna uma exceção informando o ocorrido
     * @throws LivroReservado caso o livro possua alguma reserva,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorLimiteDeRenovacao caso o leitor já tenha alcançado o limite de
     * renovações de empréstimo, retorna uma exceção informando o ocorrido
     */

    public void renovarEmprestimo() throws LeitorNaoPossuiEmprestimo, LeitorBloqueado, LeitorMultado, LivroReservado, LeitorLimiteDeRenovacao {
        Emprestimo novo = DAO.getEmprestimo().encontrarPorId(this.getID());

        if(novo==null)
            throw new LeitorNaoPossuiEmprestimo();

        if(this.getBloqueado())
            throw new LeitorBloqueado();

        if(this.getDataMulta()!=null) {
            if(this.getDataMulta().isAfter(LocalDate.now()))
                throw new LeitorMultado("LEITOR MULTADO\n A MULTA VENCERA EM: " + this.getDataMulta());

            this.setDataMulta(null);
        }

        if(DAO.getReserva().livroTemReserva(novo.getLivro().getISBN()))
            throw new LivroReservado();

        if(this.getLimiteRenova()==1)
            throw new LeitorLimiteDeRenovacao();

        DAO.getEmprestimo().remover(this.getID());

        novo.setdataPegou(LocalDate.now());
        novo.setdataPrevista(novo.getdataPegou().plusDays(7));

        this.setLimiteRenova(1);

        DAO.getEmprestimo().criar(novo);
    }

    /**
     * Método que realiza a Reserva de um livro para o Leitor.
     *
     * Caso o leitor esteja bloqueado, com multa ativa, tenha atingido o limite de reservas ativas,
     * esteja tentando reservar um livro que esteja emprestado por ele, a reserva não pode ser feita.
     * Caso o livro não seja encontrado ou tenha atingido o limite de reservas ativas,
     * a reserva não pode ser feita.
     * Caso tudo esteja correto, é criada uma reserva para o livro e a reserva é registrada no sistema.
     *
     * ps:
     * -> se a multa do leitor ja estiver vencido, a multa é retirada
     *
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorReservarLivroEmMaos caso o leitor tente reservar um livro que esteja emprestado por ele,
     * retorna uma exceção informando o ocorrido
     * @throws LivroLimiteDeReservas caso o livro tenha atingido o limite de reservas ativas,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorLimiteDeReservas caso o leitor tenha atingido o limite de reservas ativas,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorBloqueado caso o leitor esteja bloqueado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorMultado caso o leitor possua multa ativa,
     * retorna uma exceção informando o ocorrido
     */

    public void reservarLivro(double isbn) throws ObjetoInvalido, LeitorReservarLivroEmMaos, LivroLimiteDeReservas, LeitorLimiteDeReservas, LeitorBloqueado, LeitorMultado {
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        if(getBloqueado())
            throw new LeitorBloqueado();

        if(getDataMulta()!=null) {
            if(getDataMulta().isAfter(LocalDate.now()))
                throw new LeitorMultado("LEITOR MULTADO\n A MULTA VENCERA EM: " + getDataMulta());

            this.setDataMulta(null);
        }

        if(DAO.getReserva().encontrarLeitorReservouLivro(isbn).size()==4)
            throw new LivroLimiteDeReservas();

        if(DAO.getReserva().encontrarLivroReservadoPorLeitor(this.getID()).size()==3)
            throw new LeitorLimiteDeReservas();

        if(DAO.getEmprestimo().encontrarPorISBN(isbn).getLeitor().getID()==this.getID())
            throw new LeitorReservarLivroEmMaos();

        Reserva reserva = new Reserva(DAO.getLivro().encontrarPorISBN(isbn) , DAO.getLeitor().encontrarPorId(this.getID()));

        DAO.getReserva().criar(reserva);
    }

    /**
     * Método que retira uma Reserva feita pelo Leitor.
     *
     * Caso tudo esteja correto, remove uma reserva específica do sistema e caso exista,
     * remove também o prazo para aquela reserva.
     *
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     */

    public void retirarReserva(double isbn) throws ObjetoInvalido {
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        DAO.getReserva().removerUmaReserva(this.getID(),isbn);
        DAO.getPrazos().removerUmPrazo(this.getID(),isbn);
    }
}
