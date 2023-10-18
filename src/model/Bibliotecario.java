package model;

import dao.DAO;
import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorNaoPossuiEmprestimo;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroEmprestado;
import erros.livro.LivroNaoDisponivel;
import erros.livro.LivroNaoPossuiEmprestimo;
import erros.livro.LivroReservado;
import erros.objetos.ObjetoInvalido;

/**
 * Subclasse Bibliotecario que extende a Superclasse Usuário.
 *
 * Representa um Bibliotecario do sistema, podendo registrar novos dos livros,
 * fazer empréstimos e devoluções para determinado leitor e também pesquisa de livros.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class Bibliotecario extends Usuario {

    /**
     * Construtor de um Bibliotecario do sistema.
     *
     * Recebe como parâmetro a maioria dos atributos da classe para inseri-las diretamente.
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
     * Método que realiza o Empréstimo de um livro para um determinado Leitor.
     *
     * Caso o livro possua reserva e o top 1 da fila não
     * for o leitor em questão, o livro não poderá ser emprestado.
     * Caso o leitor não seja encontrado, esteja bloqueado, com multa ativa ou com algum emprestimo ativo,
     * o leitor não poderá realizar o empréstimo.
     * Caso tudo esteja correto, é realizado o empréstimo do livro e o empréstimo é registrado no sistema.
     *
     * ps:
     * -> se o livro possuir reserva e o top 1 da fila for o leitor em questão, sua reserva e seu prazo
     * são deletados.
     *
     * @param id identicação do leitor
     * @param isbn isbn do livro
     * @throws ObjetoInvalido caso não seja encontrado o livro com o isbn informado ou
     * o não seja encontrado o leitor com o id informado, retorna uma exceção informando o ocorrido
     * @throws LeitorBloqueado caso o leitor esteja bloqueado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorMultado caso o leitor esteja multado,
     * retorna uma exceção informando o ocorrido
     * @throws LeitorTemEmprestimo caso o leitor já possua um empréstimo ativo,
     * retorna uma exceção informando o ocorrido
     * @throws LivroReservado caso o livro esteja reservado e o top 1 da fila não seja o leitor em questão,
     * retorna uma exceção informando o ocorrido
     */

    public static void fazerEmprestimo(int id, double isbn) throws ObjetoInvalido, LeitorBloqueado, LeitorMultado, LeitorTemEmprestimo, LivroReservado{
        Leitor leitor = DAO.getLeitor().encontrarPorId(id);

        if(leitor==null)
            throw new ObjetoInvalido("LEITOR NÃO ENCONTRADO");

        else if(leitor.getBloqueado())
            throw new LeitorBloqueado();

        else if(leitor.getDataMulta()!=null)
            throw new LeitorMultado("LEITOR MULTADO. A MULTA VENCERÁ EM: " + leitor.getDataMulta());

        else if (DAO.getEmprestimo().encontrarPorId(id) != null)
            throw new LeitorTemEmprestimo();

        else if(DAO.getReserva().livroTemReserva(isbn)){
            if (DAO.getReserva().top1Reserva(isbn).getLeitor() != id)
                throw new LivroReservado();

            DAO.getPrazos().removerPrazoDeUmLivro(isbn);
            DAO.getReserva().removeTop1(isbn);
        }

        Emprestimo novo = new Emprestimo(isbn,id);
        DAO.getEmprestimo().criar(novo);
    }

    /**
     * Método que realiza a Devolução de um determinado Livro.
     *
     * Caso o livro não possua empréstimo, não há devolução a ser feita.
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
     * @param isbn isbn do livro
     * @throws LivroNaoPossuiEmprestimo caso o livro não possua empréstimo ativo,
     * retorna uma exceção informando o ocorrido
     */

    public static void devolverLivro(double isbn) throws LivroNaoPossuiEmprestimo {
        Emprestimo emprestimoLivro = DAO.getEmprestimo().encontrarPorISBN(isbn);

        if(emprestimoLivro==null)
            throw new LivroNaoPossuiEmprestimo();

        Sistema.aplicarMulta(emprestimoLivro);

        if(DAO.getReserva().livroTemReserva(emprestimoLivro.getLivro())){
            Reserva reservaTop1 = DAO.getReserva().top1Reserva(emprestimoLivro.getLivro());
            Prazos novo = new Prazos(reservaTop1.getLeitor(),reservaTop1.getLivro());

            DAO.getPrazos().criar(novo);
        }

        Livro alteraLivro = DAO.getLivro().encontrarPorISBN(emprestimoLivro.getLivro());
        Leitor alteraLeitor = DAO.getLeitor().encontrarPorId(emprestimoLivro.getLeitor());
        alteraLivro.setDisponivel(true);
        alteraLeitor.setLimiteRenova(0);
        DAO.getLivro().atualizar(alteraLivro);
        DAO.getLeitor().atualizar(alteraLeitor);

        DAO.getEmprestimo().remover(emprestimoLivro.getLeitor());
    }
}
