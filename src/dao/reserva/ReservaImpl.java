package dao.reserva;

import model.Reserva;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todas as reservas de livros do sistema em uma lista,
 * e estruturar os métodos necessários para inserir, consultar, alterar ou remover.
 * Implementa a interface ReservaDAO.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class ReservaImpl implements ReservaDAO {

    private List<Reserva> listaReserva;

    /**
     * Construtor que inicializa a lista de armazenamento de reservas.
     */

    public ReservaImpl() {
        this.listaReserva = new ArrayList<Reserva>();
    }

    /**
     * Método para adicionar um objeto do tipo Reserva na lista de armazenamento.
     *
     * @param obj reserva que deve ser armazenada
     * @return retorna objeto reserva criado
     */

    @Override
    public Reserva criar(Reserva obj) {
        this.listaReserva.add(obj);
        return obj;
    }

    /**
     * Método que remove uma determinada reserva ativa da estrutura de armazenamento.
     *
     * @param id identificação do leitor
     * @param isbn isbn do livro
     */

    public void removerUmaReserva(int id, double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getIDleitor() == id && reserva.getISBNlivro() == isbn) {
                this.listaReserva.remove(reserva);
                return;
            }
        }
    }

    /**
     * Método que remove todas as reservas ativas de um leitor da estrutura de armazenamento.
     *
     * Esta forma de remoção em sequência, evita problemas de deslocamento de índice que podem
     * ocorrer ao remover elementos de um ArrayList enquanto o percorremos.
     * 
     * @param id identificação do leitor
     */

    @Override
    public void remover(int id){
        List<Reserva> reservasARemover = new ArrayList<Reserva>();

        for (Reserva reserva : this.listaReserva) {
            if (reserva.getIDleitor() == id)
                reservasARemover.add(reserva);
        }

        this.listaReserva.removeAll(reservasARemover);
    }

    /**
     * Método que remove todas as reservas ativas de um livro da estrutura de armazenamento.
     *
     * Esta forma de remoção em sequência, evita problemas de deslocamento de índice que podem
     * ocorrer ao remover elementos de um ArrayList enquanto o percorremos.
     *
     * @param isbn isbn do livro
     */

    @Override
    public void removerReservasDeUmLivro(double isbn) {
        List<Reserva> reservasARemover = new ArrayList<Reserva>();

        for (Reserva reserva : this.listaReserva) {
            if (reserva.getISBNlivro() == isbn)
                reservasARemover.add(reserva);
        }

        this.listaReserva.removeAll(reservasARemover);
    }

    /**
     * Deleta todos os objetos do tipo Reserva do banco de dados.
     */

    @Override
    public void removerTodos() {
        this.listaReserva.clear();
    }

    /**
     * Método que encontra todas as reservas ativas.
     *
     * @return retorna lista de reservas ativas encontradas
     */

    @Override
    public List<Reserva> encontrarTodos() {
        return this.listaReserva;
    }

    /**
     * Método que encontra todas as reserva ativas de um livro.
     *
     * Objetos Reserva que possuam o ISBN informado, são adicionados numa
     * lista e retornados.
     *
     * @param isbn isbn do livro reservado
     * @return retorna lista de reservas ativas de um livro
     */

    @Override
    public List<Reserva> encontrarReservasLivro(double isbn) {
        List<Reserva> listaReservaLeitor = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getISBNlivro() == isbn)
                listaReservaLeitor.add(reserva);
        }

        return listaReservaLeitor;
    }

    /**
     * Método que encontra todas as reserva ativas de um leitor.
     *
     * Objetos Reserva que possuam o ID informado, são adicionados numa
     * lista e retornados.
     *
     * @param id identificação do leitor
     * @return retorna lista de reservas ativas de um leitor
     */

    @Override
    public List<Reserva> encontrarReservasLeitor(int id) {
        List<Reserva> listaReservaLivro = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getIDleitor() == id)
                listaReservaLivro.add(reserva);
        }

        return listaReservaLivro;
    }

    /**
     * Método que verifica se já possui uma reserva ativa de um livro por um leitor.
     *
     * @param id identificação do leitor
     * @param isbn isbn do livro
     * @return retorna true caso encontre e false caso não encontre
     */

    @Override
    public boolean leitorJaReservouEsseLivro(int id, double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getIDleitor() == id && reserva.getISBNlivro() == isbn)
                return true;
        }

        return false;
    }

    /**
     * Método que verifica se determinado livro possui alguma reserva ativa.
     *
     * @param isbn isbn do livro
     * @return retorna true caso encontre e false caso não encontre
     */

    @Override
    public boolean livroTemReserva(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getISBNlivro() == isbn)
                return true;
        }

        return false;
    }

    /**
     * Método que encontra a primeira reserva de um livro.
     *
     * @param isbn isbn do livro
     * @return retorna a primeira reserva encontrada de um livro
     */

    @Override
    public Reserva top1Reserva(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getISBNlivro() == isbn)
                return reserva;
        }

        return null;
    }

    /**
     * Método que remove a primeira reserva de um livro da estrutura de armazenamento.
     *
     * @param isbn isbn do livro
     */

    @Override
    public void removeTop1(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getISBNlivro() == isbn){
                this.listaReserva.remove(reserva);
                return;
            }
        }
    }

    /**
     * Método de retorno da Reserva através da busca por ID.
     *
     * @param id identificação do leitor que fez a reserva a ser encontrada
     * @return retorna objeto reserva encontrado
     */

    @Override
    public Reserva encontrarPorId(int id) {
        for(Reserva reserva : this.listaReserva){
            if(reserva.getIDleitor() == id){
                return reserva;
            }
        }

        return null;
    }

    @Override
    public Reserva atualizar(Reserva obj) {
        return null;
    }

    @Override
    public void alteraParaPastaTeste() {
    }

    @Override
    public void alteraParaPastaPrincipal() {
    }
}
