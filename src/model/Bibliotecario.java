package model;

import dao.DAO;
import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorNaoPossuiEmprestimo;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.livro.LivroReservado;
import erros.livro.PrazoVencido;
import erros.objetos.ObjetoInvalido;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Subclasse Bibliotecario que extende a Superclasse Usuário.
 *
 * Representa um Bibliotecario do sistema, podendo registrar novos dos livros,
 * fazer empréstimos e devoluções para determinado leitor e
 * também pesquisa de livros herdado da Superclasse Usuário.
 *
 * @author Lucas Gabriel.
 */

public class Bibliotecario extends Usuario {

    /**
     * Construtor de um Bibliotecario do sistema.
     *
     * Recebe a maioria dos atributos da classe para inseri-las diretamente.
     * A inserção é feita chamando o construtor da Superclasse.
     * O tipo de usuário é definido a depender do usuário em questão.
     *
     * @param nome nome do Bibliotecario
     * @param senha senha do Bibliotecario
     */

    public Bibliotecario(String nome, String senha){
        super(nome, senha, TipoUsuario.Bibliotecario);
    }

    /**
     * Método que cria um novo Livro e o adiciona no acervo da biblioteca
     *
     * @param livro objeto livro contendo todas as informações pré definidas
     */

    public void registrarLivro(Livro livro){
        DAO.getLivro().criar(livro);
    }

    /**
     * Método que realiza o Empréstimo de um livro para um determinado Leitor.
     *
     * Caso o livro não seja encontrado, esteja emprestado, possua reserva e o top 1 da fila não
     * for o leitor em questão ou o leitor em questão seja o top 1 da fila mas seu prazo para fazer o empréstimo
     * está vencido, o livro não poderá ser emprestado.
     * Caso o leitor não seja encontrado, esteja bloqueado, com multa ativa ou com algum emprestimo ativo,
     * o leitor não poderá realizar o empréstimo.
     * Caso tudo esteja correto, é realizado o empréstimo do livro e o empréstimo é registrado no sistema.
     *
     * ps:
     * -> se a multa do leitor ja estiver vencido, a multa é retirada
     * -> se o livro tiver reservado e o leitor em questão ser o top 1, ele é retirado da fila de reserva e seu prazo
     * é excluido
     *
     * @param id identicação do leitor
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado ou
     * o não seja encontrado o letiro com id informado, retorna uma exceção informando o ocorrido
     * @throws LeitorBloqueado caso o leitor esteja bloqueado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorMultado caso o leitor esteja multado e a multa ainda não ter vencido,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorTemEmprestimo caso o leitor já possua um empréstimo ativo,
     * retorna uma exceção informando o ocorrido
     * @throws LivroEmprestado caso o livro esteja emprestado,
     * retorna uma exceção informando o ocorrido
     * @throws LivroReservado caso o livro esteja reservado e o top 1 da fila não seja o leitor em questão,
     * retorna uma exceção informando o ocorrido
     * @throws PrazoVencido caso o livro esteja reservado, o leitor em questão seja o top 1 da fila, mas
     * o prazo para realizar o empréstimo esteja vencido, retorna uma exceção informando o ocorrido
     */

    public void fazerEmprestimo(int id, double isbn) throws ObjetoInvalido, LeitorBloqueado, LeitorMultado, LeitorTemEmprestimo, LivroEmprestado, LivroReservado, PrazoVencido {
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);
        Livro livro = DAO.getLivro().encontrarPorISBN(isbn);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");

        if(livro==null)
            throw new ObjetoInvalido("LIVRO NAO ENCONTRADO");

        if(leitor.getBloqueado())
            throw new LeitorBloqueado();

        if(leitor.getDataMulta()!=null) {
            if(leitor.getDataMulta().isAfter(LocalDate.now()))
                throw new LeitorMultado("LEITOR MULTADO\n A MULTA VENCERA EM: " + leitor.getDataMulta());

            leitor.setDataMulta(null);
        }

        if(DAO.getEmprestimo().encontrarPorId(id)!=null)
            throw new LeitorTemEmprestimo();

        if(!livro.getDisponivel())
            throw new LivroEmprestado();

        if(DAO.getReserva().livroTemReserva(isbn)){
            if(DAO.getReserva().top1Reserva(isbn).getLeitor().getID()!=id)
                throw new LivroReservado();

            DAO.getPrazos().removerUmPrazo(id, isbn);
            DAO.getReserva().removeTop1(isbn);

            if (DAO.getPrazos().encontrarUmPrazo(id, isbn).getDataLimite().isBefore(LocalDate.now())){
                throw new PrazoVencido();
            }
        }

        Emprestimo novo = new Emprestimo(livro,leitor);

        DAO.getEmprestimo().criar(novo);
    }

    /**
     * Método que realiza a Devolução de um Livro de um determiando Leitor.
     *
     * Caso o leitor não seja encontrado ou não possua empréstimo,
     * não há devoluão a ser feita.
     * Caso tudo esteja correto, a disponibilidade do livro é atualizada pra "true",
     * o limite de renovação de empréstimos do leitor em questão é resetado para 0 e
     * a devolução é feita, removendo o empréstimo da lista de empréstimos ativos.
     *
     * ps:
     * -> se a data atual de devolução vier depois da data prevista de devolução,
     * o leitor recebe uma data que indica o dia em que a multa vencerá e todas as
     * suas reservas e prazos são deletados do sistema
     * -> se o livro devolvido tiver reservas, é criado um prazo para o top 1 da fila ir
     * realizar o empréstimo do livro em questão e esse prazo é registrado no sistema
     *
     * @param id identificação do leitor
     * @throws ObjetoInvalido caso não seja encontrado o leitor com o id informado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorNaoPossuiEmprestimo caso o leitor não possua empréstimo ativo,
     * retorna uma exceção informando o ocorrido
     */

    public void devolverLivro(int id) throws ObjetoInvalido, LeitorNaoPossuiEmprestimo {
        if(DAO.getLeitor().encontrarPorId(id)==null){
            throw new ObjetoInvalido("LEITOR NAO ENCONTRADO");
        }

        Emprestimo opera = DAO.getEmprestimo().encontrarPorId(id);

        if(opera==null)
            throw new LeitorNaoPossuiEmprestimo();

        LocalDate devolve = LocalDate.now();

        long dias = ChronoUnit.DAYS.between(opera.getdataPrevista(),devolve);

        if(dias>0) {

            opera.getLeitor().setDataMulta(devolve.plusDays(dias*2));

            DAO.getReserva().remover(id);
            DAO.getPrazos().removerPrazosDeUmLeitor(id);
        }

        if(DAO.getReserva().livroTemReserva(opera.getLivro().getISBN())){
            Reserva opera2 = DAO.getReserva().top1Reserva(opera.getLivro().getISBN());
            Prazos novo = new Prazos(opera2.getLeitor(),opera2.getLivro(),devolve.plusDays(2));

            DAO.getPrazos().criar(novo);
        }

        opera.getLivro().setDisponivel(true);
        opera.getLeitor().setLimiteRenova(0);

        DAO.getEmprestimo().remover(id);
    }
}
