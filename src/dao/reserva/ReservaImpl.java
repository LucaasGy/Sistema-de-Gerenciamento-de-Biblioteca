package dao.reserva;

import model.Reserva;

import java.util.ArrayList;
import java.util.List;

/**
 * É responsável por armazenar todas as reservas de livros do sistema,
 * e estruturar os métodos necessários para inserir, consultar, alterar ou remover.
 * Implementa a interface ReservaDAO.
 *
 * @author Lucas Gabriel.
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
     * @param obj Reserva que deve ser armazenada
     * @return retorna objeto reserva criado
     */

    @Override
    public Reserva criar(Reserva obj) {
        this.listaReserva.add(obj);

        return obj;
    }

    /**
     * Método que remove uma determinada reserva ativa.
     *
     * @param id identificação do leitor
     * @param isbn isbn do livro
     */

    public void removerUmaReserva(int id, double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor().getID()==id && reserva.getLivro().getISBN()==isbn) {
                this.listaReserva.remove(reserva);
                return;
            }
        }
    }

    /**
     * Método que remove todas as reservas ativas de um leitor.
     * É utilizado um for padrão invés de um for each, pois como
     * se trata de sequências de remoções em um loop, o for each
     * acaba dando erro pois o tamanho da lista vai alterando durante
     * as remoções. Utilizando um for padrão, este erro não ocorre.
     * 
     * @param id identificação do leitor
     */

    @Override
    public void removerReservasDeUmLeitor(int id) {
        for(int i = 0; i < this.listaReserva.size(); i++){
            if (this.listaReserva.get(i).getLeitor().getID()==id)
                this.listaReserva.remove(i);
        }
    }

    /**
     * Método que remove todas as reservas ativas de um livro.
     * É utilizado um for padrão invés de um for each, pois como
     * se trata de sequências de remoções em um loop, o for each
     * acaba dando erro pois o tamanho da lista vai alterando durante
     * as remoções. Utilizando um for padrão, este erro não ocorre.
     *
     * @param isbn isbn do livro
     */

    @Override
    public void removerReservasDeUmLivro(double isbn) {
        for(int i = 0; i < this.listaReserva.size(); i++){
            if (this.listaReserva.get(i).getLivro().getISBN()==isbn)
                this.listaReserva.remove(i);
        }
    }

    /**
     * Deleta todos os objetos do tipo Reserva do banco de dados.
     */

    @Override
    public void removerTodos() {
        this.listaReserva.clear();
    }

    /**
     * Método que encontra todas as reservas ativos.
     *
     * @return retorna lista de reservas ativas encontradas
     */

    @Override
    public List<Reserva> encontrarTodos() {
        return this.listaReserva;
    }

    /**
     * Método que encontra todas as reserva ativas de um livro.
     * Objetos Reserva que possuam o ISBN informado, são adicionados numa
     * lista e retornados.
     *
     * @param isbn isbn do livro reservado
     * @return retorna lista de reservas ativas de um livro
     */

    @Override
    public List<Reserva> encontrarLeitorReservouLivro(double isbn) {
        List<Reserva> listaReservaLeitor = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn)
                listaReservaLeitor.add(reserva);
        }

        return listaReservaLeitor;
    }

    /**
     * Método que encontra todas as reserva ativas de um leitor.
     * Objetos Reserva que possuam o ID informado, são adicionados numa
     * lista e retornados.
     *
     * @param id identificação do leitor
     * @return retorna lista de reservas ativas de um leitor
     */

    @Override
    public List<Reserva> encontrarLivroReservadoPorLeitor(int id) {
        List<Reserva> listaReservaLivro = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor().getID()==id)
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
            if(reserva.getLeitor().getID()==id && reserva.getLivro().getISBN()==isbn){
                return true;
            }
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
            if(reserva.getLivro().getISBN()==isbn)
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
            if(reserva.getLivro().getISBN()==isbn)
                return reserva;
        }

        return null;
    }

    /**
     * Método que remove a primeira reserva de um livro.
     *
     * @param isbn isbn do livro
     */

    @Override
    public void removeTop1(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn){
                this.listaReserva.remove(reserva);
                return;
            }
        }
    }

    @Override
    public Reserva encontrarPorId(int id) {
        return null;
    }
    @Override
    public void remover(int id){
    }

}
