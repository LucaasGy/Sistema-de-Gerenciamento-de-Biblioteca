package dao;

import dao.adm.AdmDAO;
import dao.adm.AdmImplArquivo;
import dao.bibliotecario.BibliotecarioDAO;
import dao.bibliotecario.BibliotecarioImplArquivo;
import dao.emprestimo.EmprestimoDAO;
import dao.emprestimo.EmprestimoImplArquivo;
import dao.leitor.LeitorDAO;
import dao.leitor.LeitorImplArquivo;
import dao.livro.LivroDAO;
import dao.livro.LivroImplArquivo;
import dao.prazo.PrazosDAO;
import dao.prazo.PrazosImplArquivo;
import dao.reserva.ReservaDAO;
import dao.reserva.ReservaImplArquivo;

/**
 * Classe usada para acessar as extensões do DAO no sistema. Os dados armazenados são divididos em sete
 * entidades, que são: administrador, biliotecario, leitor, livro, empréstimo, reserva e prazo.
 * Os atributos e métodos estão instanciados como static, pois um objeto não precisa ser instanciado para
 * acessar essas funções.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class DAO {
    private static AdmDAO admDAO;
    private static BibliotecarioDAO bibliotecarioDAO;
    private static LeitorDAO leitorDAO;
    private static LivroDAO livroDAO;
    private static EmprestimoDAO emprestimoDAO;
    private static ReservaDAO reservaDAO;
    private static PrazosDAO prazosDAO;

    /**
     * Método de retorno do atributo admDAO, que é responsável por armazenar os dados
     * de uma lista de administradores e acessar os seus métodos de manuseio. O objeto é inicializado
     * como uma instância da classe AdmImpl, caso não tenha sido anteriormente.
     * @return retorna AdmDAO - objeto da classe AdmImpl
     */

    public static AdmDAO getAdm() {
        if (admDAO == null) {
            admDAO = new AdmImplArquivo();
        }

        return admDAO;
    }

    /**
     * Método de retorno do atributo bibliotecarioDAO, que é responsável por armazenar os dados
     * de uma lista de bibliotecarios e acessar os seus métodos de manuseio. O objeto é inicializado
     * como uma instância da classe BibliotecarioImpl, caso não tenha sido anteriormente.
     * @return retorna BibliotecarioDAO - objeto da classe BibliotecarioImpl
     */

    public static BibliotecarioDAO getBibliotecario() {
        if (bibliotecarioDAO == null) {
            bibliotecarioDAO = new BibliotecarioImplArquivo();
        }

        return bibliotecarioDAO;
    }

    /**
     * Método de retorno do atributo leitorDAO, que é responsável por armazenar os dados
     * de uma lista de leitores e acessar os seus métodos de manuseio. O objeto é inicializado
     * como uma instância da classe LeitorImpl, caso não tenha sido anteriormente.
     * @return retorna LeitorDAO - objeto da classe LeitorImpl
     */

    public static LeitorDAO getLeitor() {
        if (leitorDAO == null) {
            leitorDAO = new LeitorImplArquivo();
        }

        return leitorDAO;
    }

    /**
     * Método de retorno do atributo livroDAO, que é responsável por armazenar os dados
     * de uma lista de livros e acessar os seus métodos de manuseio. O objeto é inicializado
     * como uma instância da classe LivroImpl, caso não tenha sido anteriormente.
     * @return retorna LivroDAO - objeto da classe LivroImpl
     */

    public static LivroDAO getLivro() {
        if (livroDAO == null) {
            livroDAO = new LivroImplArquivo();
        }

        return livroDAO;
    }

    /**
     * Método de retorno do atributo emprestimoDAO, que é responsável por armazenar os dados
     * de uma lista de empréstimos e acessar os seus métodos de manuseio. O objeto é inicializado
     * como uma instância da classe EmprestimoImpl, caso não tenha sido anteriormente.
     * @return retorna EmprestimoDAO - objeto da classe EmprestimoImpl
     */

    public static EmprestimoDAO getEmprestimo() {
        if (emprestimoDAO == null) {
            emprestimoDAO = new EmprestimoImplArquivo();
        }

        return emprestimoDAO;
    }

    /**
     * Método de retorno do atributo reservaDAO, que é responsável por armazenar os dados
     * de uma lista de reservas e acessar os seus métodos de manuseio. O objeto é inicializado
     * como uma instância da classe ReservaImpl, caso não tenha sido anteriormente.
     * @return retorna ReservaDAO - objeto da classe ReservaImpl
     */

    public static ReservaDAO getReserva() {
        if (reservaDAO == null) {
            reservaDAO = new ReservaImplArquivo();
        }

        return reservaDAO;
    }

    /**
     * Método de retorno do atributo prazosDAO, que é responsável por armazenar os dados
     * de uma lista de prazos e acessar os seus métodos de manuseio. O objeto é inicializado
     * como uma instância da classe PrazosImpl, caso não tenha sido anteriormente.
     * @return retorna PrazosDAO - objeto da classe PrazosImpl
     */

    public static PrazosDAO getPrazos() {
        if (prazosDAO == null) {
            prazosDAO = new PrazosImplArquivo();
        }

        return prazosDAO;
    }
}
