package dao.reserva;

import model.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaImpl implements ReservaDAO {

    private List<Reserva> listaReserva;

    public ReservaImpl() {
        this.listaReserva = new ArrayList<Reserva>();
    }

    @Override
    public Reserva criar(Reserva obj) {
        this.listaReserva.add(obj);

        return obj;
    }

    public void removerUmaReserva(int id, double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor().getID()==id && reserva.getLivro().getISBN()==isbn) {
                this.listaReserva.remove(reserva);
                return;
            }
        }
    }


    //remove todas as reservas de um dao.leitor
    @Override
    public void removerReservasDeUmLeitor(int id) {
        for(int i = 0; i < this.listaReserva.size(); i++){
            if (this.listaReserva.get(i).getLeitor().getID()==id)
                this.listaReserva.remove(i);
        }
    }

    //remove todas as reservas de um dao.livro
    @Override
    public void removerReservasDeUmLivro(double isbn) {
        for(int i = 0; i < this.listaReserva.size(); i++){
            if (this.listaReserva.get(i).getLivro().getISBN()==isbn)
                this.listaReserva.remove(i);
        }
    }

    @Override
    public void removerTodos() {
        this.listaReserva.clear();
    }

    @Override
    public List<Reserva> encontrarTodos() {
        return this.listaReserva;
    }

    @Override
    public List<Reserva> encontrarLeitorReservouLivro(double isbn) {
        List<Reserva> listaReservaLeitor = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn)
                listaReservaLeitor.add(reserva);
        }

        return listaReservaLeitor;
    }

    @Override
    public List<Reserva> encontrarLivroReservadoPorLeitor(int id) {
        List<Reserva> listaReservaLivro = new ArrayList<Reserva>();

        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor().getID()==id)
                listaReservaLivro.add(reserva);
        }

        return listaReservaLivro;
    }

    @Override
    public boolean leitorJaReservouEsseLivro(int id, double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor().getID()==id && reserva.getLivro().getISBN()==isbn){
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean leitorTemReserva(int id){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLeitor().getID()==id)
                return true;
        }

        return false;
    }

    @Override
    public boolean livroTemReserva(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn)
                return true;
        }

        return false;
    }

    //retorno a primeira dao.reserva de um dao.livro para comparar de Id dessa dao.reserva é o mesmo id de quem ta querendo fazer
    //o dao.emprestimo de um dao.livro reservado
    @Override
    public Reserva top1Reserva(double isbn){
        for(Reserva reserva : this.listaReserva){
            if(reserva.getLivro().getISBN()==isbn)
                return reserva;
        }

        return null;
    }

    //removo a primeira dao.reserva de um dao.livro ( top1 )
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
