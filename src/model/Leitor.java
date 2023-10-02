package model;

import dao.DAO;

import erros.leitor.*;
import erros.livro.LivroLimiteDeReservas;
import erros.livro.LivroNaoDisponivel;
import erros.livro.LivroNaoPossuiEmprestimoNemReserva;
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
     * Recebe como parâmetro a maioria dos atributos da classe para inseri-las diretamente.
     * A inserção é feita chamando o construtor da Superclasse.
     * O tipo de usuário é definido a depender do usuário em questão.
     * O atributo bloqueado indica se o leitor está bloqueado ou não. É iniciado como false, pois não está bloqueado.
     * O atributo dataMulta indica a data que o leitor poderá voltar a realizar empréstimos, reservas e renovações.
     * É iniciado como null, pois não foi multado.
     * O atributo limiteRenova indica o limite de renovações de empréstimo ativo que o leitor pode realizar. É iniciado
     * com 0, pois não realizou nenhuma renovação.
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

        this.limiteRenova = 0;
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
     * Caso o leitor não possua um empréstimo ativo, possua um emprestimo ativo em atraso,
     * esteja bloqueado, já tenha alcançado o limite de renovações de empréstimo ou
     * o livro possua alguma reserva, o leitor não poderá realizar a renovação.
     * Caso tudo esteja correto, o empréstimo atual é removido da lista de empréstimos ativos,
     * é criado um novo empréstimo com dataPegou atual e dataPrevista para daqui 7 dias e esse
     * novo empréstimo é registrado na lista de empréstimos ativos e na lista de todos os empréstimos
     * já feitos. Também é atualizado o limite de renovações.
     *
     * ps:
     * -> é necessário verificar se o leitor possui empréstimo ativo em atraso, pois como a multa
     * só é aplicada ao devolver um livro, existe a possibilidade dele estar "multado" sem a multa
     * ter sido aplicada ainda, restringindo ele de realizar reservas, emprestimos e renovações.
     *
     * @throws LeitorNaoPossuiEmprestimo caso o leitor não possua um empréstimo ativo,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorBloqueado caso o leitor esteja bloqueado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorTemEmprestimoEmAtraso caso o leitor possua um emprestimo ativo em atraso,
     * retorna uma exceção informando o ocorrido
     * @throws LivroReservado caso o livro possua alguma reserva,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorLimiteDeRenovacao caso o leitor já tenha alcançado o limite de
     * renovações de empréstimo, retorna uma exceção informando o ocorrido
     */

    public void renovarEmprestimo() throws LeitorNaoPossuiEmprestimo, LeitorBloqueado, LivroReservado, LeitorLimiteDeRenovacao, LeitorTemEmprestimoEmAtraso {
        if(this.getBloqueado())
            throw new LeitorBloqueado();

        Emprestimo emprestimoDoLeitor = DAO.getEmprestimo().encontrarPorId(this.getID());

        if(emprestimoDoLeitor==null)
            throw new LeitorNaoPossuiEmprestimo();

        else if(emprestimoDoLeitor.getdataPrevista().isBefore(LocalDate.now()))
            throw new LeitorTemEmprestimoEmAtraso();

        else if(DAO.getReserva().livroTemReserva(emprestimoDoLeitor.getLivro().getISBN()))
            throw new LivroReservado();

        else if(this.getLimiteRenova()==1)
            throw new LeitorLimiteDeRenovacao();

        DAO.getEmprestimo().remover(this.getID());

        emprestimoDoLeitor.setdataPegou(LocalDate.now());
        emprestimoDoLeitor.setdataPrevista(emprestimoDoLeitor.getdataPegou().plusDays(7));

        Leitor leitor = DAO.getLeitor().encontrarPorId(this.getID());
        leitor.setLimiteRenova(1);
        DAO.getLeitor().atualizar(leitor);

        DAO.getEmprestimo().criar(emprestimoDoLeitor);
    }

    /**
     * Método que realiza a Reserva de um livro para o Leitor.
     *
     * Caso o leitor esteja bloqueado, com multa ativa, possua um emprestimo ativo em atraso,
     * tenha atingido o limite de reservas ativas ou esteja tentando reservar um livro que esteja
     * emprestado por ele, a reserva não pode ser feita.
     * Caso o livro não seja encontrado, tenha atingido o limite de reservas ativas, não esteja disponível e
     * sem empréstimo ativo ou esteja disponível e sem reservas, a reserva não pode ser feita.
     * Caso tudo esteja correto, é criada uma reserva para o livro e a reserva é registrada no sistema.
     *
     * ps:
     * -> como existe a possiblidadade de um adm alterar a disponibilidade de um livro que não esteja com
     * um empréstimo ativo, é necessário verificar porque um livro não está disponível. Ele pode não está
     * disponível mesmo não possuindo um empréstimo ativo.
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
     * @throws LeitorTemEmprestimoEmAtraso caso o leitor possua um emprestimo ativo em atraso,
     * retorna uma exceção informando o ocorrido
     * @throws LivroNaoPossuiEmprestimoNemReserva caso o livro não possua um empréstimo ativo e nem reservas,
     * retorna uma exceção informando o ocorrido
     * @throws LivroNaoDisponivel caso o livro não esteja disponível e não possua um empréstimo ativo e
     * esteja disponível, retorna uma exceção informando o ocorrido
     * @throws LeitorPossuiReservaDesseLivro caso o leitor tentar reserva um livro que ele já possui reserva ativa,
     * retorna uma exceção informando o ocorrido
     */

    public void reservarLivro(double isbn) throws ObjetoInvalido, LeitorReservarLivroEmMaos, LivroLimiteDeReservas, LeitorLimiteDeReservas, LeitorBloqueado, LeitorMultado, LeitorTemEmprestimoEmAtraso, LeitorPossuiReservaDesseLivro, LivroNaoDisponivel, LivroNaoPossuiEmprestimoNemReserva {
        if(getBloqueado())
            throw new LeitorBloqueado();

        else if(getDataMulta()!=null)
            throw new LeitorMultado("LEITOR MULTADO\n A MULTA VENCERÁ EM: " + getDataMulta());

        else if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NÃO ENCONTRADO");

        Emprestimo emprestimoDoLeitor = DAO.getEmprestimo().encontrarPorId(this.getID());

        if(emprestimoDoLeitor!=null && emprestimoDoLeitor.getdataPrevista().isBefore(LocalDate.now()))
            throw new LeitorTemEmprestimoEmAtraso();

        else if (!DAO.getLivro().encontrarPorISBN(isbn).getDisponivel() && DAO.getEmprestimo().encontrarPorISBN(isbn) == null)
            throw new LivroNaoDisponivel();

        else if(DAO.getEmprestimo().encontrarPorISBN(isbn)!=null && DAO.getEmprestimo().encontrarPorISBN(isbn).getLeitor().getID()==this.getID())
            throw new LeitorReservarLivroEmMaos();

        else if(DAO.getReserva().leitorJaReservouEsseLivro(this.getID(), isbn))
            throw new LeitorPossuiReservaDesseLivro();

        else if(DAO.getReserva().encontrarReservasLivro(isbn).size()==4)
            throw new LivroLimiteDeReservas();

        else if(DAO.getReserva().encontrarReservasLeitor(this.getID()).size()==3)
            throw new LeitorLimiteDeReservas();

        else if(DAO.getLivro().encontrarPorISBN(isbn).getDisponivel() && !DAO.getReserva().livroTemReserva(isbn))
            throw new LivroNaoPossuiEmprestimoNemReserva();

        Reserva reserva = new Reserva(DAO.getLivro().encontrarPorISBN(isbn) , DAO.getLeitor().encontrarPorId(this.getID()));

        DAO.getReserva().criar(reserva);
    }

    /**
     * Método que retira uma Reserva feita pelo Leitor.
     *
     * Caso tudo esteja correto, remove uma reserva específica do sistema e caso exista,
     * remove também o prazo para aquela reserva. Caso o livro ainda possua reserva,
     * é criado também um novo prazo para o novo top1 da fila de reserva ir efetuar o
     * empréstimo.
     *
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorNaoPossuiReservaDesseLivro caso não seja encontrada a reserva do leitor do livro,
     * retorna uma exceção informando o ocorrido
     */

    public void retirarReserva(double isbn) throws ObjetoInvalido, LeitorNaoPossuiReservaDesseLivro {
        if(DAO.getLivro().encontrarPorISBN(isbn)==null)
            throw new ObjetoInvalido("LIVRO NÃO ENCONTRADO");

        else if(!DAO.getReserva().leitorJaReservouEsseLivro(this.getID(), isbn))
            throw new LeitorNaoPossuiReservaDesseLivro();

        DAO.getReserva().removerUmaReserva(this.getID(),isbn);

        if(DAO.getPrazos().encontrarPrazoDeUmLivro(isbn)!=null && DAO.getPrazos().encontrarPrazoDeUmLivro(isbn).getLeitor().getID()==this.getID()) {
            DAO.getPrazos().removerPrazoDeUmLivro(isbn);

            Reserva reservaTop1 = DAO.getReserva().top1Reserva(isbn);
            if(reservaTop1!=null){
                Prazos novoPrazoTop1 = new Prazos(reservaTop1.getLeitor(), reservaTop1.getLivro());
                DAO.getPrazos().criar(novoPrazoTop1);
            }
        }
    }
}
